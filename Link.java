
public class Link {

	public PointMass p0;
	public PointMass p1;
	
	private int restingDistance;
	private int tearingDistance;
	
	public Link( PointMass p0, PointMass p1, int linkLength, int linkStrength ){
		
		this.p0 = p0;
		this.p1 = p1;
		restingDistance = linkLength;
		tearingDistance = linkLength*linkStrength;
		
	}
	
	public boolean solve() {
	
		float distX = p0.x - p1.x;
		float distY = p0.y - p1.y;
		
		float linearDist = sqrt( distX*distX + distY*distY );
		float difference = linearDist > 0 ? (restingDistance-linearDist)/linearDist : 1; // 1 is a fallback if dist == 0
		
		float translateX = distX * .6f * difference;
		float translateY = distY * .6f * difference;
		
		// if one point is pinned, the other needs to translate more to stay in line
		// if both are pinned, it doesn't matter, but this is simplest
		if( p0.pinned || p1.pinned ){
			translateX*=2;
			translateY*=2;
		}
		
		p0.translate( translateX, translateY );
		p1.translate( -translateX, -translateY );
		
		return linearDist > tearingDistance; // shouldTear
		
	}
	
	public void draw( Render r ){
		r.drawLine( (int)p0.x, (int)p0.y, (int)p1.x, (int)p1.y);
	}
	
	// calculates the sqrt of a number
	public float sqrt( float x ){
		return x * Float.intBitsToFloat( 0x5f3759d5 - (Float.floatToIntBits(x) >> 1));
	}
	
	// triangle is defined by three points, and made up by three links which are
	// each made up of 2 points
	
	// PointMasses constructed first, then links, then triangles
	
}
