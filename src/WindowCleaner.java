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

import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import java.awt.Toolkit;

public class WindowCleaner extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graphics2D g2Frame; 
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
	
	private int wcWidth, wcHeight, wcStartX, wcStartY, nextWCstartX, currentWinX, displacement;
	private Color wcColor;
	private boolean moveUp, moveDown, moveLeft, moveRight, firstMoveRight;
	
	public static void main(String args[]) {
		WindowCleaner winCleaner = new WindowCleaner();
		winCleaner.createWinCleaner(winCleaner);
		winCleaner.setWCdimensions();
		winCleaner.drawWinCleaner();
	}
	
	public WindowCleaner() {
		appWidth = 1366;
		appHeight = 725;
		this.setSize(appWidth, appHeight);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.createBufferStrategy(2);
	}
	
	private void createWinCleaner(WindowCleaner winCleaner) {
		sbar = new StatusBar(winCleaner);
		
		building = new Building(winCleaner, sbar);
		cspray = new ChemicalSpray(winCleaner);
		dolly = new Dolly(winCleaner);
		scups = new SuctionCups(winCleaner);
		wpump = new WaterPump(winCleaner);
		wtank = new WaterTank(winCleaner);
		
		wcStartX = building.getBuildingStartX() + 2*building.getPaneWidth() + scups.getArmsLength() + scups.getSCupsDiameter();
		wcStartY = building.getBuildingStartY();
	}
	
	private void wcMove() {
		System.out.println("currentWinX: " + currentWinX);
		if (moveUp) {
			wcMoveUp();
			if (getWCstartY() - displacement < building.getBuildingStartY()) {
				moveUp = false;
				moveRight = true;
				firstMoveRight = true;
				currentWinX += 1;
			}
		}
		else if (moveDown) {
			wcMoveDown();
			
			if (getWCstartY() + displacement >= getAppHeight() - getWCheight()) {
				moveDown = false;
				moveUp = true;
			}
		}
		else if (moveRight) {
			wcMoveRight();
			
			if (firstMoveRight) {
				nextWCstartX = getWCstartX() + building.getWindowWidth();
				firstMoveRight = false;
			}
			
			if (getWCstartX() + displacement > nextWCstartX) {
				moveRight = false;
				moveDown = true;
			}
		}
		else if (moveLeft) {
			wcMoveLeft();
			
			if (getWCstartX() - building.getWindowWidth()/2 <= building.getBuildingStartX()) {
				moveLeft = false;
			}
		}
	}
	
	private void wcMoveDown() {
		wcStartY = getWCstartY() + displacement;
	}
	
	private void wcMoveUp() {
		wcStartY = getWCstartY() - displacement;
	}
	
	private void wcMoveLeft() {
		wcStartX = getWCstartX() - displacement;
	}

	private void wcMoveRight() {
		wcStartX = getWCstartX() + displacement;
	}
	
	private void drawWinCleaner() {
		do {
			do {
				BufferStrategy bf = this.getBufferStrategy();
				Graphics2D g2 = null;
				
				try {
					g2 = (Graphics2D) bf.getDrawGraphics();
					
					this.wcMove();
					this.drawWinFrame(g2);
				}
				finally {
					g2.dispose();	// It is best to dispose() a Graphics object when done with it.
				}
			 
				bf.show();	// Shows the contents of the backbuffer on the screen.
		 
		        Toolkit.getDefaultToolkit().sync();	//Tell the System to do the Drawing now, otherwise it can take a few extra ms until drawing is done which looks very jerky
			} while (currentWinX <= building.getNumWinX());
			
			moveDown = false;
			moveRight = false;
			moveUp = false;
		} while (moveLeft);
	}
	
	private void setWCdimensions() {
		wcColor = new Color(3);
		wcWidth = building.getWindowWidth() - 2*(scups.getArmsLength() + scups.getSCupsDiameter());
		wcHeight = 62;
		displacement = 1;
		
		moveDown = true;
		moveUp = false;
		moveRight = false;
		moveLeft = true;
		firstMoveRight = false;
		currentWinX = 1;
	}
	
	private void drawWinFrame(Graphics2D g2) {
		g2.setBackground(new Color(255, 255, 255, 0));
		g2.clearRect(0, 0, getAppWidth(), getAppHeight());
		
		building.createBuilding(g2);	// If you delete this line, the window cleaner remains stationary
			// However, the building is not drawn in the background
			// Something about BufferStrategy is not used for transparency
		g2.setColor(wcColor);
		g2.fillRect(getWCstartX(), getWCstartY(), wcWidth, wcHeight);
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
	
	public int getCurrentWinX() {
		return currentWinX;
	}
}