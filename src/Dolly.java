import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class Dolly {
	private int dollyHeight, wireStrokeSize;
	private double dollyCleanWTank, dollyDirtyWTank, dollyMaxCleanWTank, dollyMaxDirtyWTank;
	private Color dollyColor, wireColor;
	private BasicStroke wireStroke;
	private boolean fillingDollyCleanWTank, emptyingDollyDirtyWTank;
	
	public Dolly(WindowCleaner winCleaner) {
		dollyHeight = 50;
		dollyColor = new Color(94,94,94);
		wireColor = new Color(0,0,0);
		
		wireStrokeSize = 2;
		wireStroke = new BasicStroke(wireStrokeSize);
		
		dollyCleanWTank = dollyMaxCleanWTank = 70;
		dollyMaxDirtyWTank = 30;
		dollyDirtyWTank = 0;
	}
	
	public void drawDolly(WindowCleaner winCleaner, Building building, Graphics2D g2) {
		g2.setColor(dollyColor);
		g2.fillRect(winCleaner.getWCstartX(), building.getBuildingStartX(), winCleaner.getWCwidth(), dollyHeight);	// Draws dolly
		
		g2.setColor(wireColor);
		g2.setStroke(wireStroke);
		g2.drawLine(winCleaner.getWCstartX(), building.getBuildingStartY(), winCleaner.getWCstartX(), winCleaner.getWCstartY()); 	// Draws left wire
		g2.drawLine(winCleaner.getWCendX(), building.getBuildingStartY(), winCleaner.getWCendX(), winCleaner.getWCstartY()); 		// Draws right wire
	}
	
	public void checkTanks(WindowCleaner winCleaner, ChemicalSpray cspray, WaterTank wtank) {
		checkCleanWTank(winCleaner, wtank);
		checkDirtyWTank(winCleaner, cspray, wtank);
	}
	
	private void checkCleanWTank(WindowCleaner winCleaner, WaterTank wtank) {
		if (wtank.getFillingWaterTank())
			dollyCleanWTank -= wtank.getWaterFillRate();
		
		if (dollyCleanWTank < 20) {
			fillingDollyCleanWTank = true;
		}
		
		if (fillingDollyCleanWTank) {
			dollyCleanWTank += 2*wtank.getWaterFillRate();
			
			if (dollyCleanWTank > 30) {
				fillingDollyCleanWTank = false;
			}
		}
	}
	
	private void checkDirtyWTank(WindowCleaner winCleaner, ChemicalSpray cspray, WaterTank wtank) {
		if ((winCleaner.getCurrentCleaning())	// When the window cleaner is cleaning, chemicals and water are used 
			&& (dollyDirtyWTank + cspray.getChemicalLossRate() + wtank.getWaterLossRate() <= dollyMaxDirtyWTank)	// Collects both chemicals and dirty water
			&& (cspray.getChemicalLossRate() + wtank.getWaterLossRate() <= wtank.getWaterPumpRate())) {	// Cannot go over the max water pump rate
			
			dollyDirtyWTank += cspray.getChemicalLossRate() + wtank.getWaterLossRate();
		}
		else if ((winCleaner.getCurrentCleaning()) && (cspray.getChemicalLossRate() + wtank.getWaterLossRate() > wtank.getWaterPumpRate())) {
			dollyDirtyWTank += wtank.getWaterPumpRate();
		}
			
		if (dollyDirtyWTank > 25) {
			emptyingDollyDirtyWTank = true;
		}
		
		if (emptyingDollyDirtyWTank) {
			dollyDirtyWTank -= wtank.getWaterPumpRate();
			
			if (dollyDirtyWTank > 10) {
				emptyingDollyDirtyWTank = false;
			}
		}
	}
	
	public double getDollyCleanWTank() {
		return dollyCleanWTank;
	}
	
	public double getDollyDirtyWTank() {
		return dollyDirtyWTank;
	}
	
	public boolean getFillingCleanWTank() {
		return fillingDollyCleanWTank;
	}
	
	public boolean getEmptyingDirtyWTank() {
		return emptyingDollyDirtyWTank;
	}
	
	public double getDollyMaxCleanWTank() {
		return dollyMaxCleanWTank;
	}
	
	public double getDollyMaxDirtyWTank() {
		return dollyMaxDirtyWTank;
	}
}
