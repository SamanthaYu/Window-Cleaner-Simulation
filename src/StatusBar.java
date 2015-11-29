import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class StatusBar {
	private BufferedImage sbarImage;
	private Graphics2D g2sbar;
	private Color sbarColor;
	private int sbarWidth;
	
	public StatusBar(WindowCleaner winCleaner) {
		sbarWidth = 250;
		sbarColor = new Color(222,242,206);
		
		sbarImage = new BufferedImage(winCleaner.getAppWidth(), winCleaner.getAppHeight(), BufferedImage.TYPE_INT_ARGB);
		g2sbar = (Graphics2D) sbarImage.getGraphics();
		
		drawStatusBar(winCleaner);
	}
	
	private void drawStatusBar(WindowCleaner winCleaner) {
		g2sbar.setColor(sbarColor);
		g2sbar.fillRect(winCleaner.getAppWidth() - sbarWidth, 0, sbarWidth, winCleaner.getAppHeight());
	}
	
	public BufferedImage getSBarImage() {
		return sbarImage;
	}
	
	public int getSBarWidth() {
		return sbarWidth;
	}
}