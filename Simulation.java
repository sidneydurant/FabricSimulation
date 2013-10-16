import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Simulation extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

	private static int WIDTH;
	private static int HEIGHT;

	private static BufferedImage simulationImage;
	private static int[] simulationRaster;

	private Fabric fabric;
	private Render ren;

	public Simulation(int width, int height) {

		addKeyListener(this);
	    addMouseListener(this);
	    addMouseMotionListener(this);
		
		WIDTH = width;
		HEIGHT = height;

		simulationImage = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_ARGB);
		simulationRaster = ((DataBufferInt) simulationImage.getRaster().getDataBuffer()).getData();

		ren = new Render(WIDTH, HEIGHT, simulationRaster);

		init();
	}

	public void init(){

		int vertexCountX = 172/2;
		int vertexCountY = 80/2;
		int linkLength = 10*2;
		int linkStrength = 6/2;

		int xPos = 100;
		int yPos = 100;
		
		fabric = new Fabric( xPos, yPos, vertexCountX, vertexCountY, linkLength, linkStrength, ren);

	}
	
	public void tick() {
		fabric.tick();
	}

	public void paint(Graphics g) {

		ren.clear();
		fabric.draw( ren );
		
		g.drawImage(simulationImage, 0, 0, WIDTH, HEIGHT, null);

	}

	public int oldX, oldY ;
	
	public void mouseDragged(MouseEvent me) {
		fabric.drag( me.getX(), me.getY(), oldX, oldY );
		
		oldX = me.getX();
		oldY = me.getY();
		
	}

	public void mouseMoved(MouseEvent me) {
		oldX = me.getX();
		oldY = me.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		requestFocus();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if( e.getKeyChar() == 'r' ){
			init();
		}
		
	}

}
