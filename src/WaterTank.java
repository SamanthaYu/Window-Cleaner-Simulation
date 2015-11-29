import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class WaterTank {
	BufferedImage wtankImage;
	Graphics2D g2wtank;
	
	public WaterTank(WindowCleaner winCleaner) {
		wtankImage = new BufferedImage(winCleaner.getAppWidth(), winCleaner.getAppHeight(), BufferedImage.TYPE_INT_ARGB);
		g2wtank = (Graphics2D) wtankImage.getGraphics();
	}
	
	public BufferedImage getWTankImage() {
		return wtankImage;
	}
}
