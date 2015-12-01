import java.awt.Graphics2D;
import java.awt.Color;

public class SuctionCups {
	private int armsLength, scupsDiameter, scDisplacement, scupsTopPos, lrSCupsX, lrSCupsY;
	private Color scupsOnColor, scupsReleasedColor;
	private boolean lrSCupsOn, middleSCupOn;
	
	public SuctionCups(WindowCleaner winCleaner, Building building) {
		setSCdimensions(winCleaner, building);
	}
	
	public void scupsMove(WindowCleaner winCleaner) {
		if (winCleaner.getWCmoveUpIndex() == winCleaner.getWCheight()) {
			lrSCupsMoveUp(winCleaner);
		}
	}
	
	private void setSCdimensions(WindowCleaner winCleaner, Building building) {
		armsLength = 10;
		scupsDiameter = 30;
		scupsOnColor = new Color(255,0,0);
		scupsReleasedColor = new Color(0,255,0);
		
		lrSCupsX = building.getBuildingStartX() + scupsDiameter;
		lrSCupsY = building.getBuildingStartY() - scupsDiameter/2;
	}

	public void drawSuctionCups(WindowCleaner winCleaner, Graphics2D g2) {
		g2.setColor(scupsOnColor);
		g2.fillOval(lrSCupsX, lrSCupsY, scupsDiameter, scupsDiameter);
		System.out.print("lrSCupsX: " + lrSCupsX);
		System.out.println(" lrSCupsY: " + lrSCupsY);
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
	
	private void lrSCupsMoveUp(WindowCleaner winCleaner) {
		lrSCupsX = winCleaner.getWCstartX() - getArmsLength() - scupsDiameter;
		lrSCupsY = winCleaner.getWCstartY() + winCleaner.getWCheight()/2 - scupsDiameter/2;
	}
	
	private void middleSCupsMove() {
		
	}
	
	public int getArmsLength() {
		return armsLength;
	}
	
	public int getSCupsDiameter() {
		return scupsDiameter;
	}
}
