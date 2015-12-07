/****************************************************************
 * ENSC 100: Window Cleaner Simulation
 * Group: Zeta
 * @author Samantha Yu
 ****************************************************************/

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import java.awt.Toolkit;

public class WindowCleaner extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int appWidth, appHeight;
	
	private Building building;
	private ChemicalSpray cspray;
	private Dolly dolly;
	private SuctionCups scups;
	private WaterPump wpump;
	private WaterTank wtank;
	private StatusBar sbar;
	
	private int wcWidth, wcHeight, wcStartX, wcStartY, nextWCstartX, currentWinXNum, wcDisplacement, wcMiddleY;
	private Color wcColor;
	private boolean moveUp, moveDown, moveLeft, moveRight, miniMoveUp, miniMoveDown, firstMoveRight, lastMiniMoveUp, cleanWindow, currentCleaning;
	
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
		scups = new SuctionCups(winCleaner, building);
		wpump = new WaterPump(winCleaner);
		wtank = new WaterTank(winCleaner);
		
		wcStartX = building.getBuildingStartX() + building.getPaneWidth() + scups.getArmsNormLength() + scups.getSCupsDiameter();
		wcStartY = building.getBuildingStartY();
	}
	
	private void wcMove() {
		if (miniMoveDown) {
			miniMoveDown();
		}
		else if (moveUp) {
			moveUp();
		}
		else if (moveDown) {
			moveDown();
		}
		else if (moveRight) {
			moveRight();
		}
		else if (miniMoveUp) {
			miniMoveUp();
		}
		else if (moveLeft) {
			moveLeft();
		}

		scups.scupsMove(this, building);
	}
	
	private void moveUp() {
		wcMoveUp();
		currentCleaning = true;
		
		// Window cleaner reaches almost the top of building
		if (getWCstartY() - wcDisplacement < building.getBuildingStartY() + getWCheight()) {
			cleanWindow = true;
			
			miniMoveUp = true;
			moveUp = false;
		}
	}
	
	private void moveDown() {
		wcMoveDown();
		
		// Window cleaner reaches bottom of building
		if (getWCstartY() + wcDisplacement >= getAppHeight() - getWCheight()) {
			moveDown = false;
			moveUp = true;
		}
	}
	
	private void moveRight() {
		wcMoveRight();
		
		// Window cleaner moves right for the next window's first time
		if (firstMoveRight) {
			nextWCstartX = getWCstartX() + building.getWindowWidth();
			firstMoveRight = false;
		}
		
		// Window cleaner has reached next window
		if (getWCstartX() + wcDisplacement >= nextWCstartX) {
			moveRight = false;
			miniMoveUp = true;
			cleanWindow = false;
		}
	}
	
	private void moveLeft() {
		wcMoveLeft();
		
		// Window cleaner has returned to its final position
		if (getWCstartX() - wcDisplacement <= building.getBuildingStartX() + scups.getArmsNormLength() + scups.getSCupsDiameter()) {
			moveLeft = false;
			lastMiniMoveUp = true;
			miniMoveUp = true;
		}
	}
	
	private void miniMoveDown() {
		// Window cleaner reaches the end of its small downward movement
		if (getWCstartY() + wcDisplacement >= building.getBuildingStartY() + getWCheight()) {
			// Suction cups have finished moving downwards
			miniMoveDown = false;
			
			if (!cleanWindow) {
				moveDown = true;
			}
			else {
				moveRight = true;
				firstMoveRight = true;
			}
		}
		else {
			// Window cleaner moves down a bit so that middle suction cup can stay on building
			wcMoveDown();
		}
	}
	
	private void miniMoveUp() {
		// Window cleaner reaches the end of its small downward movement
		if (getWCstartY() - wcDisplacement <= building.getBuildingStartY()) {
			miniMoveUp = false;
			currentCleaning = false;
			
			if (cleanWindow)
				currentWinXNum += 1;
			
			if (!lastMiniMoveUp)
				miniMoveDown = true;
		}
		else {
			// Window cleaner moves up a bit so that middle suction cup is not on the building at the top
			wcMoveUp();
		}
	}
	
	private void wcMoveDown() {
		wcStartY = getWCstartY() + wcDisplacement;
	}
	
	private void wcMoveUp() {
		wcStartY = getWCstartY() - wcDisplacement;
	}
	
	private void wcMoveLeft() {
		wcStartX = getWCstartX() - wcDisplacement;
	}

	private void wcMoveRight() {
		wcStartX = getWCstartX() + wcDisplacement;
	}
	
	private void drawWinCleaner() {
		do {
			do {
				BufferStrategy bf = this.getBufferStrategy();
				Graphics2D g2 = null;
				
				try {
					g2 = (Graphics2D) bf.getDrawGraphics();
					
					g2.setBackground(new Color(255, 255, 255, 0));
					g2.clearRect(0, 0, getAppWidth(), getAppHeight());
					
					building.createBuilding(this, g2);
					dolly.drawDolly(this, building, g2);
					scups.drawSuctionCups(this, g2);
					sbar.drawStatusBar(this, g2);
					
					this.wcMove();
					this.drawWinFrame(g2);
				}
				finally {
					g2.dispose();	// It is best to dispose() a Graphics object when done with it.
				}
			 
				bf.show();	// Shows the contents of the backbuffer on the screen.
		 
		        Toolkit.getDefaultToolkit().sync();	//Tell the System to do the Drawing now, otherwise it can take a few extra ms until drawing is done which looks very jerky
			} while (currentWinXNum <= building.getNumWinX() || lastMiniMoveUp);
			
			moveDown = false;
			moveRight = false;
			moveUp = false;
		} while (moveLeft);
	}
	
	private void setWCdimensions() {
		wcColor = new Color(3);
		wcWidth = building.getWindowWidth() - 2*(scups.getArmsNormLength() + scups.getSCupsDiameter());
		wcHeight = 62;
		wcDisplacement = 1;
		
		moveDown = false;
		moveUp = false;
		moveRight = false;
		moveLeft = true;
		miniMoveDown = true;
		miniMoveUp = false;
		
		firstMoveRight = false;
		lastMiniMoveUp = false;
		cleanWindow = false;
		currentWinXNum = 1;
		
		scups.setInitialSCups(this, building);
	}
	
	private void drawWinFrame(Graphics2D g2) {
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
	
	public int getWCmiddleX() {
		return getWCstartX() + getWCwidth()/2;
	}
	
	public int getWCmiddleY() {
		return getWCstartY() + getWCheight()/2;
	}
	
	public int getWCendX() {
		return getWCstartX() + getWCwidth();
	}
	
	public int getWCendY() {
		return getWCstartY() + getWCheight();
	}
	
	public int getCurrentWinXNum() {
		return currentWinXNum;
	}
	
	public boolean getMoveUp() {
		return moveUp;
	}
	
	public boolean getMoveDown() {
		return moveDown;
	}
	
	public boolean getMoveRight() {
		return moveRight;
	}
	
	public boolean getMoveLeft() {
		return moveLeft;
	}
	
	public boolean getMiniMoveUp() {
		return miniMoveUp;
	}
	
	public boolean getMiniMoveDown() {
		return miniMoveDown;
	}
	
	public boolean getCleanWin() {
		return cleanWindow;
	}
	
	public boolean getCurrentCleaning() {
		return currentCleaning;
	}
	
	public int getWCdisplacement() {
		return wcDisplacement;
	}
}