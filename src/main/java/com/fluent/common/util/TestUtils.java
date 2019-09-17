package com.fluent.common.util;

public final class TestUtils{
    
    private TestUtils() {}
    
    
    public final static double rndNumberBetween( double upper, double lower ) {
        return (Math.random( ) * (upper - lower)) + lower;
    }
    

}
