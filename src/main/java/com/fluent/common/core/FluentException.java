package com.fluent.common.core;

import static com.fluent.common.util.Constants.*;


public class FluentException extends Exception{

    private static final long serialVersionUID = 1L;

    public FluentException( String message ){
        this( message, null );
    }

    public FluentException( Throwable e ){
        this( EMPTY, e );
    }

    public FluentException( String message, Throwable e ){
        super( message, e );
    }


}