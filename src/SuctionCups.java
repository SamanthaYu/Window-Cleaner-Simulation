import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class SuctionCups {
	private BufferedImage scupsImage;
	private Graphics2D g2scups;
	
	private int armsLength, scupsDiameter;
	
	public SuctionCups(WindowCleaner winCleaner) {
		scupsImage = new BufferedImage(winCleaner.getAppWidth(), winCleaner.getAppHeight(), BufferedImage.TYPE_INT_ARGB);
		g2scups = (Graphics2D) scupsImage.getGraphics();
		
		setSCdimensions();
	}
	
	public void setSCdimensions() {
		armsLength = 10;
		scupsDiameter = 5;
	}
	
	public BufferedImage getSCupsImage() {
		return scupsImage;
	}
	
	public int getArmsLength() {
		return armsLength;
	}
	
	public int getSCupsDiameter() {
		return scupsDiameter;
	}
}
