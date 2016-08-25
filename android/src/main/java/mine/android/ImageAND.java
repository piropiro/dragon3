/*
 * Created on 2004/10/10
 */
package mine.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import mine.paint.MineGraphics;
import mine.paint.MineImage;

/**
 * @author saito
 */
public class ImageAND implements MineImage {

	private Bitmap image;

	public ImageAND(Bitmap image) {
		this.image = image;
	}
	
	public Object getImage() {
		return image;
	}
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineImage#getGraphics()
	 */
	public MineGraphics getGraphics() {
		return new GraphicsAND(new Canvas(image));
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineImage#getSubimage(int, int, int, int)
	 */
	public MineImage getSubimage(int x, int y, int w, int h) {
		Bitmap subimage = Bitmap.createBitmap(image, x, y, w, h);
		return new ImageAND(subimage);
	}
	
	public MineImage getCopy() {
		return new ImageAND(Bitmap.createBitmap(image));
	}
}
