import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class Building {
	private Color dirtyWinColor, cleanWinColor, paneColor;
	
	private int buildingStartX, buildingStartY, buildingWidth, buildingHeight;
	private int winStartX, winStartY, winWidth, winHeight, paneWidth, numWinX;
	
	public Building(WindowCleaner winCleaner, StatusBar sbar) {
		setBdimensions(winCleaner, sbar);
	}
	
	private void setBdimensions(WindowCleaner winCleaner, StatusBar sbar) {
		buildingStartX = 50;
		buildingStartY = 100;
		
		buildingWidth = winCleaner.getAppWidth() - 2*buildingStartX - sbar.getSBarWidth();
		buildingHeight = winCleaner.getAppHeight() - buildingStartY;
		
		paneWidth = 1;
		numWinX = 0;
		
		winStartX = buildingStartX;
		winStartY = buildingStartY;
		
		winWidth = 253;
		winHeight = 311;
		
		dirtyWinColor = new Color(127,171,185);
		cleanWinColor = new Color(178,208,218);
		paneColor = new Color(179,176,174);
	}
	
	public void createBuilding(Graphics2D g2) {
		winStartX = getBuildingStartX();
		numWinX = 0;
		
		do {
			numWinX += 1;
			
			do {
				createWindow(g2, false, winStartX, winStartY);
				winStartY += winHeight + paneWidth;
			} while (winStartY <= buildingHeight);
			
			winStartX += winWidth + paneWidth;
			winStartY = buildingStartY;
		} while (getWinStartX() < getBuildingWidth());
	}
	
	private void createWindow(Graphics2D g2, Boolean clean, int winStartX, int winStartY) {
		if (clean) {
			g2.setColor(cleanWinColor);
		} else {
			g2.setColor(dirtyWinColor);
		}
		
		g2.fillRect(winStartX, winStartY, winWidth, winHeight);
		
		g2.setColor(paneColor);
		g2.setStroke(new BasicStroke(paneWidth));
		g2.drawRect(winStartX, winStartY, winWidth, winHeight);
	}
	
	public int getBuildingStartX() {
		return buildingStartX;
	}
	
	public int getBuildingStartY() {
		return buildingStartY;
	}
	
	public int getBuildingEndX() {
		return buildingStartX + buildingWidth;
	}

	public int getBuildingWidth() {
		return buildingWidth;
	}
	
	public int getBuildingHeight() {
		return buildingHeight;
	}
	
	public int getWindowWidth() {
		return winWidth + paneWidth;
	}
	
	public int getWindowHeight() {
		return winHeight;
	}
	
	public int getPaneWidth() {
		return paneWidth;
	}
	
	public int getNumWinX() {
		return numWinX;
	}
	
	public int getWinStartX() {
		return winStartX;
	}
}