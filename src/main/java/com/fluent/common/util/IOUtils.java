package com.fluent.common.util;
/*@formatter:off */

import java.lang.management.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

import static com.fluent.common.util.Constants.*;

import sun.management.*;


public final class IOUtils{

    public final static String  HOSTNAME        = getHostName( );
    @SuppressWarnings( "restriction" )
    private final static RuntimeMXBean MX_BEAN  = ManagementFactoryHelper.getRuntimeMXBean();
        
    private IOUtils( ){}

   
    public final static List<String> loadFile( String filename ) throws Exception{
        Path filePath   = Paths.get(filename);
        if( !Files.exists(filePath) ){
            throw new Exception( "FAILED to load " + filePath + " as it doesn't exist." );
        }
    
        List<String> all= Files.readAllLines( filePath );
        
        return all;
    
    }
        

    public final static boolean isWindows( ){
        String OS_NAME         = System.getProperty( "os.name" ).toLowerCase( );
        return OS_NAME.indexOf( "win" ) >= ZERO;
    }


    public final static boolean isLinux( ) {
        String OS_NAME         = System.getProperty( "os.name" ).toLowerCase( );
        return OS_NAME.indexOf( "nix" ) >= ZERO || OS_NAME.indexOf( "nux" ) >= ZERO || OS_NAME.indexOf( "aix" ) > ZERO;
    }
    
    
    public final static String getFullProcessName( ) {
        return MX_BEAN.getName( );
    }
    

    public final static String getHostName( ) {
        String hostName = EMPTY;
        try{
            hostName = InetAddress.getLocalHost( ).getHostName( );
        }catch( Exception e ){
        }

        return hostName;
    }
    
}
