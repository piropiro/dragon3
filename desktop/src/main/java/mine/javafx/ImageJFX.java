/*
 * Created on 2004/10/10
 */
package mine.javafx;


import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

/**
 * @author saito
 */
public class ImageJFX implements MineImage {

	private Image image;
	
	public ImageJFX(Image image) {
		this.image = image;
	}
	
	@Override
	public Object getImage() {
		return image;
	}
	
	@Override
	public int getWidth() {
		return (int)image.getWidth();
	}
	
	@Override
	public int getHeight() {
		return (int)image.getHeight();
	}

	@Override
	public MineGraphics getGraphics() {
		throw new UnsupportedOperationException();
		//return new GraphicsAWT(image.getGraphics());
	}

	@Override
	public MineImage getSubimage(int sx, int sy, int w, int h) {
		WritableImage subimage = new WritableImage(w, h);
		PixelWriter pw = subimage.getPixelWriter();
		pw.setPixels(0, 0, w, h, image.getPixelReader(), sx, sy);
		return new ImageJFX(subimage);
	}
	
	@Override
	public MineImage getCopy() {
		return getSubimage(0, 0, getWidth(), getHeight());
	}
}
