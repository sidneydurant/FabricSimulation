
/* @author Sidney Durant
 * 
 * This class can rasterize basic geometric primitives onto a 1D array of size
 * WIDTH*HEIGHT
 * implemented: points, lines, and triangles with vertex colors defined
 */

public class Render {

	private static int WIDTH, HEIGHT;
	private static int[] raster;

	public Render(int width, int height, int[] raster) {

		WIDTH = width;
		HEIGHT = height;
		this.raster = raster;

	}

	// clears all of the pixels to be transparent black
	public void clear() {
		for (int i = 0; i < raster.length; i++){
			raster[i] = 0xffffffff;
		}
	}

	// draws a point if its inside the frame
	public void drawPoint(int x, int y) {
		if (x < WIDTH && x >= 0 && y < HEIGHT && y >= 0) {
			raster[x + WIDTH * y] = 0xff000000;
		}
	}

	// attempts to draw a point whether or not it's inside the frame. Will 
	// throw arrayIndexOutOfBoundsException, drawPoint() is a safe alternative
	public void drawUncheckedPoint(int x, int y) {
		raster[x + WIDTH * y] = 0xff000000;
	}
	
	// draws a point if its inside the frame
		public void drawColoredPoint(int x, int y, Color c) {
			if (x < WIDTH && x >= 0 && y < HEIGHT && y >= 0) {
				raster[x + WIDTH * y] = c.getColor();
			}
		}

		// attempts to draw a point whether or not it's inside the frame. Will 
		// throw arrayIndexOutOfBoundsException, drawPoint() is a safe alternative
		public void drawUncheckedColoredPoint(int x, int y, Color c) {
			raster[x + WIDTH * y] = c.getColor();
		}
	
