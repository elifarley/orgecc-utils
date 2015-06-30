package com.orgecc.utils;

public class ExceptionKit {

    /**
     *
     * @param exception A checked exception to be thrown
     * @throws T trick
     * @see http://stackoverflow.com/a/18408831/299109
     */
    @SuppressWarnings( "unchecked" )
    public static <T extends Throwable> void throwException( final Throwable exception ) throws T {
        throw (T) exception;
    }

    public static void throwException( final Throwable toBeThrown, final Throwable cause ) {
        ExceptionKit.<RuntimeException> throwException( toBeThrown.initCause( cause ) );
    }

}
