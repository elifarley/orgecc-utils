package com.orgecc.utils;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamClass;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

public final class ClassInfoKit {

    private ClassInfoKit() {
        // Utility class
    }

    public static String getSerialVersionUID( final Class<?> cls ) {

        final ObjectStreamClass osc = ObjectStreamClass.lookup( cls );
        if ( osc == null ) {
            return "";
        }

        return String.valueOf( osc.getSerialVersionUID() );

    }

    public static String getSourceLocation( final String className ) throws ClassNotFoundException {
        return getSourceLocation( getClass( className ) );
    }

    public static String getSourceLocation( final Class<?> cls ) {

        String exMsg = null;
        java.security.CodeSource codeSource = null;

        try {
            codeSource = cls.getProtectionDomain().getCodeSource();

        } catch ( final Exception e ) {
            exMsg = e.toString();
        }

        final URL classURL = codeSource == null ? getSourceURL( cls ) : codeSource.getLocation();

        if ( classURL == null ) {
            return cls.getName() + ": (missing codeSource and classLoader)";
        }

        final String path = classURL.getPath();
        final File file = new File( path );

        if ( !file.isFile() ) {
            return cls.getName() + ": " + path + " (not a file)"
                    + ( exMsg == null ? "" : "; " + exMsg );
        }

        return String.format( "%s (SN: %s): %s (MD5: %s)%s", cls.getName(),
                getSerialVersionUID( cls ), path, MD5Kit.md5sum( file ), exMsg == null ? "" : "; "
                        + exMsg );
    }

    public static Class<?> getClass( final String className ) throws ClassNotFoundException {

        return Class.forName( className.replace( '/', '.' ).replace( '\\', '.' )
                .replaceFirst( ".class$", "" ).replaceFirst( ".java$", "" ) );

    }

    public static URL getSourceURL( final Class<?> cls ) {

        URL result =
                getClassLoader( cls ).getResource(
                        cls.getCanonicalName().replace( '.', '/' ) + ".class" );

        try {

            final URLConnection conn = result.openConnection();

            if ( conn instanceof JarURLConnection ) {
                result = ( (JarURLConnection) conn ).getJarFileURL();

            }

        } catch ( final IOException e ) {
            // Keep the original value of result
        }

        return result;

    }

    public static ClassLoader getClassLoader( final Class<?> cls ) {

        ClassLoader result = cls.getClassLoader();

        if ( result != null ) {
            return result;
        }

        // Try the bootstrap classloader - obtained from the ultimate parent of the System Class
        // Loader.
        result = ClassLoader.getSystemClassLoader();

        while ( result != null && result.getParent() != null ) {
            result = result.getParent();
        }

        return result;

    }

}