	// rasterizes a colored line segment from (x0, y0) to (x1, y1) with a smooth
	// gradient from (c0, c1)
	public void drawColoredLine(int x0, int y0, Color c0, int x1, int y1, Color c1){

		// if the entire line is on the screen, drawUnchecked point
		if (x0 < WIDTH && x0 >= 0 && y0 < HEIGHT && y0 >= 0 && x1 < WIDTH
				&& x1 >= 0 && y1 < HEIGHT && y1 >= 0) {

			float dx = x1 - x0;
			float dy = y1 - y0;
			
			// compare dx and dy to see if slope is steep ( slope > .5 )
			if ( ( dy > 0 ? dy : -dy) > (dx > 0 ? dx : -dx ) ){ // slope steep
				
				float xChangePerStep = dx/dy;
				
				if( y0 < y1 ){ // iterate from y0 to y1
					float x = x0;
					
					Color c = c0;
					for( int y = y0; y <= y1; y++ ){
						//c = c0 + (c1.subtract(c0) * ((x - x1) / xdiff)); // PROBLEM: with the whole add dColor at every y position while using shorts for the internal representation 
						// you could end up with a negative color being painted. If you recalculate the actual color every iteration as a function of y, then this is slow
						// if you use an internal representation of floats, then it's slow in a different way. Using floats internally seems preferrable
						
						
						// DO THE FLOAT WAY. ALso, get rid of not color oriented line/point drawing. Also upload to github before doing all of this shit
						drawUncheckedPoint( (int) x, y );
						x += xChangePerStep;
					}
				}else{ // iterate from y1 to y0
					float x = x1;
					for( int y = y1; y <= y0; y++ ){
						drawUncheckedPoint( (int) x, y );
						x += xChangePerStep;
					}
				
				}
				
			} else { // not steep
				
				float yChangePerStep = dy/dx;
				
				if( x0 < x1){ // iterate from x0 to x1
					float y = y0;
					for ( int x = x0; x <= x1; x++ ){
						drawUncheckedPoint( x, (int)y );
						y += yChangePerStep;
					}
				}else{ // iterate from x1 to x0
					float y = y1;
					for ( int x = x1; x <= x0; x++ ){
						drawUncheckedPoint( x, (int)y );
						y += yChangePerStep;
					}
				}
			}
			
		} else { // line segment goes off screen, call drawPoint()
			
			float dx = x1 - x0;
			float dy = y1 - y0;

			// compare dx and dy to see if slope is steep ( slope > .5 )
			if ( ( dy > 0 ? dy : -dy) > (dx > 0 ? dx : -dx ) ){ // slope steep
				
				float xChangePerStep = dx/dy;
				
				if( y0 < y1 ){ // 
					float x = x0;
					for( int y = y0; y <= y1; y++ ){
						drawPoint( (int) x, y );
						x += xChangePerStep;
					}
				}else{
					float x = x1;
					for( int y = y1; y <= y0; y++ ){
						drawPoint( (int) x, y );
						x += xChangePerStep;
					}
				
				}
				
			} else { // not steep
				
				float yChangePerStep = dy/dx;
				
				if( x0 < x1){
					float y = y0;
					for ( int x = x0; x <= x1; x++ ){
						drawPoint( x, (int)y );
						y += yChangePerStep;
					}
				}else{
					float y = y1;
					for ( int x = x1; x <= x0; x++ ){
						drawPoint( x, (int)y );
						y += yChangePerStep;
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	// Make this override drawColoredLine // if worth the code readability improvement
	// Won't ever be making all black lines anyways? Having this individual method may
	// be just as arbitrary as having one for every possible color. perhaps.
	// rasterizes a line segment from (x0, y0) to (x1, y1)
	public void drawLine(int x0, int y0, int x1, int y1) {

		// if the entire line is on the screen, drawUnchecked point
		if (x0 < WIDTH && x0 >= 0 && y0 < HEIGHT && y0 >= 0 && x1 < WIDTH
				&& x1 >= 0 && y1 < HEIGHT && y1 >= 0) {

			float dx = x1 - x0;
			float dy = y1 - y0;
			
			// compare dx and dy to see if slope is steep ( slope > .5 )
			if ( ( dy > 0 ? dy : -dy) > (dx > 0 ? dx : -dx ) ){ // slope steep
				
				float xChangePerStep = dx/dy;
				
				if( y0 < y1 ){ // iterate from y0 to y1
					float x = x0;
					for( int y = y0; y <= y1; y++ ){
						drawUncheckedPoint( (int) x, y );
						x += xChangePerStep;
					}
				}else{ // iterate from y1 to y0
					float x = x1;
					for( int y = y1; y <= y0; y++ ){
						drawUncheckedPoint( (int) x, y );
						x += xChangePerStep;
					}
				
				}
				
			} else { // not steep
				
				float yChangePerStep = dy/dx;
				
				if( x0 < x1){ // iterate from x0 to x1
					float y = y0;
					for ( int x = x0; x <= x1; x++ ){
						drawUncheckedPoint( x, (int)y );
						y += yChangePerStep;
					}
				}else{ // iterate from x1 to x0
					float y = y1;
					for ( int x = x1; x <= x0; x++ ){
						drawUncheckedPoint( x, (int)y );
						y += yChangePerStep;
					}
				}
			}
			
		} else { // line segment goes off screen, call drawPoint()
			
			float dx = x1 - x0;
			float dy = y1 - y0;

			// compare dx and dy to see if slope is steep ( slope > .5 )
			if ( ( dy > 0 ? dy : -dy) > (dx > 0 ? dx : -dx ) ){ // slope steep
				
				float xChangePerStep = dx/dy;
				
				if( y0 < y1 ){ // 
					float x = x0;
					for( int y = y0; y <= y1; y++ ){
						drawPoint( (int) x, y );
						x += xChangePerStep;
					}
				}else{
					float x = x1;
					for( int y = y1; y <= y0; y++ ){
						drawPoint( (int) x, y );
						x += xChangePerStep;
					}
				
				}
				
			} else { // not steep
				
				float yChangePerStep = dy/dx;
				
				if( x0 < x1){
					float y = y0;
					for ( int x = x0; x <= x1; x++ ){
						drawPoint( x, (int)y );
						y += yChangePerStep;
					}
				}else{
					float y = y1;
					for ( int x = x1; x <= x0; x++ ){
						drawPoint( x, (int)y );
						y += yChangePerStep;
					}
				}
			}
		}
	}

}
