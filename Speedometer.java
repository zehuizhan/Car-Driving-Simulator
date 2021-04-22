import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Speedometer extends JComponent implements KeyListener, Runnable{

    private static final int VALUE_FONT_SIZE = 18;

    public static final String LINE = "line";
    public static final String ARC = "arc";
    public static final String CIRCLE = "circle";
    public static final String  SEMI_CIRCLE = "semi_circle";

    public static final double CIRCLE_ANGLE = 360;
    public static final int ARC_ANGLE_INDEX = 3;
    public static final int SEMI_ANGLE_INDEX = 11;
    public static final double CIRCLE_ANGLE_START = Math.PI / 2;

    private double from = 0;
    private double to = 200;

    private String type = "circle";

    //鏈�澶у埢搴︼紝鏈�灏忓埢搴�
    private double major = 10;
    private double minor = 1;

    //褰撳墠鎸囬拡鎸囧悜鐨勪綅缃�
    private String value = "0";

    //鍗曚綅
    private String unit = "MPH";

    private double width;
    private double height;

    private Graphics2D g2;
    private int fontSize;

    public Speedometer() {
        super();
        this.setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g) {
        width = getWidth();
        height = getHeight();
        g2 = graphicsConfig(g);
        drawArcAndCircle();
        drawArcAndCircleText();
        
    }

    private Graphics2D graphicsConfig(Graphics g) {
        fontSize = 14;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.getBackground());
        g2.fillRect(0, 0, (int) width, (int) height);
        g2.setColor(this.getForeground());
        g2.setStroke(new BasicStroke(1));
        g2.setFont(new Font(Font.SERIF, Font.PLAIN, fontSize));
        return  g2;
    }

    private void drawArcAndCircle() {
        double angle = type.equals(CIRCLE) ? CIRCLE_ANGLE : getArcAngle();
        double startAngle = type.equals(CIRCLE) ? CIRCLE_ANGLE_START : getArcStartAngle(angle);
        double r = getRadius(angle);
        double yOffset = angle <= 180 ? 0 : r;
        double dunit = getDunit(angle);

        drawArcScale(startAngle, dunit, r, yOffset);
        drawArcValue(startAngle, dunit, r, yOffset, angle);
    }

    private double getDunit(double angle) {
        if((to - from) != 0)
            return (angle / 180 * Math.PI) / (to - from);
        return 1;
    }

    private double getRadius(double angle){
            return angle <= 180 ? Math.min(width / 2, height) : Math.min(width / 2, height / 2);
    }

    private double getArcAngle() {
        return toDouble(type.substring(ARC_ANGLE_INDEX));
    }

    private double getArcStartAngle(double angle) {
        return (180 + (angle - 180) / 2) / 180 * Math.PI;
    }

    private void drawArcScale(double startAngle, double dunit,  double r, double yOffset) {
        for(int i = 0; i <= (to - from) / major; i++) {
            g2.setColor(Color.BLACK);
            g2.draw(getArcMajorLine(startAngle, dunit, r, yOffset, i));
            if(minor > 0 && i < (to - from) / major) {
                for (int j = 1; j < major/minor; j++) {
                    g2.setColor(Color.BLACK);
                    g2.draw(getArcMinorLine(startAngle, dunit, r, yOffset, i, j));
                }
            }
        }
    }

    private Line2D getArcMajorLine(double startAngle, double dunit, double r, double yOffset, int num) {
        double x1 = Math.cos(startAngle - num * major * dunit) * r + width / 2;
        double y1 = height - yOffset - Math.sin(startAngle - num * major * dunit) * r;
        double x2 = Math.cos(startAngle - num * major * dunit) * r * 0.75 + width / 2;
        double y2 = height - yOffset - Math.sin(startAngle - num * major * dunit) * r * 0.75;
        return new Line2D.Double(x1, y1, x2, y2);
    }

    private Line2D getArcMinorLine(double startAngle, double dunit, double r, double yOffset, int num1, int num2) {
        double x1 = Math.cos(startAngle - (num1 * major + num2 * minor) * dunit) * r + width / 2;
        double y1 = height - yOffset - Math.sin(startAngle - (num1 * major + num2 * minor) * dunit) * r;
        double x2 = Math.cos(startAngle - (num1 * major + num2 * minor) * dunit) * r * 0.875 + width / 2;
        double y2 = height - yOffset - Math.sin(startAngle - (num1 * major + num2 * minor) * dunit) * r * 0.875;
        return new Line2D.Double(x1, y1, x2, y2);

    }

    private void drawArcValue(double startAngle, double dunit, double r, double yOffset, double angle) {
        if (value.length() > 0) {
            double val = toDouble(value);
            GeneralPath p = new GeneralPath();
            p.moveTo(Math.cos(startAngle - (val - from) * dunit) * r * 0.875 + width / 2, height - yOffset - Math.sin(startAngle - (val - from) * dunit) * r * 0.875);
            p.lineTo(Math.cos(startAngle - (val - from) * dunit + Math.PI * 0.5) * 2 + width / 2, height - yOffset - Math.sin(startAngle - (val - from) * dunit + Math.PI * 0.5) * 2);
            p.lineTo(Math.cos(startAngle - (val - from) * dunit - Math.PI * 0.5) * 2 + width / 2, height - yOffset - Math.sin(startAngle - (val - from) * dunit - Math.PI * 0.5) * 2);
            p.closePath();
            g2.setColor(Color.RED);
            g2.fill(p);
            g2.setFont(new Font("", Font.BOLD, VALUE_FONT_SIZE));
            yOffset = angle <= 180 ? 10 : r - fontSize / 2;
            drawValue(g2, value + unit, (int) (width / 2 - getStrBounds(g2, value).getWidth() / 2), (int) (height - yOffset));
        }
    }

    private void drawArcAndCircleText() {
        double angle;
        double startAngle;
        g2.setColor(Color.BLACK);
        if (type.equals(CIRCLE)) {
            angle = 360;
            startAngle = Math.PI / 2;
        } else {
            angle = toDouble(type.substring(3));
            startAngle = (180 + (angle - 180) / 2) / 180 * Math.PI;
        }
        double r = angle <= 180 ? Math.min(width / 2, height) : Math.min(width / 2, height / 2);
        double yOffset = angle <= 180 ? 0 : r;
        double dunit = (angle / 180 * Math.PI) / (to - from);
        int xoff = 0;
        int yoff = 0;
        double strAngle;
        for (int i = type.equals(CIRCLE) ? 1 : 0; i <= (to - from) / major; i++) {
            String str;
            str = format(from + i * major);
            strAngle = (startAngle - i * major * dunit + Math.PI * 2) % (Math.PI * 2);
            xoff = 0;
            yoff = 0;
            if (strAngle >= 0 && strAngle < Math.PI * 0.25) {
                xoff = (int) -getStrBounds(g2, str).getWidth();
                yoff = fontSize / 2;
                if (strAngle == 0 && angle == 180) {
                    yoff = 0;
                }
            } else if (near(strAngle, Math.PI * 0.5)) {
                xoff = (int) -getStrBounds(g2, str).getWidth() / 2;
                yoff = fontSize;
            } else if (strAngle >= Math.PI * 0.25 && strAngle < Math.PI * 0.5) {
                xoff = (int) -getStrBounds(g2, str).getWidth();
                yoff = fontSize;
            } else if (strAngle >= Math.PI * 0.5 && strAngle < Math.PI * 0.75) {
                yoff = fontSize;
            } else if (strAngle >= Math.PI * 0.75 && strAngle < Math.PI) {
                yoff = fontSize / 2;
            } else if (near(strAngle, Math.PI)) {
                xoff = 1;
                yoff = fontSize / 2;
                if (angle == 180) {
                    yoff = 0;
                }
            } else if (strAngle >= Math.PI && strAngle < Math.PI * 1.25) {
                yoff = fontSize / 4;
            } else if (near(strAngle, Math.PI * 1.5)) {
                xoff = (int) -getStrBounds(g2, str).getWidth() / 2;
            } else if (strAngle >= Math.PI * 1.5 && strAngle < Math.PI * 2) {
                xoff = (int) -getStrBounds(g2, str).getWidth();
            }
            g2.drawString(str, (int) (Math.cos(strAngle) * r * 0.75 + width / 2) + xoff, (int) (height - yOffset - Math.sin(strAngle) * r * 0.75) + yoff);
        }

    }

    private void drawValue(Graphics2D g2, String value, int x, int y) {
        g2.setFont(new Font(Font.SERIF, Font.BOLD, VALUE_FONT_SIZE));
        g2.setColor(Color.BLACK);
        g2.drawString(value, x, y);
    }

    private String format(double d) {
        if ((int) d == d) {
            return String.valueOf((int) d);
        } else {
            return String.valueOf(d);
        }
    }

    private double toDouble(String string) {
        try {
            return Double.valueOf(string);
        } catch (Exception e) {
            return 0;
        }
    }

    private boolean near(double d1, double d2) {
        return Math.round(d1 * 1000000) == Math.round(d2 * 1000000);
    }

    private static Rectangle2D getStrBounds(Graphics2D g, String str) {
        Font f = g.getFont();
        Rectangle2D rect = f.getStringBounds(str, g.getFontRenderContext());
        if (rect.getHeight() < f.getSize()) {
            rect.setFrame(rect.getX(), rect.getY(), rect.getWidth(), f.getSize() + 1);
        }
        return rect;
    }


    public void setValue(String value) {
        if(value == null)
            this.value = "0";
        else    this.value = value;
    }

    public boolean speedCorrect() {
    	return Integer.parseInt(value)>=0;
    }
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	boolean isAcc = false;
	@Override
	public void keyPressed(KeyEvent e) {
		int result = Integer.parseInt(value);
//		if(result>=30) {
//			setValue("30");
//			return;
//		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			result+=5;
			setValue(Integer.toString(result));
			isAcc = true;
        }
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(value.equals("0")) {
				return;
			}
			else if(speedCorrect()) {
				result-=1;
				setValue(Integer.toString(result));
			}
        }
        repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		final int DELAY = 80;
        if(e.getKeyCode() == KeyEvent.VK_UP) {
        	isAcc = false;
        	ExecutorService service = Executors.newFixedThreadPool(1);
        	Runnable r1=() -> {
        		while(true) {
        			int result = Integer.parseInt(value);
      				result-=1;
      				value = Integer.toString(result);
      				setValue(value);
      	            repaint();
					if(result<=0) {
      	            	setValue("0");
      	            	return;
      	            }
					else if(isAcc) {
						return;
					}
      	            try {
      	            	Thread.sleep(DELAY);
      	            }catch(InterruptedException e1) {
      	            	e1.printStackTrace();
      	            }
        		}
        	};
        	service.execute(r1);
        	service.shutdown();
        }
        
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}