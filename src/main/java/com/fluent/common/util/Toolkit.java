package com.fluent.common.util;

import java.math.*;
import java.util.*;

import static com.fluent.common.util.Constants.*;


public final class Toolkit{

    private Toolkit( ){}

    private final static double DEFAULT_TOLERANCE = 1.0e-10;
    
    
    public final static <T> T errorIfNull( T reference, String message ){
        if( reference == null ){
            throw new IllegalArgumentException( message );
        }

        return reference;
    }
    

    //------------------------------------------------------------------------------------------
    //int
    //------------------------------------------------------------------------------------------

    public final static int nextPowerOfTwo( final int value ) {
        return 1 << (32 - Integer.numberOfLeadingZeros( value - 1 ));
    }

    
    public final static boolean isInteger( String value ){
        if (value == null) {
            return false;
        }
        
        int length = value.length();
        if (length == 0) {
            return false;
        }
        
        int i = 0;
        if (value.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        
        for (; i < length; i++) {
            char c = value.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        
        return true;
    }

    public final static int parseInteger( String value ) {
        return isBlank( value ) ? ZERO : Integer.parseInt( value );
    }
    
    
    public final static int getRoundedTickDifference( double pivotPrice, double newPrice, double minPriceTick ){

        double priceDifference      = ( newPrice - pivotPrice );
        double tickDifference       = ( priceDifference / minPriceTick );
        double tickDifferenceAdj    = ( tickDifference >= ZERO ) ? (tickDifference + PRICE_TOLERANCE) : (tickDifference - PRICE_TOLERANCE);
        int roundedTickDifference   = (int) tickDifferenceAdj;

        return roundedTickDifference;

    }

    
    public final static int errorIfNegative( int value, String message ){
        if( value <= 0 ){
            throw new IllegalArgumentException( message );
        }

        return value;
    }
    

    //------------------------------------------------------------------------------------------
    //double
    //------------------------------------------------------------------------------------------

    public final static double parseDouble( String value ) {
        return isBlank( value ) ? ZERO_DOUBLE : Double.parseDouble( value );
    }

    
    public final static boolean doubleEquals( double first, double second ){
        return doubleEquals( first, second, DEFAULT_TOLERANCE );
    }
    
    
    public final static boolean doubleEquals( double first, double second, double tolerance ){
        return ( Math.abs(first - second) <= tolerance );
    }
    
    
    public final static double bgRound( int scale, double value ){
        BigDecimal bd = new BigDecimal(value).setScale(scale, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }


    //------------------------------------------------------------------------------------------
    //TimeZone
    //------------------------------------------------------------------------------------------
    public final static TimeZone parseTimeZone( String timeZoneStr ) throws Exception {

        TimeZone timeZone = TimeZone.getTimeZone( timeZoneStr );
        if( !timeZone.getID( ).equals( timeZoneStr ) ){
            throw new Exception( "TimeZone [" + timeZoneStr + "] is invalid." );
        }

        return timeZone;
    }
    

    
    //------------------------------------------------------------------------------------------
    //String
    //------------------------------------------------------------------------------------------

    public final static String toUpper( String data ) {
        return isBlank( data ) ? EMPTY : data.trim( ).toUpperCase( );
    }
    
    
    public final static boolean isBlank( String data ) {
        return (data == null || data.isEmpty( )) ? true : false;
    }
    
    
    public final static String errorIfEmpty( String value, String message ){
        if( isBlank(value) ){
            throw new IllegalArgumentException( message );
        }

        return value;
    }
    
    
    public static List<String> fastSplit( String text, char separator ) {

        final List<String> result = new ArrayList<String>( );

        if( text != null && text.length( ) > ZERO ){

            int index1 = ZERO;
            int index2 = text.indexOf( separator );

            while( index2 >= ZERO ){
                String token = text.substring( index1, index2 );
                result.add( token );
                index1 = index2 + ONE;
                index2 = text.indexOf( separator, index1 );
            }

            if( index1 < text.length( ) - ONE ){
                result.add( text.substring( index1 ) );
            }
        }// else: input unavailable

        return result;

    }


    public final static String padLeft( String value, int spaces ){
        return String.format( "%1$" + spaces + "s", value );
    }
    
    public final static String padRight( String value, int spaces ){
        return String.format( "%1$-" + spaces + "s", value );
    }

}
