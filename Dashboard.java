import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Dashboard {
    public static void main(String args[]) {
        
    	JFrame f = new JFrame("DashBoard");
        Container p = f.getContentPane();
        p.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        
        Speedometer speed = new Speedometer();
        Tachometer tach = new Tachometer();
        
        f.addKeyListener(tach);
        f.addKeyListener(speed);
        speed.addKeyListener(speed);
        tach.addKeyListener(tach);
        //set the type
 
        speed.setPreferredSize(new Dimension(400,400));
        tach.setPreferredSize(new Dimension(400, 400));
        
        panel.add(tach);
        panel.add(speed);
        p.add(panel, BorderLayout.WEST);
        f.setSize(800,450);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }


}