/*
 * Created on 2004/10/10
 */
package mine.paint;



/**
 * @author saito
 */
public interface MineGraphics {
	
	public void setColor(MineColor color);
	
	public void setColor(int r, int g, int b);
	
	public void setColor(int r, int g, int b, int a);

	public void setColor(int[] rgba);
	
	public void fillRect(int x, int y, int w, int h);
	
	public void drawRect(int x, int y, int w, int h);
	
	public void drawLine(int sx, int sy, int dx, int dy);
	
	public void drawString(String s, int x, int y);
	
	public void drawImage(MineImage image, int x, int y);
	
	public void drawImage(MineImage image, int sx, int sy, int w, int h, int dx, int dy);

	public void drawFitImage(MineImage image, int dw, int dh);


	public void drawRotateImage(MineImage image, int dx, int dy, double angle);

	public void setAlpha(double alpha);
	
	public void setAntialias(boolean flag);
	
	public void setFont(String font, int size);

	/**
	 * 文字列を中央に描画する。<p>
	 * 
	 * @param s
	 * @param x
	 * @param y
	 * @param xs
	 * @param g
	 */
	public void drawString(String s, int x, int y, int xs);
}
