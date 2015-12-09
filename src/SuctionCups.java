import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class SuctionCups {
	private int armsNormLength, scupsDiameter, scupsDisplacement, scupsTopPos;
	private int leftSCupsX, rightSCupsX, middleSCupX, lrSCupsY, middleSCupY;
	private int lrSCupsPauseY, middleSCupPauseX, armStrokeSize;
	
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
		else if (winCleaner.getMiniMoveUp()) {
			scupsMiniMoveUp(winCleaner, building);
		}
		else if (winCleaner.getMoveUp()) {
			scupsMoveUp(winCleaner, building);
		}
		else if (winCleaner.getMoveDown()) {
			scupsMoveDown(winCleaner, building);
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
		rightSCupsX =  winCleaner.getWCendX() + getArmsNormLength();
		middleSCupX = winCleaner.getWCmiddleX() - scupsDiameter/2;
		
		lrSCupsY = winCleaner.getWCmiddleY() + scupsDiameter/2;
		middleSCupY = winCleaner.getWCstartY() - getArmsNormLength() - scupsDiameter;
		
		lrSCupsPauseY = winCleaner.getWCstartY() + 2*winCleaner.getWCheight() - scupsDiameter/2;
		middleSCupPauseX = winCleaner.getWCmiddleX() + winCleaner.getWCwidth()/2 - scupsDiameter/2;
		
		switchLRscupsOn();
		switchMiddleSCupOff();
	}
	
	private void setSCdimensions(WindowCleaner winCleaner, Building building) {
		armsNormLength = 10;
		scupsDiameter = 30;
		scupsDisplacement = 2;
		
		scupsOnColor = new Color(170,57,57);		// Red
		scupsReleasedColor = new Color(17,102,17);	// Green
		armsColor = new Color(0,0,0);
		
		armStrokeSize = 2;
		armStroke = new BasicStroke(armStrokeSize);
	}

	public void drawSuctionCups(WindowCleaner winCleaner, Graphics2D g2) {
		g2.setColor(armsColor);
		g2.setStroke(armStroke);
		g2.drawLine(getLeftSCupMiddleX(), getLRscupMiddleY(), winCleaner.getWCstartX(), winCleaner.getWCmiddleY());	// Draws arm to left suction cup
		g2.drawLine(getRightSCupMiddleX(), getLRscupMiddleY(), winCleaner.getWCendX(), winCleaner.getWCmiddleY());	// Draws arm to right suction cup
		
		g2.drawLine(winCleaner.getWCstartX(), winCleaner.getWCstartY(), getMiddleSCupMiddleX(), getMiddleSCupMiddleY());	// Draws left arm to middle suction cup
		g2.drawLine(winCleaner.getWCendX(), winCleaner.getWCstartY(), getMiddleSCupMiddleX(), getMiddleSCupMiddleY());		// Draws right arm to middle suction cup

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
	}
	
	private void scupsMiniMoveDown(WindowCleaner winCleaner, Building building) {
		if (middleSCupY < building.getBuildingStartY()) {	// If the middle suction cup is not on the building
			middleSCupMoveDown(winCleaner);
			switchMiddleSCupOff();
			switchLRscupsOn();
		}
		
		lrSCupsPauseY = building.getBuildingStartY() + 3*winCleaner.getWCheight()/2 - scupsDiameter/2;
		
		if (lrSCupsY < lrSCupsPauseY) {	// If the left/right suction cups have not passed its stopping position
			lrSCupsMoveDown(winCleaner);
			switchLRscupsOff();
			switchMiddleSCupOn();
		}
	}
	
	private void scupsMiniMoveUp(WindowCleaner winCleaner, Building building) {
		if (middleSCupY > building.getBuildingStartY() - scupsDiameter - armsNormLength) {	// If the suction cup is still on the building
			middleSCupMoveUp(winCleaner);
			switchMiddleSCupOff();
			switchLRscupsOn();
		}
		
		lrSCupsPauseY = building.getBuildingStartY() + winCleaner.getWCheight()/2 - scupsDiameter/2;
		
		if (lrSCupsY > lrSCupsPauseY) {	// If the left/right suction cups pass its stopping position
			lrSCupsMoveUp(winCleaner);
			switchLRscupsOff();
			switchMiddleSCupOn();
		}
	}
	
	private void scupsMoveUp(WindowCleaner winCleaner, Building building) {
		if (lrSCupsY > lrSCupsPauseY) {
			lrSCupsMoveUp(winCleaner);
			switchLRscupsOff();
			switchMiddleSCupOn();
		}
		else {	// Move the middle suction cup whenever the left and right suction cup cling to the building
			middleSCupMoveUp(winCleaner);
			switchMiddleSCupOff();
			switchLRscupsOn();
		}
		
		// If the left/right suction cups moves past where the suction cups are supposed to stop
		if (lrSCupsY <= lrSCupsPauseY) {
			// If the window cleaner catches up to the left/right suction cups, then update the suction cup stopping position
			if (winCleaner.getWCmiddleY() <= lrSCupsY) {
				// If the left/right suction cup's next stopping position would still be on the building
				if (winCleaner.getWCmiddleY() - 5*winCleaner.getWCheight()/2 + scupsDiameter/2 > building.getBuildingStartY() + winCleaner.getWCheight()/2 - scupsDiameter/2)
					lrSCupsPauseY = winCleaner.getWCmiddleY() - 5*winCleaner.getWCheight()/2 + scupsDiameter/2;
				// Otherwise, move the left/right suction cups to its final position at the top of the building
				else {
					lrSCupsPauseY = building.getBuildingStartY() + winCleaner.getWCheight()/2 - scupsDiameter/2;
				}
			}
		}
	}
	
	private void scupsMoveDown(WindowCleaner winCleaner, Building buildng) {
		if (lrSCupsY < lrSCupsPauseY) {
			lrSCupsMoveDown(winCleaner);
			switchLRscupsOff();
			switchMiddleSCupOn();
		}
		else {	// Move the middle suction cup whenever the left and right suction cup cling to the building
			middleSCupMoveDown(winCleaner);
			switchMiddleSCupOff();
			switchLRscupsOn();
		}
		
		// If the left/right suction cup moves past where the suction cups are supposed to stop
		if (lrSCupsY >= lrSCupsPauseY) {
			// If the window cleaner catches up to the left/right suction cups, then update the suction cup stopping position
			if (winCleaner.getWCmiddleY() >= lrSCupsY) {
				// If the left/right suction cup's next stopping position would still be on the building
				if (winCleaner.getWCmiddleY() + 5*winCleaner.getWCheight()/2 - scupsDiameter/2 < winCleaner.getAppHeight() - winCleaner.getWCheight()/2 - scupsDiameter)
					lrSCupsPauseY = winCleaner.getWCmiddleY() + 5*winCleaner.getWCheight()/2 - scupsDiameter/2;
				// Otherwise, move the left/right suction cups to its final position at the bottom of the building
				else {
					lrSCupsPauseY = winCleaner.getAppHeight() - winCleaner.getWCheight()/2 - scupsDiameter;
				}
			}
		}
	}
	
	private void scupsMoveLeft(WindowCleaner winCleaner, Building building) {
		if (middleSCupX > middleSCupPauseX) {
			middleSCupMoveLeft(winCleaner);
			switchMiddleSCupOff();
			switchLRscupsOn();
		}
		else {	// Move the left and right suction cup when the middle suction cup is in place
			leftSCupsX = winCleaner.getWCstartX() - getArmsNormLength() - scupsDiameter;
			rightSCupsX = winCleaner.getWCendX() + getArmsNormLength();
			switchLRscupsOff();
			switchMiddleSCupOn();
		}
		
		// If the middle suction cup moves past where it is supposed to stop
		if (middleSCupX <= middleSCupPauseX) {
			// If the window cleaner catches up to the middle suction cup, then update the suction cup stopping position
			if (winCleaner.getWCmiddleX() <= middleSCupX) {
				// If the middle suction cup's next stopping position would still be on the building
				if (winCleaner.getWCmiddleX() - winCleaner.getWCwidth()/2 + scupsDiameter/2 > building.getBuildingStartX() + building.getWindowWidth()/2 - scupsDiameter/2)
					middleSCupPauseX = winCleaner.getWCmiddleX() - winCleaner.getWCwidth()/2 - scupsDiameter/2;
				// Otherwise, move the suction cup to the middle of the next window
				else {
					middleSCupPauseX = building.getBuildingStartX() + building.getWindowWidth()/2 - scupsDiameter/2;
				}
			}
		}
	}
	
	private void scupsMoveRight(WindowCleaner winCleaner, Building building) {
		if (middleSCupX < middleSCupPauseX) {
			middleSCupMoveRight(winCleaner);
			switchMiddleSCupOff();
			switchLRscupsOn();
		}
		else {	// Move the left and right suction cup when the middle suction cup is in place
			leftSCupsX = winCleaner.getWCstartX() - getArmsNormLength() - scupsDiameter;
			rightSCupsX = winCleaner.getWCendX() + getArmsNormLength();
			switchLRscupsOff();
			switchMiddleSCupOn();
		}
		
		// If the middle suction cup moves past where it is supposed to stop
		if (middleSCupX >= middleSCupPauseX) {
			// If the window cleaner catches up to the middle suction cup, then update the suction cup stopping position
			if (winCleaner.getWCmiddleX() >= middleSCupX) {
				// If the middle suction cup's next stopping position is before the middle of the next window
				if (winCleaner.getWCmiddleX() + winCleaner.getWCwidth()/2  - scupsDiameter/2 < building.getBuildingStartX() + winCleaner.getCurrentWinXNum()*building.getWindowWidth() - building.getWindowWidth()/2 - scupsDiameter/2)
					middleSCupPauseX = winCleaner.getWCmiddleX() + winCleaner.getWCwidth()/2  - scupsDiameter/2;
				// Otherwise, move the suction cup to the middle of the next window
				else {
					middleSCupPauseX = building.getBuildingStartX() + winCleaner.getCurrentWinXNum()*building.getWindowWidth() - building.getWindowWidth()/2 - scupsDiameter/2;
				}
			}
		}
	}
	
	private void lrSCupsMoveUp(WindowCleaner winCleaner) {
		lrSCupsY = getLRscupsY() - scupsDisplacement - winCleaner.getWCdisplacement();
	}
	
	private void lrSCupsMoveDown(WindowCleaner winCleaner) {
		lrSCupsY = getLRscupsY() + scupsDisplacement + winCleaner.getWCdisplacement();
	}
	
	private void middleSCupMoveUp(WindowCleaner winCleaner) {
		middleSCupY = winCleaner.getWCstartY() - winCleaner.getWCheight()/2 - getArmsNormLength() - scupsDiameter;
	}
	
	private void middleSCupMoveDown(WindowCleaner winCleaner) {
		middleSCupY = winCleaner.getWCstartY() - getArmsNormLength() - scupsDiameter;
	}
	
	private void middleSCupMoveRight(WindowCleaner winCleaner) {
		middleSCupX = getMiddleSCupX() + scupsDisplacement + winCleaner.getWCdisplacement();
	}
	
	private void middleSCupMoveLeft(WindowCleaner winCleaner) {
		middleSCupX = getMiddleSCupX() - scupsDisplacement - winCleaner.getWCdisplacement();
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
	
	public int getMiddleSCupMiddleX(){
		return getMiddleSCupX() + scupsDiameter/2;
	}
	
	public int getMiddleSCupMiddleY() {
		return getMiddleSCupY() + scupsDiameter/2;
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
