package com.fluent.common.util;

import java.util.*;

import static com.fluent.common.util.Constants.*;


public final class Toolkit{

    protected Toolkit( ){}

    
    public final static String errorIfEmpty( String value, String message ){
        if( isBlank(value) ){
            throw new IllegalArgumentException( message );
        }

        return value;
    }
    
    
    public final static <T> T errorIfNull( T reference, String message ){
        if( reference == null ){
            throw new IllegalArgumentException( message );
        }

        return reference;
    }


    public final static String notBlank( String reference, Object message ) {
        if( reference == null || reference.trim( ).isEmpty( ) ){
            throw new IllegalArgumentException( String.valueOf( message ) );
        }

        return reference;
    }


    public final static int notNegative( int value, Object message ) {
        if( value <= 0 ){
            throw new IllegalArgumentException( String.valueOf( message ) );
        }

        return value;
    }

    
    public final static boolean isInteger( String data ) {

        try{
            Integer.parseInt( data );
            return true;
        }catch( Exception e ){
        }

        return false;

    }


    public final static boolean isBlank( String data ) {
        return (data == null || data.isEmpty( )) ? true : false;
    }


    public final static String toUpper( String data ) {
        return isBlank( data ) ? EMPTY : data.trim( ).toUpperCase( );
    }


    public final static int nextPowerOfTwo( final int value ) {
        return 1 << (32 - Integer.numberOfLeadingZeros( value - 1 ));
    }


    public final static int parseInteger( String value ) {
        return isBlank( value ) ? ZERO : Integer.parseInt( value );
    }


    public final static double parseDouble( String value ) {
        return isBlank( value ) ? ZERO_DOUBLE : Double.parseDouble( value );
    }


    public final static double rndNumberBetween( final double upper, final double lower ) {
        return (Math.random( ) * (upper - lower)) + lower;
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
