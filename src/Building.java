import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class Building {
	private Color dirtyWinColor, cleanWinColor, paneColor;
	private int buildingStartX, buildingStartY, buildingWidth, buildingHeight;
	private int winStartX, winStartY, winWidth, winHeight, paneWidth, numWinX;
	private boolean clean;
	
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
		
		dirtyWinColor = new Color(71,113,135);
		cleanWinColor = new Color(109,147,161);
		paneColor = new Color(174,174,174);
	}
	
	public void createBuilding(WindowCleaner winCleaner, Graphics2D g2) {
		winStartX = getBuildingStartX();
		numWinX = 0;
		
		do {
			numWinX += 1;
			
			do {
				if (winCleaner.getCurrentWinXNum() > numWinX)
					clean = true;
				else
					clean = false;
				
				createWindow(g2);
				winStartY += winHeight + paneWidth;
			} while (winStartY <= buildingHeight);
			
			winStartX += winWidth + paneWidth;
			winStartY = buildingStartY;
		} while (getWinStartX() < getBuildingWidth());
		
		if (winCleaner.getCurrentCleaning()) {
			cleanWindow(winCleaner, g2);
		}
	}
	
	private void createWindow(Graphics2D g2) {
		if (clean) {
			g2.setColor(cleanWinColor);
		}
		else {
			g2.setColor(dirtyWinColor);
		}
		
		g2.fillRect(winStartX, winStartY, winWidth, winHeight);
		
		g2.setColor(paneColor);
		g2.setStroke(new BasicStroke(paneWidth));
		g2.drawRect(winStartX, winStartY, winWidth, winHeight);
	}
	
	private void cleanWindow(WindowCleaner winCleaner, Graphics2D g2) {
		g2.setColor(cleanWinColor);
		g2.fillRect(getCurrentWinStartX(winCleaner), winCleaner.getWCstartY(), winWidth, winCleaner.getAppHeight() - winCleaner.getWCstartY());
		
		if (winCleaner.getWCstartY() < getWinPaneY()) {
			g2.setColor(paneColor);
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(getCurrentWinStartX(winCleaner), getWinPaneY(), getCurrentWinEndX(winCleaner), getWinPaneY());
		}
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
	
	public int getWinPaneY() {
		return winStartY + winHeight;
	}
	
	public int getCurrentWinStartX(WindowCleaner winCleaner) {
		return getBuildingStartX() + (winCleaner.getCurrentWinXNum() - 1)*getWindowWidth();
	}
	
	public int getCurrentWinEndX(WindowCleaner winCleaner) {
		return getCurrentWinStartX(winCleaner) + winWidth;
	}
}