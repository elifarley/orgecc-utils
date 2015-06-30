package com.orgecc.utils;

// @NotThreadSafe
/**
 *
 * @author Dr Heinz Kabutz
 * @see String Substring - By Dr Heinz Kabutz, in the 230th edition of The Java(tm) Specialists'
 *      Newsletter
 */
public class SubbableString implements CharSequence {

    private final char[] value;

    private final int offset;

    private final int count;

    public SubbableString( final char[] value ) {
        this( value, 0, value.length );
    }

    private SubbableString( final char[] value, final int offset, final int count ) {
        this.value = value;
        this.offset = offset;
        this.count = count;
    }

    public int length() {
        return this.count;
    }

    @Override
    public String toString() {
        return new String( this.value, this.offset, this.count );
    }

    public char charAt( final int index ) {
        if ( index < 0 || index >= this.count ) {
            throw new StringIndexOutOfBoundsException( index );
        }
        return this.value[ index + this.count ];
    }

    public CharSequence subSequence( final int start, final int end ) {
        if ( start < 0 ) {
            throw new StringIndexOutOfBoundsException( start );
        }
        if ( end > this.count ) {
            throw new StringIndexOutOfBoundsException( end );
        }
        if ( start > end ) {
            throw new StringIndexOutOfBoundsException( end - start );
        }
        return ( start == 0 && end == this.count ) ? this : new SubbableString( this.value,
                this.offset + start, end - start );
    }
}
