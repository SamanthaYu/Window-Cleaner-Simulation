import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class SuctionCups {
	private int armsLength, scupsDiameter, scupsDisplacement, scupsTopPos, leftSCupsX, rightSCupsX, lrSCupsY, lrSCupsPauseX, lrSCupsPauseY, armStrokeSize;
	private Color scupsOnColor, scupsReleasedColor, armsColor;
	private boolean lrSCupsOn, middleSCupOn;
	private BasicStroke armStroke;
	
	public SuctionCups(WindowCleaner winCleaner, Building building) {
		setSCdimensions(winCleaner, building);
	}
	
	public void scupsMove(WindowCleaner winCleaner, Building building) {
		if (winCleaner.getMiniMoveDown()) {
			scupsMiniMoveDown(winCleaner, building);
		}
		else if (winCleaner.getMoveUp()) {
			scupsMoveUp(winCleaner, building);
		}
		else if (winCleaner.getMoveDown()) {
			scupsMoveDown(winCleaner, building);
		}
		else if (winCleaner.getMiniMoveUp()) {
			scupsMiniMoveUp(winCleaner, building);
		}
		else if (winCleaner.getMoveRight() || winCleaner.getMoveLeft()) {
			leftSCupsX = winCleaner.getWCstartX() - getArmsLength() - scupsDiameter;
			rightSCupsX = winCleaner.getWCstartX() + winCleaner.getWCwidth() + getArmsLength();
		}
	}
	
	public void setInitialSCups(WindowCleaner winCleaner, Building building) {
		leftSCupsX = building.getBuildingStartX();
		rightSCupsX =  winCleaner.getWCstartX() + winCleaner.getWCwidth() + getArmsLength();
		lrSCupsY = building.getBuildingStartY() + 31 + scupsDiameter/2;
		
		lrSCupsPauseX = building.getBuildingStartX() + winCleaner.getWCwidth();
		lrSCupsPauseY = winCleaner.getWCstartY() + 2*winCleaner.getWCheight() - scupsDiameter/2;
	}
	
	private void setSCdimensions(WindowCleaner winCleaner, Building building) {
		armsLength = 10;
		scupsDiameter = 30;
		scupsDisplacement = 3;
		
		scupsOnColor = new Color(255,0,0);
		scupsReleasedColor = new Color(0,255,0);
		armsColor = new Color(0,0,0);
		
		armStrokeSize = 2;
		armStroke = new BasicStroke(armStrokeSize);
	}

	public void drawSuctionCups(WindowCleaner winCleaner, Graphics2D g2) {
		g2.setColor(armsColor);
		g2.setStroke(new BasicStroke(armStrokeSize));
		g2.drawLine(getLeftSCupX() + scupsDiameter, getLRscupsY() + scupsDiameter/2, winCleaner.getWCstartX(), winCleaner.getMiddleY());	// Draws left arm
		g2.drawLine(getRightSCupX(), getLRscupsY() + scupsDiameter/2, winCleaner.getWCstartX() + winCleaner.getWCwidth(), winCleaner.getMiddleY());	// Draws right arm
		
		g2.setStroke(armStroke);	// Return the stroke back to normal
		
		g2.setColor(scupsOnColor);
		g2.fillOval(getLeftSCupX(), getLRscupsY(), scupsDiameter, scupsDiameter);	// Draws left suction cup
		g2.fillOval(getRightSCupX(), getLRscupsY(), scupsDiameter, scupsDiameter);	// Draws right suction cup
		
		System.out.print("lrSCupsX: " + getLeftSCupX());
		System.out.println(" lrSCupsY: " + getLRscupsY());
	}
	
	private void lrSCupsMoveUp(WindowCleaner winCleaner) {
		lrSCupsY = getLRscupsY() - scupsDisplacement - winCleaner.getWCdisplacement();
	}
	
	private void lrSCupsMoveDown(WindowCleaner winCleaner) {
		lrSCupsY = getLRscupsY() + scupsDisplacement + winCleaner.getWCdisplacement();
	}
	
	private void scupsMiniMoveDown(WindowCleaner winCleaner, Building building) {
		// If window cleaner has reached the bottom of its mini downward movement
		if (winCleaner.getWCstartY() + winCleaner.getWCdisplacement() >= building.getBuildingStartY() + winCleaner.getWCheight()) {
			lrSCupsPauseY = building.getBuildingStartY() + 3*winCleaner.getWCheight()/2 - scupsDiameter/2;
			
			if (lrSCupsY < lrSCupsPauseY) {
				lrSCupsMoveDown(winCleaner);
			}
		}
	}
	
	private void scupsMiniMoveUp(WindowCleaner winCleaner, Building building) {
		// If the window cleaner has reached the bottom of its mini upward movement
		if (winCleaner.getWCstartY() - winCleaner.getWCdisplacement() <= building.getBuildingStartY()) {
			lrSCupsPauseY = building.getBuildingStartY() + winCleaner.getWCheight()/2 - scupsDiameter/2;
			
			if (lrSCupsY > lrSCupsPauseY) {
				lrSCupsMoveUp(winCleaner);
			}
		}
	}
	
	private void scupsMoveUp(WindowCleaner winCleaner, Building building) {
		// If the suction cups moves past where the suction cups are supposed to stop
		if (lrSCupsY <= lrSCupsPauseY) {
			// If the window cleaner catches up to the suction cups, then update the suction cup stopping position
			if (winCleaner.getMiddleY() <= lrSCupsY) {
				// If the suction cup's next stopping position would still be on the building
				if (winCleaner.getMiddleY() - 5*winCleaner.getWCheight()/2 + scupsDiameter/2 > building.getBuildingStartY() + winCleaner.getWCheight()/2 - scupsDiameter/2)
					lrSCupsPauseY = winCleaner.getMiddleY() - 5*winCleaner.getWCheight()/2 + scupsDiameter/2;
				// Otherwise, move the suction cup to its final position at the top of the building
				else {
					System.out.println("hey up");
					lrSCupsPauseY = building.getBuildingStartY() + winCleaner.getWCheight()/2 - scupsDiameter/2;
				}
			}
		}
		if (lrSCupsY > lrSCupsPauseY) {
			lrSCupsMoveUp(winCleaner);
		}
	}
	
	private void scupsMoveDown(WindowCleaner winCleaner, Building buildng) {
		// If the suction cup moves past where the suction cups are supposed to stop
		if (lrSCupsY >= lrSCupsPauseY) {
			// If the window cleaner catches up to the suction cups, then update the suction cup stopping position
			if (winCleaner.getMiddleY() >= lrSCupsY) {
				// If the suction cup's next stopping position would still be on the building
				if (winCleaner.getMiddleY() + 5*winCleaner.getWCheight()/2 - scupsDiameter/2 < winCleaner.getAppHeight() - winCleaner.getWCheight()/2 - scupsDiameter)
					lrSCupsPauseY = winCleaner.getMiddleY() + 5*winCleaner.getWCheight()/2 - scupsDiameter/2;
				// Otherwise, move the suction cup to its final position at the bottom of the building
				else {
					System.out.println("hey down");
					lrSCupsPauseY = winCleaner.getAppHeight() - winCleaner.getWCheight()/2 - scupsDiameter;
				}
			}
		}
		if (lrSCupsY < lrSCupsPauseY) {
			lrSCupsMoveDown(winCleaner);
		}
	}
	
	private int getSCupsTopPos() {
		return scupsTopPos;
	}
	
	private void lrSCupsOn() {
		lrSCupsOn = true;
	}
	
	private void lrSCupsOff() {
		lrSCupsOn = false;
	}
	
	private void middleSCupsOn() {
		middleSCupOn = true;
	}
	
	private void middleSCupsOff() {
		middleSCupOn = false;
	}
	
	public int getArmsLength() {
		return armsLength;
	}
	
	public int getSCupsDiameter() {
		return scupsDiameter;
	}
	
	public int getLeftSCupX() {
		return leftSCupsX;
	}
	
	public int getRightSCupX() {
		return rightSCupsX;
	}
	
	public int getLRscupsY() {
		return lrSCupsY;
	}
	
	public int getLRscupsPauseY() {
		return lrSCupsPauseY;
	}
	
	public int getSCupsDisplacement() {
		return scupsDisplacement;
	}
}
