import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * Represents the dashed lines in window. Animates to show movement of CarIcon. 
 * @author victorshih
 *
 */
public class BackgroundShape extends JComponent implements KeyListener, Runnable {
	/**
	 * have an array of white rectangles that represent lines on road. If rectangle
	 * is out of frame, remove. Also, constantly create new rectangles in the
	 * direction we are moving.
	 */
	
	// FIELDS
	private ArrayList<DashIcon> dashedLines;
	private double spdX, velY;
	private int accelerating;
	private int accelDELAY;
		
	// CONSTRUCTOR
	public BackgroundShape() {
		dashedLines = new ArrayList<DashIcon>(6);
		accelerating = 0;	
		spdX = 0;
		velY = 0;
		accelDELAY = 0;
		// create dashes to span the entire jframe.
		for (int i = -1; i <= 15; i++) {
			dashedLines.add(new DashIcon(0, 200 * i));
			dashedLines.add(new DashIcon(860, 200 * i));
		}	
	}
	
	public ArrayList<DashIcon> getDashedLines() {
		return dashedLines;
	}
	
	/**
	 * Adds dashes in the direction of movement.
	 * 
	 * @param direction
	 *            is the direction of movement.
	 * @pre direction must be {EAST, WEST, NORTH, SOUTH}.
	 */
	public void addDash() {	
		if (velY > 0) {
			// add dashes to the top
			for (DashIcon i : dashedLines) {
				if (i.getY() >= Tester.h + 200) {
					i.setY(-200);
				}		
			}
		} else if (velY < 0) {
			// add dashes to the bottom.
			for (DashIcon i : dashedLines) {
				if (i.getY() <= -200) {	
					i.setY(Tester.h);
				}
			}
		} else {
		
		}
	}

	/**
	 * Updates position of background depending on speed.
	 */
	public void update() {
		for (int i = 0; i < dashedLines.size(); i++) {
				dashedLines.get(i).translate(spdX, velY);		
		}
	}

	// test accel 
	double accelChange = 0.5; // 60/(this*10) = time to go 0~60
	String pastMoveCmd = "none";
	/**
	 * Handles acceleration of the car (technically the dashes.)
	 */
	public void accel() {
		double spdCap = 60;	
		if (accelerating == 1 && velY + 0.1 < spdCap) {
			if (accelChange < spdCap + 0.1)
				//accelChange += 0.03;
			this.velY += accelChange;
//			System.out.println(accelerating);
			System.out.println(velY);
		} else if (accelerating == 0) {
			// do nothing. Car is coasting.
			if (velY > 0.000001) {
				velY -= 0.135;
			}
			if (velY < -0.000001)
				velY += 0.135;
//			System.out.println(accelerating);
			System.out.println(velY);
		} else if (accelerating == -1 && velY - 0.1 > -spdCap) {
			this.velY -= accelChange;
//			System.out.println(accelerating);
			System.out.println(velY);
		}
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			accelerating = 1;	
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			accelerating = -1;
		}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			spdX = -1;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			spdX = 1;
		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			accelerating = 0;
//		} else if (e.getKeyCode() == KeyEvent.VK_A) {

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

			accelerating = 0;
//		} else if (e.getKeyCode() == KeyEvent.VK_D) {
		
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		for (int i = 0; i < dashedLines.size(); i++) { 		
			g2.fillRect((int)dashedLines.get(i).getX(), (int)dashedLines.get(i).getY(), dashedLines.get(i).getWidth(), dashedLines.get(i).getHeight());		
		}
	}

}