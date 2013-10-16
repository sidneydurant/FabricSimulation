
/* @author Sidney Durant
 * 
 * supplies methods for ARGB color manipulation, although the internal
 * representation uses four shorts, the external representation uses the
 * ARGB integer standard to encode the color. Shorts are used instead of bytes
 * to avoid dealing with the fact that bytes only store numbers from -128 to
 * 127, causing problems when using multiplication for interpolation
 */

 // need an abstractColor type class thing that can contain negative color values so that I can store the difference between two colors for line drawing(?)
 // no I don't, because if everything is done correctly, negative colors should never actually exist anywhere that they will be drawn(?)
 // use floats so I can do things I otherwise couldn't (for example, treat colors in a way synonymous to how I treat points when lerping for rasterization

public class Color {

	/*
	 * the shorts a, r, g, b must always have 8 leading empty bits, meaning
	 * that they will be a value from 0 to 255
	 */
	
	private short a, r, g, b;
	
	public Color( int c ){
		a = (short) ((c & 0xff000000) >>> 24);
		r = (short) ((c & 0xff0000) >> 16);
		g = (short) ((c & 0xff00) >> 8);
		b = (short) (c & 0xff);
	}
	
	// add int c to the current color
	public void add( int c ){
		a = (short) ( a + ( c & 0xff000000));
		r = (short) ( r + ( c & 0xff0000 ));
		g = (short) ( r + ( c & 0xff00 ));
		b = (short) ( b + ( c & 0xff ));
	}
	
	// subtract int c from the current color, if the color becomes negative
	// the person doing the subtraction should be aware, and thus the color
	// wont be drawn
	public void subtract( int c ){
		a = (short) ( a - ( c & 0xff000000));
		r = (short) ( r - ( c & 0xff0000 ));
		g = (short) ( r - ( c & 0xff00 ));
		b = (short) ( b - ( c & 0xff ));
	}
	
	// multiply each of the ARGB values by a scalar f
	public void multiply( float f ){
		a = (a*f) >= 0xff ? 0xff : (short)(a * f);
		r = (r*f) >= 0xff ? 0xff : (short)(r * f);
		g = (g*f) >= 0xff ? 0xff : (short)(g * f);
		b = (b*f) >= 0xff ? 0xff : (short)(b * f);	
	}
	
	// return the color as an ARGB encoded int
	public int getColor(){
		return a<<24|r<<16|g<<8|b ;
	}
	
}
