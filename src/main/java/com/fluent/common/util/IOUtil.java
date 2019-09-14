package com.fluent.common.util;
/*@formatter:off */

import java.lang.management.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

import static com.fluent.common.util.Constants.*;

import sun.management.*;


public final class IOUtil{

    public final static boolean IS_WINDOWS      = isWindows( );
    public final static boolean IS_LINUX        = isLinux( );
    public final static String  HOSTNAME        = getHostName( );
    @SuppressWarnings( "restriction" )
    private final static RuntimeMXBean MX_BEAN  = ManagementFactoryHelper.getRuntimeMXBean();
    private final static String OS_NAME         = System.getProperty( "os.name" ).toLowerCase( );
    
    private IOUtil( ){}

   
    public final static List<String> loadFile( String filename ) throws Exception{
        Path filePath   = Paths.get(filename);
        if( !Files.exists(filePath) ){
            throw new Exception( "FAILED to load " + filePath + " as it doesn't exist." );
        }
    
        List<String> all= Files.readAllLines( filePath );
        
        return all;
    
    }
        

    protected final static boolean isWindows( ){
        return OS_NAME.indexOf( "win" ) >= ZERO;
    }


    protected final static boolean isLinux( ) {
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
