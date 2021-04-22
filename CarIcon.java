import javax.swing.ImageIcon;

/**
 * A Car object represented by an image.
 * @author victorshih
 *
 */
public class CarIcon extends ImageIcon {

	// FIELDS
	private ImageIcon c;
	 
	// CONSTRUCTORS
	public CarIcon() {
		java.net.URL res = getClass().getClassLoader().getResource("images/car.png");
		c = new ImageIcon(res);
	}
	
	// METHODS
	public ImageIcon getImg() {
		return c;
	}
	
	public int getWidth() {
		// TODO Auto-generated method stub
		return c.getIconWidth();
	}

	
	public int getHeight() {
		// TODO Auto-generated method stub
		return c.getIconHeight();
	}
	
	  
	
}