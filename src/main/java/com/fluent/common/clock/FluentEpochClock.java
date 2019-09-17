package com.fluent.common.clock;

/**
 * Time in milliseconds since 1 Jan 1970 UTC.
 *
 * @return the number of milliseconds since 1 Jan 1970 UTC.
 */

public final class FluentEpochClock implements FluentClock{

    @Override
    public final long time() {
        return System.currentTimeMillis( );
    }
    
}
