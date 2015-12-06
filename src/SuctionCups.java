import java.awt.Graphics2D;
import java.awt.Color;

public class SuctionCups {
	private int armsLength, scupsDiameter, scupsDisplacement, scupsTopPos, lrSCupsX, lrSCupsY, lrSCupsPauseX, lrSCupsPauseY;
	private Color scupsOnColor, scupsReleasedColor;
	private boolean lrSCupsOn, middleSCupOn;
	
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
			lrSCupsX = winCleaner.getWCstartX() - getArmsLength() - scupsDiameter;
		}
	}
	
	private void setSCdimensions(WindowCleaner winCleaner, Building building) {
		armsLength = 10;
		scupsDiameter = 30;
		scupsDisplacement = 3;
		scupsOnColor = new Color(255,0,0);
		scupsReleasedColor = new Color(0,255,0);
		
		lrSCupsX = building.getBuildingStartX();
		lrSCupsY = building.getBuildingStartY() + 31 + scupsDiameter/2;
		lrSCupsPauseX = building.getBuildingStartX() + winCleaner.getWCwidth();
		lrSCupsPauseY = winCleaner.getWCstartY() + 2*winCleaner.getWCheight() - scupsDiameter/2;
	}

	public void drawSuctionCups(WindowCleaner winCleaner, Graphics2D g2) {
		g2.setColor(scupsOnColor);
		g2.fillOval(getLRscupsX(), getLRscupsY(), scupsDiameter, scupsDiameter);
		System.out.print("lrSCupsX: " + getLRscupsX());
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
	
	private void lrSCupsMoveRight(WindowCleaner winCleaner) {
		lrSCupsX = getLRscupsX() + scupsDisplacement;
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
	
	private void middleSCupsMove() {
		
	}
	
	public int getArmsLength() {
		return armsLength;
	}
	
	public int getSCupsDiameter() {
		return scupsDiameter;
	}
	
	public int getLRscupsX() {
		return lrSCupsX;
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
