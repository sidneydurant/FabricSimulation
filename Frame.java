import java.awt.Canvas;
import javax.swing.*;

public class Frame extends Canvas {

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1200;
	public static final int TICK = 16;

	public static boolean paused = false;

	public static void main(String[] args) {

		final JFrame frame = new JFrame("");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setFocusable(true);

		final Simulation sim = new Simulation(WIDTH, HEIGHT);
		frame.add(sim);

		frame.setVisible(true);

		Thread runThread = new Thread(new Runnable() {
			public void run() {
				try{
					Thread.sleep( 20 ); // sleep to let the frame set up
				} catch ( Exception e ){
					// uncaught exception
				}
				while (true) {
					long time = System.currentTimeMillis();

					sim.repaint();

					try{Thread.sleep( 20 ); }catch(Exception e ){} // The problem is that without sleeping here, it calls repaint and then moves on to tick////////////////////
					// which modifies the links while it's still trying to paint them
					
					// another problem is that if the framerate is different the "strength" of drawing finger is greater,
					// Maybe fix the timestep and 'solve' only the needed amount of time, and only 'update' with accel the lerped value
					
					sim.tick();
					// PROBLEM: this does not wait for it to paint, it calls repaint and moves on///////////////////////////////////////////////////////////////////////////////

					long endTime = System.currentTimeMillis();
					try {
						Thread.sleep( 10/*TICK - endTime + time*/ );///////////////////////////////////////////////////////////////////////////////////////////////////////////
					} catch ( Exception e ) {
						System.out.println("cannot sleep: " + (TICK - (endTime - time)) );
					}
				}
			}
		});

		runThread.start();

	}
}