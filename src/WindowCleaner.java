/****************************************************************
 * ENSC 100: Window Cleaner Simulation
 * Group: Zeta
 * @author Samantha Yu
 ****************************************************************/

import java.awt.BasicStroke;
import java.awt.Frame;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.util.ArrayList;
import java.lang.Thread;

public class WindowCleaner extends Frame implements WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graphics2D g2Frame; 
	private static ArrayList<BufferedImage> layerArray;
	private int appWidth, appHeight;
	
	private BufferedImage wcImage;
	private Graphics2D g2winCleaner;
	
	private Building building;
	private ChemicalSpray cspray;
	private Dolly dolly;
	private SuctionCups scups;
	private WaterPump wpump;
	private WaterTank wtank;
	private StatusBar sbar;
	
	private int wcWidth, wcHeight, wcStartX, wcStartY, delay, nextWCstartX;
	private Color wcColor;
	private boolean moveDown, moveRight;
	
	public static void main(String args[]) {
		 WindowCleaner winCleaner = new WindowCleaner();
		 winCleaner.addWindowListener(winCleaner);
		 winCleaner.createWinCleaner(winCleaner);
		 winCleaner.createImageArray();
		 winCleaner.setVisible(true);
		 
		 winCleaner.wcMove();
	}
	
	public WindowCleaner() {
		appWidth = 1366;
		appHeight = 725;
		setSize(appWidth, appHeight);
	}
	
	public void createWinCleaner(WindowCleaner winCleaner) {
		sbar = new StatusBar(winCleaner);
		
		building = new Building(winCleaner, sbar);
		cspray = new ChemicalSpray(winCleaner);
		dolly = new Dolly(winCleaner);
		scups = new SuctionCups(winCleaner);
		wpump = new WaterPump(winCleaner);
		wtank = new WaterTank(winCleaner);
		
		wcStartX = building.getBuildingStartX() + scups.getArmsLength() + scups.getSCupsDiameter();
		wcStartY = building.getBuildingStartY();
			
		createWinFrame();
	}
	
	public void wcMove() {
		delay = 200;
		moveDown = true;
		moveRight = false;
		
		do {
			if (moveRight) {
				wcMoveRight();
				moveRight = false;
				moveDown = false;
			}
			
			if (moveDown) {
				wcMoveDown();
				moveRight = true;
			} else {
				wcMoveUp();
				moveDown = true;
			}
		} while(getWCstartX() + building.getWindowWidth()/4 <= building.getBuildingWidth());
	}
	
	private void wcMoveDown() {
		do {
			g2winCleaner.setBackground(new Color(255, 255, 255, 0));
			g2winCleaner.clearRect(0, 0, getAppWidth(), getAppHeight());
			
			wcStartY = getWCstartY() + getWCheight()/2;
			drawWinFrame();
			
			try {
				Thread.sleep(delay);
				System.out.println("time");
			} catch (InterruptedException ie) {
				System.out.println("Timer was interrupted");
			}
			
			repaint();
		} while (getWCstartY() <= building.getBuildingHeight());
	}
	
	private void wcMoveUp() {
		do {
			g2winCleaner.setBackground(new Color(255, 255, 255, 0));
			g2winCleaner.clearRect(0, 0, getAppWidth(), getAppHeight());
			
			wcStartY = getWCstartY() - getWCheight()/2;
			drawWinFrame();
			
			try {
				Thread.sleep(delay);
				System.out.println("time");
			} catch (InterruptedException ie) {
				System.out.println("Timer was interrupted");
			}
			
			repaint();
		} while (getWCstartY() - getWCheight()/2 >= building.getBuildingStartY());
	}
	
	private void wcMoveRight() {
		nextWCstartX = getWCstartX() + building.getWindowWidth();
		
		do {
			g2winCleaner.setBackground(new Color(255, 255, 255, 0));
			g2winCleaner.clearRect(0, 0, getAppWidth(), getAppHeight());
			
			wcStartX = getWCstartX() + building.getWindowWidth()/4;
			drawWinFrame();
			
			try {
				Thread.sleep(delay);
				System.out.println("time");
			} catch (InterruptedException ie) {
				System.out.println("Timer was interrupted");
			}
			
			repaint();
		} while (getWCstartX() + building.getWindowWidth()/4 <= nextWCstartX);
	}
	
	private void createWinFrame() {
		wcImage = new BufferedImage(getAppWidth(), getAppHeight(), BufferedImage.TYPE_INT_ARGB);
		g2winCleaner = (Graphics2D) wcImage.getGraphics();
		
		setWCdimensions();
		drawWinFrame();
	}
	
	private void setWCdimensions() {
		wcColor = new Color(3);
		wcWidth = building.getWindowWidth() - 2*(scups.getArmsLength() + scups.getSCupsDiameter());
		wcHeight = 40;
	}
	
	private void drawWinFrame() {
		g2winCleaner.setColor(wcColor);
		g2winCleaner.fillRect(getWCstartX(), getWCstartY(), wcWidth, wcHeight);
	}
	
	public void createImageArray() {
		layerArray = new ArrayList<BufferedImage>();
		layerArray.add(building.getBuildingImage());
		layerArray.add(cspray.getCSprayImage());
		layerArray.add(dolly.getDollyImage());
		layerArray.add(scups.getSCupsImage());
		layerArray.add(wpump.getWPumpImage());
		layerArray.add(wtank.getWTankImage());
		layerArray.add(sbar.getSBarImage());
	}
	
	public int getAppWidth() {
		return appWidth;
	}
	
	public int getAppHeight() {
		return appHeight;
	}
	
	public int getWCstartX() {
		return wcStartX;
	}
	
	public int getWCstartY() {
		return wcStartY;
	}
	
	public int getWCwidth() {
		return wcWidth;
	}
	
	public int getWCheight() {
		return wcHeight;
	}
	
	public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0); // Normal exit of program
    }
	
	 // These window events are required to be implemented, because this class implements the WindowListener
    // However, this window cleaner simulation does not need these window events so these methods are left blank
    public void windowOpened(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){}
	
	public void paint(Graphics g) {
		System.out.println("hi");
		g2Frame = (Graphics2D) g;
		g2Frame.drawImage(building.getBuildingImage(), 0, 0, this);
		g2Frame.drawImage(wcImage, 0, 0, this);
		g2Frame.drawImage(sbar.getSBarImage(), 0, 0, this);
	}
	 
	public void update(Graphics g) {
		paint(g);
	}
}
