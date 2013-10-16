
import java.util.LinkedList;
import java.util.Random;

/* @author Sidney Durant
 * This class simulates a fabric composed of a grid like arrangement of points
 * each point is connected to four neighbors, and with every tick each spring
 * attempts to return to its resting length
 */

public class Fabric {

	LinkedList<Link> allLinks;
	LinkedList<PointMass> allPointMasses;
	
	public Fabric( int x, int y, int pointMassCountX, int pointMassCountY, int linkLength, int linkStrength, Render ren ){
		
		// construct pointMasses in an array, and then copy into allPointMasses
		PointMass[][] pointMassArray = new PointMass[pointMassCountX][pointMassCountY];
		
		int xPos;
		int yPos;
	
		Random r = new Random();
		
		for ( int xi = 0; xi < pointMassCountX; xi++ ){
			for( int yi = 0; yi < pointMassCountY; yi++ ){
				xPos = x + (linkLength*xi);
				yPos = y + (linkLength*yi);
				
				pointMassArray[xi][yi] = new PointMass( xPos, yPos, 0, 0, new Color( r.nextInt() ) );
				if( yi == 0 ){
					pointMassArray[xi][yi].setPin( true );
				}
			}
		}
		
		allPointMasses = new LinkedList<PointMass>();
		
		for( PointMass[] subArray : pointMassArray ){
			for( PointMass p : subArray ){
				allPointMasses.add(p);
			}
		}
		
		allLinks = new LinkedList<Link>();
		// construct all of the links and add them to the list of allLinks
		for ( int xi = 0; xi < pointMassCountX; xi++ ){
			for ( int yi = 0; yi < pointMassCountY; yi++ ){
				
				if( xi != 0 ){
					allLinks.add( new Link( pointMassArray[xi][yi], pointMassArray[xi-1][yi], linkLength, linkStrength ) );
				}
				if( yi != 0 ){
					allLinks.add( new Link( pointMassArray[xi][yi], pointMassArray[xi][yi-1], linkLength, linkStrength ) );
				}
				
			}
		}
	}
	
	public void tick(){
		
		for( PointMass p : allPointMasses ){
			p.update();
		}

		LinkedList<Link> allTornLinks = new LinkedList<Link>();
		
		int relaxationCount = 15; // number of times to relax the fabric
		
		for( int i = 1; i <= relaxationCount; i++ ){
			for( Link l : allLinks){
				// relax, then if link is torn, and this is the last relaxation
				// prepare to remove the link
				if ( l.solve() && i == relaxationCount ){
					allTornLinks.add(l);
				}
			}
		}
		
		// remove all torn links
		for( Link tornLink : allTornLinks){
			allLinks.remove( tornLink );
		}
		
		
	}
	
	public void draw( Render r ){
		for( Link l : allLinks){
			l.draw( r );
		}
	}
	
	public void drag( int x, int y, int xOld, int yOld ){
		
		float dx = x-xOld;
		float dy = y-yOld;
		float distanceSquared = 0;
		
		for( PointMass p : allPointMasses ){
			// if distance between p and x,y < certain value;
			distanceSquared = (x-p.x)*(x-p.x) + (y-p.y)*(y-p.y);
			if( distanceSquared < 900 ){
				p.translate( dx, dy );
			} else if ( distanceSquared < 1600 ){
				p.translate( dx/2, dy/2);
			}
			
		}
		
	}
}
