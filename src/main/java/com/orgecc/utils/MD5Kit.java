package com.orgecc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public final class MD5Kit {

    private MD5Kit() {
        // utility class
    }

    public static String md5sum( final File file ) {

        try {
            return md5sum( new FileInputStream( file ) );

        } catch ( final Exception e ) {
            throw new RuntimeException( e );
        }

    }

    public static String md5sum( final InputStream in ) {

        try {

            final MessageDigest md5 = MessageDigest.getInstance( "MD5" );

            final byte[] buffer = new byte[ 8192 ];
            int count;
            while ( ( count = in.read( buffer ) ) > 0 ) {
                md5.update( buffer, 0, count );
            }

            return bytesToHexString( md5.digest() );

        } catch ( final Exception e ) {
            throw new RuntimeException( e );
        }

    }

    public static String bytesToHexString( final byte[] bytes ) {

        final StringBuilder result = new StringBuilder( bytes.length * 2 );
        for ( final byte b: bytes ) {
            result.append( String.format( "%02x", b & 0xff ) );
        }

        return result.toString();

    }

}
