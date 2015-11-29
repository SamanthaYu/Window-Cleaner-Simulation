import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Dolly {
	private BufferedImage dollyImage;
	private Graphics2D g2dolly;
	
	public Dolly(WindowCleaner winCleaner) {
		dollyImage = new BufferedImage(winCleaner.getAppWidth(), winCleaner.getAppHeight(), BufferedImage.TYPE_INT_ARGB);
		g2dolly = (Graphics2D) dollyImage.getGraphics();
	}
	
	public BufferedImage getDollyImage() {
		return dollyImage;
	}
}
