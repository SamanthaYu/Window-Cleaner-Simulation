import java.awt.Graphics2D;
import java.math.RoundingMode;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class StatusBar {
	private Color sbarColor, fontColor;
	private int sbarWidth, sbarStartX, textStartX, textStartY;
	private ArrayList<String> headingArray, valueArray;
	private Font statusFont;
	private DecimalFormat df2;
	
	public StatusBar(WindowCleaner winCleaner) {
		sbarWidth = 250;
		sbarStartX = winCleaner.getAppWidth() - sbarWidth;
		
		sbarColor = new Color(128,183,159);
		fontColor = new Color(0,0,0);
		statusFont = new Font("Times New Roman", Font.PLAIN, 18);
		
		textStartX = getSBarStartX() + 5;
		textStartY = 25;
		
		df2 = new DecimalFormat("#.##");
		df2.setRoundingMode(RoundingMode.CEILING);
	}
	
	public void drawStatusBar(WindowCleaner winCleaner, Graphics2D g2) {
		g2.setColor(sbarColor);
		g2.setFont(statusFont);
		g2.fillRect(getSBarStartX(), 0, sbarWidth, winCleaner.getAppHeight());
		textStartY = 25;
		
		for (int k = 0; k<headingArray.size(); k++) {
			textStartY += 30;
			drawText(g2, headingArray.get(k), valueArray.get(k));
		}
	}
	
	public void updateStatus(WindowCleaner winCleaner, Building building, ChemicalSpray cspray, Dolly dolly, SuctionCups scups, WaterTank wtank) {
		headingArray = new ArrayList<String>();
		valueArray = new ArrayList<String>();
		
		headingArray.add("Window Cleaner Speed: ");
		valueArray.add("0.1 m/s");
		
		headingArray.add("Number of Window Columns: ");
		valueArray.add(String.valueOf(building.getNumWinX()));
		
		headingArray.add("Window Columns Cleaned: ");
		valueArray.add(String.valueOf(winCleaner.getCurrentWinXNum() - 1));
		
		headingArray.add("Left and right suction cups: ");
		if (scups.getLRscupsOn())
			valueArray.add("On");
		else
			valueArray.add("Off");
		
		headingArray.add("Middle Suction Cup: ");
		if (scups.getMiddleSCupOn())
			valueArray.add("On");
		else
			valueArray.add("Off");
		
		headingArray.add("Chemical Tank: ");
		valueArray.add(String.valueOf(df2.format(cspray.getChemicalTank())) + " gallons");
		
		headingArray.add("Filling Chemical Tank: ");
		if (cspray.getFillingChemTank())
			valueArray.add("On");
		else
			valueArray.add("Off");
		
		headingArray.add("Onboard Water Tank: ");
		valueArray.add("");
		headingArray.add("     ");
		valueArray.add(String.valueOf(df2.format(wtank.getCleanWaterTank())) + " gallons");
		
		headingArray.add("Filling Onboard Water Tank: ");
		valueArray.add("");
		headingArray.add("     ");
		if (wtank.getFillingWaterTank())
			valueArray.add("On");
		else
			valueArray.add("Off");
		
		headingArray.add("Dolly Clean Water Tank: ");
		valueArray.add("");
		headingArray.add("     ");
		valueArray.add(String.valueOf(df2.format(dolly.getDollyCleanWTank())) + " gallons");
		
		headingArray.add("Filling Dolly Water Tank: ");
		if (dolly.getFillingCleanWTank())
			valueArray.add("On");
		else
			valueArray.add("Off");
		
		headingArray.add("Maximum Dolly Dirty Water");
		valueArray.add("");
		headingArray.add("     Tank Volume: ");
		valueArray.add(String.valueOf(df2.format(dolly.getDollyMaxDirtyWTank())) + " gallons");
		
		headingArray.add("Dolly Dirty Water Tank: ");
		valueArray.add("");
		headingArray.add("     ");
		valueArray.add(String.valueOf(df2.format(dolly.getDollyDirtyWTank())) + " gallons");
		
		headingArray.add("Emptying Dolly Water Tank: ");
		valueArray.add("");
		headingArray.add("     ");
		if (dolly.getEmptyingDirtyWTank())
			valueArray.add("On");
		else
			valueArray.add("Off");
		
		headingArray.add("");
		valueArray.add("");
		headingArray.add("Please note: Dimensions are not ");
		valueArray.add("");
		headingArray.add("completely to scale.");
		valueArray.add("");
	}
	
	private void drawText(Graphics2D g2, String heading, String value) {
		g2.setColor(fontColor);
		g2.drawString(heading + value, textStartX, textStartY);
	}
	
	public int getSBarWidth() {
		return sbarWidth;
	}
	
	public int getSBarStartX() {
		return sbarStartX;
	}
}