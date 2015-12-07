import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class Dolly {
	private int dollyHeight, wireStrokeSize;
	private Color dollyColor, wireColor;
	private BasicStroke wireStroke;
	
	public Dolly(WindowCleaner winCleaner) {
		dollyHeight = 50;
		dollyColor = new Color(44,71,112);
		wireColor = new Color(0,0,0);
		
		wireStrokeSize = 2;
		wireStroke = new BasicStroke(wireStrokeSize);
	}
	
	public void drawDolly(WindowCleaner winCleaner, Building building, Graphics2D g2) {
		g2.setColor(dollyColor);
		g2.fillRect(winCleaner.getWCstartX(), building.getBuildingStartX(), winCleaner.getWCwidth(), dollyHeight);	// Draws dolly
		
		g2.setColor(wireColor);
		g2.setStroke(wireStroke);
		g2.drawLine(winCleaner.getWCstartX(), building.getBuildingStartY(), winCleaner.getWCstartX(), winCleaner.getWCstartY()); 	// Draws left wire
		g2.drawLine(winCleaner.getWCendX(), building.getBuildingStartY(), winCleaner.getWCendX(), winCleaner.getWCstartY()); 		// Draws right wire
	}
}
