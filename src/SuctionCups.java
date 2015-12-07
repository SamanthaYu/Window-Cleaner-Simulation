import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class SuctionCups {
	private int armsNormLength, scupsDiameter, scupsDisplacement, scupsTopPos;
	private int leftSCupsX, rightSCupsX, middleSCupX, lrSCupsY, middleSCupY;
	private int lrSCupsPauseY, middleSCupPauseX, middleSCupPauseY, armStrokeSize;
	
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
		else if (winCleaner.getMoveRight()) {
			scupsMoveRight(winCleaner, building);
		}
		else if (winCleaner.getMoveLeft()) {
			scupsMoveLeft(winCleaner, building);
		}
	}
	
	public void setInitialSCups(WindowCleaner winCleaner, Building building) {
		leftSCupsX = building.getBuildingStartX();
		rightSCupsX =  winCleaner.getWCstartX() + winCleaner.getWCwidth() + getArmsNormLength();
		middleSCupX = winCleaner.getWCstartX() + winCleaner.getWCwidth()/2 - scupsDiameter/2;
		
		lrSCupsY = winCleaner.getWCstartY() + winCleaner.getWCheight()/2 + scupsDiameter/2;
		middleSCupY = winCleaner.getWCstartY() - getArmsNormLength() - scupsDiameter;
		
		lrSCupsPauseY = winCleaner.getWCstartY() + 2*winCleaner.getWCheight() - scupsDiameter/2;
		
		switchLRscupsOn();
		switchMiddleSCupOff();
	}
	
	private void setSCdimensions(WindowCleaner winCleaner, Building building) {
		armsNormLength = 10;
		scupsDiameter = 30;
		scupsDisplacement = 2;
		
		scupsOnColor = new Color(108,154,51);	// Green
		scupsReleasedColor = new Color(170, 88, 57);	// Orange
		armsColor = new Color(0,0,0);
		
		armStrokeSize = 2;
		armStroke = new BasicStroke(armStrokeSize);
	}

	public void drawSuctionCups(WindowCleaner winCleaner, Graphics2D g2) {
		g2.setColor(armsColor);
		g2.setStroke(new BasicStroke(armStrokeSize));
		g2.drawLine(getLeftSCupMiddleX(), getLRscupMiddleY(), winCleaner.getWCstartX(), winCleaner.getMiddleY());	// Draws left arm
		g2.drawLine(getRightSCupMiddleX(), getLRscupMiddleY(), winCleaner.getWCstartX() + winCleaner.getWCwidth(), winCleaner.getMiddleY());	// Draws right arm
		
		g2.setStroke(armStroke);	// Return the stroke back to normal
		
		if (lrSCupsOn) {
			g2.setColor(scupsOnColor);
		}
		else {
			g2.setColor(scupsReleasedColor);
		}
		g2.fillOval(getLeftSCupX(), getLRscupsY(), scupsDiameter, scupsDiameter);	// Draws left suction cup
		g2.fillOval(getRightSCupX(), getLRscupsY(), scupsDiameter, scupsDiameter);	// Draws right suction cup
		
		if (middleSCupOn) {
			g2.setColor(scupsOnColor);
		}
		else {
			g2.setColor(scupsReleasedColor);
		}
		g2.fillOval(getMiddleSCupX(), getMiddleSCupY(), scupsDiameter, scupsDiameter);	// Draws middle suction cup
		
		System.out.print("lrSCupsX: " + getLeftSCupX());
		System.out.println(" lrSCupsY: " + getLRscupsY());
	}
	
	private void scupsMiniMoveDown(WindowCleaner winCleaner, Building building) {
		// If window cleaner has reached the bottom of its mini downward movement
		if (winCleaner.getWCstartY() + winCleaner.getWCdisplacement() >= building.getBuildingStartY() + winCleaner.getWCheight()) {
			lrSCupsPauseY = building.getBuildingStartY() + 3*winCleaner.getWCheight()/2 - scupsDiameter/2;
			
			if (lrSCupsY < lrSCupsPauseY) {
				lrSCupsMoveDown(winCleaner);
				switchLRscupsOff();
			}
			else {
				switchLRscupsOn();
			}
		}
	}
	
	private void scupsMiniMoveUp(WindowCleaner winCleaner, Building building) {
		// If the window cleaner has reached the bottom of its mini upward movement
		if (winCleaner.getWCstartY() - winCleaner.getWCdisplacement() <= building.getBuildingStartY()) {
			lrSCupsPauseY = building.getBuildingStartY() + winCleaner.getWCheight()/2 - scupsDiameter/2;
			
			if (lrSCupsY > lrSCupsPauseY) {
				lrSCupsMoveUp(winCleaner);
				switchLRscupsOff();
			}
			else {
				switchLRscupsOn();
			}
		}
	}
	
	private void scupsMoveUp(WindowCleaner winCleaner, Building building) {
		// If the left/right suction cups moves past where the suction cups are supposed to stop
		if (lrSCupsY <= lrSCupsPauseY) {
			// If the window cleaner catches up to the left/right suction cups, then update the suction cup stopping position
			if (winCleaner.getMiddleY() <= lrSCupsY) {
				// If the left/right suction cup's next stopping position would still be on the building
				if (winCleaner.getMiddleY() - 5*winCleaner.getWCheight()/2 + scupsDiameter/2 > building.getBuildingStartY() + winCleaner.getWCheight()/2 - scupsDiameter/2)
					lrSCupsPauseY = winCleaner.getMiddleY() - 5*winCleaner.getWCheight()/2 + scupsDiameter/2;
				// Otherwise, move the left/right suction cups to its final position at the top of the building
				else {
					System.out.println("hey up");
					lrSCupsPauseY = building.getBuildingStartY() + winCleaner.getWCheight()/2 - scupsDiameter/2;
				}
			}
		}
		
		if (lrSCupsY > lrSCupsPauseY) {
			lrSCupsMoveUp(winCleaner);
			switchLRscupsOff();
			switchMiddleSCupOn();
		}
		else {	// Move the middle suction cup whenever the left and right suction cup clinging to the building
			middleSCupMoveUp(winCleaner);
			switchMiddleSCupOff();
			switchLRscupsOn();
		}
	}
	
	private void scupsMoveDown(WindowCleaner winCleaner, Building buildng) {
		// If the left/right suction cup moves past where the suction cups are supposed to stop
		if (lrSCupsY >= lrSCupsPauseY) {
			// If the window cleaner catches up to the left/right suction cups, then update the suction cup stopping position
			if (winCleaner.getMiddleY() >= lrSCupsY) {
				// If the left/right suction cup's next stopping position would still be on the building
				if (winCleaner.getMiddleY() + 5*winCleaner.getWCheight()/2 - scupsDiameter/2 < winCleaner.getAppHeight() - winCleaner.getWCheight()/2 - scupsDiameter)
					lrSCupsPauseY = winCleaner.getMiddleY() + 5*winCleaner.getWCheight()/2 - scupsDiameter/2;
				// Otherwise, move the left/right suction cups to its final position at the bottom of the building
				else {
					lrSCupsPauseY = winCleaner.getAppHeight() - winCleaner.getWCheight()/2 - scupsDiameter;
				}
			}
		}
		
		if (lrSCupsY < lrSCupsPauseY) {
			lrSCupsMoveDown(winCleaner);
			switchLRscupsOff();
			switchMiddleSCupOn();
		}
		else {	// Move the middle suction cup whenever the left and right suction cup clinging to the building
			middleSCupMoveDown(winCleaner);
			switchMiddleSCupOff();
			switchLRscupsOn();
		}
	}
	
	private void scupsMoveLeft(WindowCleaner winCleaner, Building building) {
		leftSCupsX = winCleaner.getWCstartX() - getArmsNormLength() - scupsDiameter;
		rightSCupsX = winCleaner.getWCstartX() + winCleaner.getWCwidth() + getArmsNormLength();
		switchLRscupsOff();
	}
	
	private void scupsMoveRight(WindowCleaner winCleaner, Building building) {
		leftSCupsX = winCleaner.getWCstartX() - getArmsNormLength() - scupsDiameter;
		rightSCupsX = winCleaner.getWCstartX() + winCleaner.getWCwidth() + getArmsNormLength();
		switchLRscupsOff();
	}
	
	private void lrSCupsMoveUp(WindowCleaner winCleaner) {
		lrSCupsY = getLRscupsY() - scupsDisplacement - winCleaner.getWCdisplacement();
	}
	
	private void lrSCupsMoveDown(WindowCleaner winCleaner) {
		lrSCupsY = getLRscupsY() + scupsDisplacement + winCleaner.getWCdisplacement();
	}
	
	private void middleSCupMoveUp(WindowCleaner winCleaner) {
		middleSCupY = getMiddleSCupY() - scupsDisplacement - winCleaner.getWCdisplacement();
	}
	
	private void middleSCupMoveDown(WindowCleaner winCleaner) {
		middleSCupY = getMiddleSCupY() + scupsDisplacement + winCleaner.getWCdisplacement();
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
	
	public void switchLRscupsOn() {
		lrSCupsOn = true;
	}
	
	public void switchLRscupsOff() {
		lrSCupsOn = false;
	}
	
	public void switchMiddleSCupOn() {
		middleSCupOn = true;
	}
	
	public void switchMiddleSCupOff() {
		middleSCupOn = false;
	}
	
	public int getArmsNormLength() {
		return armsNormLength;
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
	
	public int getMiddleSCupX() {
		return middleSCupX;
	}
	
	public int getLRscupsY() {
		return lrSCupsY;
	}
	
	public int getMiddleSCupY() {
		return middleSCupY;
	}
	
	public int getLeftSCupMiddleX() {
		return getLeftSCupX() + scupsDiameter/2;
	}
	
	public int getRightSCupMiddleX() {
		return getRightSCupX() + scupsDiameter/2;
	}
	
	public int getLRscupMiddleY() {
		return getLRscupsY() + scupsDiameter/2;
	}
	
	public int getLRscupsPauseY() {
		return lrSCupsPauseY;
	}
	
	public int getSCupsDisplacement() {
		return scupsDisplacement;
	}
	
	public boolean getLRscupsOn() {
		return lrSCupsOn;
	}
	
	public boolean getMiddleSCupOn() {
		return middleSCupOn;
	}
}
