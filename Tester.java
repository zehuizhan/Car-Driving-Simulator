import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class Tester {

	// FIELDS
	static int frameInitialWidth = 2000;
	static int frameInitialHeight = 600;
	static JFrame f;
	static Rectangle bounds;
	static int h, w;
	
	// MAIN
	public static void main(String[] args) {
		f = new JFrame();	
        Container p = f.getContentPane();
        p.setLayout(new BorderLayout());
		//works
		// Creates road.
		BackgroundShape background = new BackgroundShape();		
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setOpaque(false);
		backgroundPanel.setLayout(new BorderLayout());
		backgroundPanel.add(background, BorderLayout.CENTER);
		// Creates car.
		CarIcon cc = new CarIcon();
		ImageIcon c = cc.getImg();
		JLabel clabel = new JLabel(c);
		JPanel cpanel = new JPanel();
		cpanel.setOpaque(false);
		cpanel.setLayout(new BorderLayout());
		cpanel.add(clabel);
		// Combines road and car with overlapping.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new OverlayLayout(mainPanel));
		mainPanel.add(cpanel);
		mainPanel.add(backgroundPanel);
		mainPanel.setOpaque(false);
		
		f.add(mainPanel, BorderLayout.CENTER);		
		f.addKeyListener(background);	 
		
		f.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent c) {
				bounds = f.getBounds();
				h = bounds.height;
				w = bounds.width;
				System.out.println("height: " + h + "width: " + w);
				
				for (int i = 0; i < background.getDashedLines().size(); i++) {
					background.getDashedLines().get(i).setX(background.getDashedLines().get(i).getInitX() + (w - frameInitialWidth)/2);
				}
			}
		});
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        JPanel panel = new JPanel();
        
        Speedometer speed = new Speedometer();
        Tachometer tach = new Tachometer();
        ClutchShifter clutch = new ClutchShifter();
        
        f.addKeyListener(tach);
        f.addKeyListener(speed);
        f.addKeyListener(clutch);
        speed.addKeyListener(speed);
        tach.addKeyListener(tach);
        clutch.addKeyListener(clutch);
        //set the type
 
        speed.setPreferredSize(new Dimension(400,400));
        tach.setPreferredSize(new Dimension(400, 400));
        
        panel.add(tach, BorderLayout.NORTH);
        panel.add(speed, BorderLayout.NORTH);
        panel.add(clutch.getLabel(), BorderLayout.SOUTH);
        p.add(panel, BorderLayout.WEST);
        f.setSize(5000,450);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		
		f.setPreferredSize(new Dimension(900, 600));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		bounds = f.getBounds();
		h = bounds.height;
		w = bounds.width;
		
		// THREADING
//		ExecutorService service = Executors.newFixedThreadPool(1);
		ExecutorService service = Executors.newFixedThreadPool(2);
		int delay = 7;
		Runnable r1 = () -> {		
			while (true) {
				background.update();
				background.addDash();
				background.repaint();
				
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		//test runnable
		int delay2 = 100; //100 ms
		Runnable r2 = () -> {		
			while (true) {
				background.accel();
				
				try {
					Thread.sleep(delay2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		//
		
		service.execute(r1);
		//teset runnable
		service.execute(r2);
		service.shutdown();		
	}
}