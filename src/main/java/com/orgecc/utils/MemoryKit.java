package com.orgecc.utils;

import java.lang.management.ManagementFactory;

import javax.management.ObjectName;

/**
 *
 * @author Dr Heinz Kabutz
 * @see String Substring - By Dr Heinz Kabutz, in the 230th edition of The Java(tm) Specialists'
 *      Newsletter
 */
public class MemoryKit {

    public static long getThreadAllocatedBytes() {
        try {

            return (Long) ManagementFactory.getPlatformMBeanServer().invoke(
                    new ObjectName( ManagementFactory.THREAD_MXBEAN_NAME ),
                    "getThreadAllocatedBytes", new Object[] {
                        Thread.currentThread().getId()
                    }, new String[] {
                        long.class.getName()
                    } );

        } catch ( final Exception e ) {
            throw new IllegalArgumentException( e );
        }

    }

}
