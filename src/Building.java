import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;


public class Building {
	private BufferedImage buildingImage;
	private Graphics2D g2building;
	private Color dirtyWinColor, cleanWinColor, paneColor;
	
	private int buildingStartX, buildingStartY, buildingWidth, buildingHeight;
	private int winStartX, winStartY, winWidth, winHeight, paneWidth, numWinX;
	
	public Building(WindowCleaner winCleaner, StatusBar sbar) {
		setBdimensions(winCleaner, sbar);
		
		buildingImage = new BufferedImage(winCleaner.getAppWidth(), winCleaner.getAppHeight(), BufferedImage.TYPE_INT_ARGB);
		g2building = (Graphics2D) buildingImage.getGraphics();
		
		createBuilding();
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
	
	private void createBuilding() {
		do {
			numWinX += 1;
			System.out.println("numWinX: " + numWinX);
			do {
				createWindow(false, winStartX, winStartY);
				winStartY += winHeight + paneWidth;
			} while (winStartY <= buildingHeight);
			
			winStartX += winWidth + paneWidth;
			winStartY = buildingStartY;
		} while (winStartX <= buildingWidth);
	}
	
	private void createWindow(Boolean clean, int winStartX, int winStartY) {
		if (clean) {
			g2building.setColor(cleanWinColor);
		} else {
			g2building.setColor(dirtyWinColor);
		}
		
		g2building.fillRect(winStartX, winStartY, winWidth, winHeight);
		
		g2building.setColor(paneColor);
		g2building.setStroke(new BasicStroke(paneWidth));
		g2building.drawRect(winStartX, winStartY, winWidth, winHeight);
	}
	
	public BufferedImage getBuildingImage() {
		return buildingImage;
	}
	
	public int getBuildingStartX() {
		return buildingStartX;
	}
	
	public int getBuildingStartY() {
		return buildingStartY;
	}
	
	public int getBuildingWidth() {
		return buildingWidth;
	}
	
	public int getBuildingHeight() {
		return buildingHeight;
	}
	
	public int getWindowWidth() {
		return winWidth + 2*paneWidth;
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
}