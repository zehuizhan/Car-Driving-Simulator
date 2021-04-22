import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ClutchShifter extends JFrame implements KeyListener {
	
	private String s;
	JLabel l = new JLabel();
	
	ClutchShifter() {

//		this.addKeyListener(this);
//		this.setBounds(200, 200, 500, 500);
//		center(this);
//		this.setVisible(true);
//		//this.setLocationRelativeTo(null);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		BorderLayout b = new BorderLayout();
//		this.setLayout(b);
		s = "N";
		l.setText(s);
		l.setFont(new Font("Times New Roma", Font.BOLD,36));
//		this.add(l, BorderLayout.CENTER);
		
		
	}
	
	public void center(Component c) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		int x = (kit.getScreenSize().width - c.getWidth()) / 2;
		int y = (kit.getScreenSize().height - c.getHeight()) / 2;
		c.setLocation(x, y);
	}

	public void keyPressed(KeyEvent e) {
		// TODO 自动生成方法存根
		if (e.getKeyCode() == KeyEvent.VK_1 && e.isShiftDown()) {
			s = "1";
			l.setText(s);
			System.out.println(s);
		}
		if (e.getKeyCode() == KeyEvent.VK_2 && e.isShiftDown()) {
			s = "2";
			l.setText(s);
			System.out.println(s);
		}
		if (e.getKeyCode() == KeyEvent.VK_3 && e.isShiftDown()) {
			s = "3";
			l.setText(s);
			System.out.println(s);
		}
		if (e.getKeyCode() == KeyEvent.VK_4 && e.isShiftDown()) {
			s = "4";
			l.setText(s);
			System.out.println(s);
		}
		if (e.getKeyCode() == KeyEvent.VK_5 && e.isShiftDown()) {
			s = "5";
			l.setText(s);
			System.out.println(s);
		}
		if (e.getKeyCode() == KeyEvent.VK_R && e.isShiftDown()) {
			s = "R";
			l.setText(s);
			System.out.println(s);
		}
		if (e.getKeyCode() == KeyEvent.VK_N && e.isShiftDown()) {
			s = "N";
			l.setText(s);
			System.out.println(s);
		}
	}
	
	public String getGear() {
		return s;
	}
	
	public JLabel getLabel() {
		return l;
	}

	public static void main(String[] args) {
		new ClutchShifter();
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO 自动生成方法存根
	}

	public void keyTyped(KeyEvent e) {
		// TODO 自动生成方法存根
	}
}
