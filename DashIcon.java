/**
 * Represents a single white rectangle which composes the dashed lines.
 * @author victorshih
 *
 */
public class DashIcon {
	// FIELDS \\
	private double x, y;
	private int initX, initY;
	private int width, height;
	
	// CONSTRUCTOR
	public DashIcon(double x, double y) {
		this.x = x;
		this.y = y;
		initX = (int)x;
		initY = (int)y;
		width = 40;
		height = 100;
	}
		
	// METHODS
	/**
	 * Moves this object by x and y amount.
	 * @param dx the amt to move the x coordinate. 
	 * @param dy the amt to move the y coordinate.
	 */
	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}  
	// SETTERS
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	//GETTERS
	public int getInitX() {
		return initX;
	}
	
	public int getInitY() {
		return initY;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

}