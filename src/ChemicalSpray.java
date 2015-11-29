import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class ChemicalSpray {
	private BufferedImage csprayImage;
	private Graphics2D g2cspray;
	
	public ChemicalSpray(WindowCleaner winCleaner) {
		csprayImage = new BufferedImage(winCleaner.getAppWidth(), winCleaner.getAppHeight(), BufferedImage.TYPE_INT_ARGB);
		g2cspray = (Graphics2D) csprayImage.getGraphics();
	}
	
	public BufferedImage getCSprayImage() {
		return csprayImage;
	}
}
