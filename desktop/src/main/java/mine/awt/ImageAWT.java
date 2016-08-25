/*
 * Created on 2004/10/10
 */
package mine.awt;

import java.awt.image.BufferedImage;

import mine.paint.MineGraphics;
import mine.paint.MineImage;

/**
 * @author saito
 */
public class ImageAWT implements MineImage {

	private BufferedImage image;
	
	public ImageAWT(BufferedImage image) {
		this.image = image;
	}
	
	public Object getImage() {
		return image;
	}
	
	public int getWidth() {
		return image.getWidth(null);
	}
	
	public int getHeight() {
		return image.getHeight(null);
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineImage#getGraphics()
	 */
	public MineGraphics getGraphics() {
		return new GraphicsAWT(image.getGraphics());
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineImage#getSubimage(int, int, int, int)
	 */
	public MineImage getSubimage(int x, int y, int w, int h) {
		return new ImageAWT(image.getSubimage(x, y, w, h));
	}
	
	public MineImage getCopy() {
		BufferedImage copy = new BufferedImage(image.getWidth(), image.getWidth(), BufferedImage.TYPE_INT_ARGB);
		copy.getGraphics().drawImage(image, 0, 0, null);
		return new ImageAWT(copy);
	}
}
