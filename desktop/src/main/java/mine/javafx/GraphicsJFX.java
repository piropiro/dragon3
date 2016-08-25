package mine.javafx;


import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

/**
 * @author saito
 */
public class GraphicsJFX implements MineGraphics {
	
	private GraphicsContext g;
	
	public GraphicsJFX(GraphicsContext g) {
		this.g = g;
	}

	/**
	 * 名前を指定してカラーを取得する。<p>
	 * 
	 * @param i カラー番号
	 * @return
	 */
	private static Color getColorOfName(MineColor i) {
		switch (i) {
			case BLACK :
				return new Color(0.0, 0.0, 0.0, 1.0);
			case BLUE :
				return new Color(0.0, 0.0, 1.0, 1.0);
			case LIME :
				return new Color(0.0, 1.0, 0.0, 1.0);
			case AQUA :
				return new Color(0.0, 1.0, 1.0, 1.0);
			case RED :
				return new Color(1.0, 0.0, 0.0, 1.0);
			case PINK :
				return new Color(1.0, 0.0, 1.0, 1.0);
			case YELLOW :
				return new Color(1.0, 1.0, 0.0, 1.0);
			case WHITE :
				return new Color(1.0, 1.0, 1.0, 1.0);
			case GRAY :
				return new Color(0.5, 0.5, 0.5, 1.0);
			case NAVY :
				return new Color(0.0, 0.0, 0.5, 1.0);
			case GREEN :
				return new Color(0.0, 0.5, 0.0, 1.0);
			case TEAL :
				return new Color(0.0, 0.5, 0.5, 1.0);
			case MAROON :
				return new Color(0.5, 0.0, 0.0, 1.0);
			case PURPLE :
				return new Color(0.5, 0.0, 0.5, 1.0);
			case OLIVE :
				return new Color(0.5, 0.5, 0.0, 1.0);
			case SILVER :
				return new Color(0.8, 0.8, 0.8, 1.0);
			case ORANGE :
				return new Color(1.0, 0.5, 0.0, 1.0);
			default :
				return new Color(0.0, 0.0, 0.0, 1.0);
		}
	}

	private void setColor(Color color) {
		g.setFill(color);
		g.setStroke(color);
	}
	
	@Override
	public void setColor(MineColor mineColor) {
		setColor(getColorOfName(mineColor));
	}
	
	@Override
	public void setColor(int red, int green, int blue) {
		setColor(red, green, blue, 0xff);
	}

	@Override
	public void setColor(int red, int green, int blue, int alpha) {
		setColor(new Color(red / 255.0, green / 255.0, blue / 255.0, alpha / 255.0));
	}

	@Override
	public void setColor(int[] rgba) {
		setColor(rgba[0], rgba[1], rgba[2], rgba[3]);
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#fillRext(int, int, int, int)
	 */
	public void fillRect(int x, int y, int w, int h) {
		g.fillRect(x, y, w, h);
	}

	@Override
	public void drawString(String s, int x, int y) {
		g.strokeText(s, x, y);
	}

	@Override
	public void drawRect(int x, int y, int w, int h) {
		g.strokeRect(x, y, w, h);
	}

	@Override
	public void drawLine(int sx, int sy, int dx, int dy) {
		g.strokeLine(sx, sy, dx, dy);
	}

	@Override
	public void drawImage(MineImage image, int x, int y) {
		g.drawImage((Image)image.getImage(), x, y);
	}

	@Override
	public void drawImage(MineImage image, int sx, int sy, int w, int h, int dx, int dy) {
		int axs = dx;
		int ays = dy;
		int axf = dx + w;
		int ayf = dy + h;
		int bxs = sx;
		int bys = sy;
		int bxf = sx + w;
		int byf = sy + h;
		g.drawImage((Image)image.getImage(), axs, ays, axf, ayf, bxs, bys, bxf, byf);
	}


	@Override
	public void setAntialias(boolean flag) {
		
//		Object alias;
//		if (flag) {
//			alias = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
//		} else {
//			alias = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
//		}
//		RenderingHints.Key key = RenderingHints.KEY_TEXT_ANTIALIASING;
//		RenderingHints hints = new RenderingHints(key, alias);
//		((Graphics2D) g).setRenderingHints(hints);		
	}

	@Override
	public void setFont(String font, int size) {
		g.setFont(new Font(font, size));
	}

	/**
	 * 文字列を中央に描画する。<p>
	 * 
	 */
	@Override
	public void drawString(String text, int x, int y, int xs) {

		g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
        g.fillText(text, x, y);
	}

	@Override
	public void drawRotateImage(MineImage mimg, int dx, int dy, double theta) {
		g.save();
		int w = mimg.getWidth();
		int h = mimg.getHeight();
		double angle = 360 * theta / Math.PI;
		Rotate r = new Rotate(angle, dx + w / 2, dy + h / 2);
        g.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

		g.drawImage((Image)mimg.getImage(), dx, dy);
		g.restore();		
	}

	@Override
	public void setAlpha(double alpha) {
		g.setGlobalAlpha(alpha);
	}




}
