
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
			Color dc = c1.minus(c0);
			
			// compare dx and dy to see if slope is steep ( slope > .5 )
			if ( ( dy > 0 ? dy : -dy) > (dx > 0 ? dx : -dx ) ){ // slope steep
				
				float xChangePerStep = dx/dy;
				Color cChangePerStep = dc.times(1/dy);
				
				if( y0 < y1 ){ // iterate from y0 to y1
					float x = x0;
					Color c = new Color(c0);
					for( int y = y0; y <= y1; y++ ){
						
						drawUncheckedColoredPoint( (int) x, y, c );
						x += xChangePerStep;
						c.add(cChangePerStep);
					}
				}else{ // iterate from y1 to y0
					float x = x1;
					Color c = new Color(c1);
					for( int y = y1; y <= y0; y++ ){
						drawUncheckedColoredPoint( (int) x, y, c );
						x += xChangePerStep;
						c.add(cChangePerStep);
					}
				
				}
				
			} else { // not steep
				
				float yChangePerStep = dy/dx;
				Color cChangePerStep = dc.times(1/dx);
				
				if( x0 < x1){ // iterate from x0 to x1
					float y = y0;
					Color c = new Color(c0);
					for ( int x = x0; x <= x1; x++ ){
						drawUncheckedColoredPoint( x, (int)y, c );
						y += yChangePerStep;
						c.add(cChangePerStep);
					}
				}else{ // iterate from x1 to x0
					float y = y1;
					Color c = new Color(c1);
					for ( int x = x1; x <= x0; x++ ){
						drawUncheckedColoredPoint( x, (int)y, c );
						y += yChangePerStep;
						c.add(cChangePerStep);
					}
				}
			}
			
		} else { // line segment goes off screen, call drawPoint()

			float dx = x1 - x0;
			float dy = y1 - y0;
			Color dc = c1.minus(c0);
			
			// compare dx and dy to see if slope is steep ( slope > .5 )
			if ( ( dy > 0 ? dy : -dy) > (dx > 0 ? dx : -dx ) ){ // slope steep
				
				float xChangePerStep = dx/dy;
				Color cChangePerStep = dc.times(1/dy);
				
				if( y0 < y1 ){ // iterate from y0 to y1
					float x = x0;
					Color c = new Color(c0);
					for( int y = y0; y <= y1; y++ ){
						
						drawColoredPoint( (int) x, y, c );
						x += xChangePerStep;
						c.add(cChangePerStep);
					}
				}else{ // iterate from y1 to y0
					float x = x1;
					Color c = new Color(c1);
					for( int y = y1; y <= y0; y++ ){
						drawColoredPoint( (int) x, y, c );
						x += xChangePerStep;
						c.add(cChangePerStep);
					}
				
				}
				
			} else { // not steep
				
				float yChangePerStep = dy/dx;
				Color cChangePerStep = dc.times(1/dx);
				
				if( x0 < x1){ // iterate from x0 to x1
					float y = y0;
					Color c = new Color(c0);
					for ( int x = x0; x <= x1; x++ ){
						drawColoredPoint( x, (int)y, c );
						y += yChangePerStep;
						c.add(cChangePerStep);
					}
				}else{ // iterate from x1 to x0
					float y = y1;
					Color c = new Color(c1);
					for ( int x = x1; x <= x0; x++ ){
						drawColoredPoint( x, (int)y, c );
						y += yChangePerStep;
						c.add(cChangePerStep);
					}
				}
			}
			
		}
	}
	
}
