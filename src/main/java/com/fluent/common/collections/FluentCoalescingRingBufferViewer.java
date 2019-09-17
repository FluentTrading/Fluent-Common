package com.fluent.common.collections;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class FluentCoalescingRingBufferViewer{
    
    private final FluentCoalescingRingBuffer<?, ?> buffer;

    public FluentCoalescingRingBufferViewer(FluentCoalescingRingBuffer<?, ?> buffer) {
        this.buffer = buffer;
    }

    public int getSize() {
        return buffer.size();
    }

    
    public int getCapacity() {
        return buffer.capacity();
    }

    
    public int getRemainingCapacity() {
        return buffer.capacity() - buffer.size();
    }

    
    public long getRejectionCount() {
        return buffer.rejectionCount();
    }

    
    public long getProducerIndex() {
        return buffer.nextWrite();
    }

    
    public long getConsumerIndex() {
        return buffer.firstWrite();
    }

    
    public static void register(String bufferName, FluentCoalescingRingBuffer<?, ?> buffer, MBeanServer mBeanServer) throws JMException {
        ObjectName name = createObjectName(bufferName);
        FluentCoalescingRingBufferViewer bean = new FluentCoalescingRingBufferViewer(buffer);
        mBeanServer.registerMBean(bean, name);
    }

    public static void unregister(String bufferName, MBeanServer mBeanServer) throws JMException {
        ObjectName name = createObjectName(bufferName);
        mBeanServer.unregisterMBean(name);
    }

    private static ObjectName createObjectName(String bufferName) throws MalformedObjectNameException {
        return new ObjectName("com.fluent.common.collections.coalescing.ring.buffer:type=" + bufferName);
    }

}