/*
 * Created on 2004/10/10
 */
package mine.paint;

/**
 * @author saito
 */
public interface MineImage {
	public Object getImage();
	
	public int getWidth();
	
	public int getHeight();
	
	public MineGraphics getGraphics();
	
	public MineImage getSubimage(int x, int y, int w, int h);
	
	public MineImage getCopy();
}
