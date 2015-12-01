import java.awt.Graphics2D;
import java.awt.Color;

public class StatusBar {
	private Color sbarColor;
	private int sbarWidth;
	
	public StatusBar(WindowCleaner winCleaner) {
		sbarWidth = 250;
		sbarColor = new Color(222,242,206);
	}
	
	public void drawStatusBar(WindowCleaner winCleaner, Graphics2D g2) {
		g2.setColor(sbarColor);
		g2.fillRect(winCleaner.getAppWidth() - sbarWidth, 0, sbarWidth, winCleaner.getAppHeight());
	}
	
	public int getSBarWidth() {
		return sbarWidth;
	}
}