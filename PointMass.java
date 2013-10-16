
public class PointMass {
	
	public float x, y, xOld, yOld;
	public boolean pinned;
	
	public PointMass( float xPos, float yPos, float xVel, float yVel ){
		
		this.x = xPos;
		this.y = yPos;
		// do it this way because old position's validity is irrelevant
		this.xOld = xPos - xVel;
		this.yOld = yPos - yVel;
		pinned = false;
	}
	
	public void update(){
		
		if(!pinned){
			float xVel = x - xOld;
			float yVel = y - yOld;
			
			float xAcc = 0;
			float yAcc = .3f; // TODO: current implementation of gravity. change so that it's stored in a field, and updated only when world changes
			
			float xNew = x + xVel + xAcc;
			float yNew = y + yVel + yAcc;
			
			xOld = x;
			yOld = y;
			
			x = xNew;
			y = yNew;
			
		}else{
			// pinned
		}
		
	}
	
	public void draw( Render r ){
		r.drawPoint((int)x, (int)y);
	}
	
	public void setPin( boolean b ){
		pinned = b;
	}
	
	// translate the point if it is not pinned
	public void translate( float dx, float dy ){
		if( !pinned ){
			x += dx;
			y += dy;
		}
	}
	
}
