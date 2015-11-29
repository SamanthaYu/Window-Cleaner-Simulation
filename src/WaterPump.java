import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class WaterPump {
	BufferedImage wpumpImage;
	Graphics2D g2wpump;
	
	public WaterPump(WindowCleaner winCleaner) {
		wpumpImage = new BufferedImage(winCleaner.getAppWidth(), winCleaner.getAppHeight(), BufferedImage.TYPE_INT_ARGB);
		g2wpump = (Graphics2D) wpumpImage.getGraphics();
	}
	
	public BufferedImage getWPumpImage() {
		return wpumpImage;
	}
}
