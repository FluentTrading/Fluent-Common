package com.fluent.common.collections;

import static java.lang.Math.*;

import java.util.*;
import java.util.concurrent.atomic.*;

import com.fluent.common.util.*;

/**
 * Only supports only one producer and one consumer.
 * 
 * @author vsingh
 */
public final class FluentCoalescingRingBuffer<K, V>{

    private final AtomicLong nextWrite = new AtomicLong(1); // the next write index
    private long lastCleaned = 0; // the last index that was nulled out by the producer
    private final AtomicLong rejectionCount = new AtomicLong(0);
    private final K[] keys;
    private final AtomicReferenceArray<V> values;

    @SuppressWarnings("unchecked")
    private final K nonCollapsibleKey = (K) new Object();
    private final int mask;
    private final int capacity;

    private volatile long firstWrite = 1; // the oldest slot that is is safe to write to
    private final AtomicLong lastRead = new AtomicLong(0); // the newest slot that it is safe to overwrite

    @SuppressWarnings("unchecked")
    public FluentCoalescingRingBuffer( int capacity ){
        this.capacity   = Toolkit.nextPowerOfTwo(capacity);
        this.mask       = this.capacity - 1;
        this.keys       = (K[]) new Object[this.capacity];
        this.values     = new AtomicReferenceArray<V>(this.capacity);
    }


    public final int capacity() {
        return capacity;
    }

    public final long rejectionCount() {
        return rejectionCount.get();
    }

    public final long nextWrite() {
        return nextWrite.get();
    }

    public final long firstWrite() {
        return firstWrite;
    }

    public final boolean isEmpty() {
        return firstWrite == nextWrite.get();
    }

    public final boolean isFull() {
        return size() == capacity;
    }

    public final int size() {

        // loop until you get a consistent read of both volatile indices
        while (true) {
            long lastReadBefore     = lastRead.get();
            long currentNextWrite   = this.nextWrite.get();
            long lastReadAfter      = lastRead.get();

            if (lastReadBefore == lastReadAfter) {
                return (int) (currentNextWrite - lastReadBefore) - 1;
            }
        }
    }
    
    
    public final boolean offer(K key, V value) {
        long nextWrite = this.nextWrite.get();

        for (long updatePosition = firstWrite; updatePosition < nextWrite; updatePosition++) {
            int index = mask(updatePosition);

            if(key.equals(keys[index])) {
                values.set(index, value);

                if (updatePosition >= firstWrite) {  // check that the reader has not read beyond our update point yet
                    return true;
                } else {
                    break;
                }
            }
        }

        return add(key, value);
    }

    
    public final boolean offer(V value) {
        return add(nonCollapsibleKey, value);
    }

    
    private final boolean add(K key, V value) {
        if (isFull()) {
            rejectionCount.lazySet(rejectionCount.get() + 1);
            return false;
        }

        cleanUp();
        store(key, value);
    
        return true;
    }

    
    private final void cleanUp() {
        long lastRead = this.lastRead.get();

        if (lastRead == lastCleaned) {
            return;
        }

        while (lastCleaned < lastRead) {
            int index = mask(++lastCleaned);
            keys[index] = null;
            values.lazySet(index, null);
        }
    }

    
    private final void store(K key, V value) {
        long nextWrite = this.nextWrite.get();
        int index = mask(nextWrite);

        keys[index] = key;
        values.set(index, value);

        this.nextWrite.lazySet(nextWrite + 1);
    }

    
    public final int drainTo(Collection<? super V> bucket) {
        return fill(bucket, nextWrite.get());
    }

    
    public final int drainTo(Collection<? super V> bucket, int maxItems) {
        long claimUpTo = min(firstWrite + maxItems, nextWrite.get());
        return fill(bucket, claimUpTo);
    }

    
    private final int fill(Collection<? super V> bucket, long claimUpTo) {
        firstWrite = claimUpTo;
        long lastRead = this.lastRead.get();

        for (long readIndex = lastRead + 1; readIndex < claimUpTo; readIndex++) {
            int index = mask(readIndex);
            bucket.add(values.get(index));
            values.set(index, null);
        }

        this.lastRead.lazySet(claimUpTo - 1);
        
        return (int) (claimUpTo - lastRead - 1);
    }

    
    private final int mask(long value) {
        return ((int) value) & mask;
    }

}