package com.fluent.common.clock;



public final class FluentNanoClock implements FluentClock{

    @Override
    public final long time() {
        return System.nanoTime( );
    }
    
}
