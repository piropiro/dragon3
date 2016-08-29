/*
 * Created on 2004/10/10
 */
package mine.awt;

import org.jetbrains.annotations.NotNull;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

/**
 * @author saito
 */
public class GraphicsAWT implements MineGraphics {
	
	private Graphics g;
	
	public GraphicsAWT(Graphics g) {
		this.g = g;
	}

	/**
	 * 名前を指定してカラーを取得する。<p>
	 * 
	 * @param i カラー番号
	 * @return
	 */
	private static Color getColorOfName(MineColor i) {
		int[] rgb = i.getRgb();
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public void setColor(MineColor color) {
		g.setColor(getColorOfName(color));
	}
	
	public void setColor(int red, int green, int blue) {
		g.setColor(new Color(red, green, blue));
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#setColor(int, int, int, int)
	 */
	public void setColor(int red, int green, int blue, int alpha) {
		g.setColor(new Color(red, green, blue, alpha));		
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#setColor(int[])
	 */
	public void setColor(int[] rgba) {
		if (rgba.length == 3) {
			g.setColor(new Color(rgba[0], rgba[1], rgba[2]));
		} else {
			g.setColor(new Color(rgba[0], rgba[1], rgba[2], rgba[3]));
		}
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#fillRext(int, int, int, int)
	 */
	public void fillRect(int x, int y, int w, int h) {
		g.fillRect(x, y, w, h);
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#drawString(java.lang.String, int, int)
	 */
	public void drawString(String s, int x, int y) {
		g.drawString(s, x, y);
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#drawRect(int, int, int, int)
	 */
	public void drawRect(int x, int y, int w, int h) {
		g.drawRect(x, y, w, h);
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#drawLine(int, int, int, int)
	 */
	public void drawLine(int sx, int sy, int dx, int dy) {
		g.drawLine(sx, sy, dx, dy);
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#drawImage(mine.paint.MineImage, int, int)
	 */
	public void drawImage(MineImage image, int x, int y) {
		g.drawImage((Image)image.getImage(), x, y, null);
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#drawImage(mine.paint.MineImage, int, int, int, int, int, int)
	 */
	public void drawImage(MineImage image, int sx, int sy, int w, int h, int dx, int dy) {
		int axs = dx;
		int ays = dy;
		int axf = dx + w;
		int ayf = dy + h;
		int bxs = sx;
		int bys = sy;
		int bxf = sx + w;
		int byf = sy + h;
		g.drawImage((Image)image.getImage(), axs, ays, axf, ayf, bxs, bys, bxf, byf, null);
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#setAlpha(double)
	 */
	public void setAlpha(double alpha) {
		AlphaComposite ac =
			AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha);
		((Graphics2D) g).setComposite(ac);
	}


	public void setAntialias(boolean flag) {
		Object alias;
		if (flag) {
			alias = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
		} else {
			alias = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
		}
		RenderingHints.Key key = RenderingHints.KEY_TEXT_ANTIALIASING;
		RenderingHints hints = new RenderingHints(key, alias);
		((Graphics2D) g).setRenderingHints(hints);		
	}

	/* (non-Javadoc)
	 * @see mine.paint.MineGraphics#setFont(java.lang.String, int)
	 */
	public void setFont(String font, int size) {
		g.setFont(new Font(font, Font.PLAIN, size));
	}

	/**
	 * 文字列を中央に描画する。<p>
	 * 
	 * @param s
	 * @param x
	 * @param y
	 * @param xs
	 */
	public void drawString(String s, int x, int y, int xs) {
		Font f = g.getFont();
		int width = g.getFontMetrics(f).stringWidth(s);
		g.drawString(s, x + (xs - width) / 2, y);
	}

	/**
	 * イメージの一部を回転させます。
	 * @param mimg - ソースイメージ
	 * @param x - 回転の中心座標X
	 * @param y - 回転の中心座標Y
	 * @param w - 回転させる部分の幅
	 * @param h - 回転させる部分の高さ
	 * @param theta - 回転させる角度
	 * @return 指定された一部を回転させたイメージ
	 */
	public MineImage rotate(MineImage mimg, int x, int y, int w, int h, double theta) {
		BufferedImage img = (BufferedImage)mimg.getImage();
		BufferedImage rimg = new BufferedImage(w, h, img.getType());
		Graphics2D g2 = (Graphics2D) rimg.getGraphics();
		g2.rotate(theta, w / 2, h / 2);
		g2.drawImage(img, 0, 0, w, h, x, y, x + w, y + h, null);
		return new ImageAWT(rimg);
	}

	/**
	 * イメージを回転させて描画する。<p>
	 * 
	 */
	public void drawRotateImage(
		MineImage mimg,
		int sx,
		int sy,
		int w,
		int h,
		int dx,
		int dy,
		double theta) {
		BufferedImage img = (BufferedImage)mimg.getImage();
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = g2.getTransform();
		g2.rotate(theta, dx + w / 2, dy + h / 2);
		g2.drawImage(img, dx, dy, dx + w, dy + h, sx, sy, sx + w, sy + h, null);
		g2.setTransform(at);
	}

	@Override
	public void drawRotateImage(MineImage mimg, int dx, int dy, double theta) {
		BufferedImage img = (BufferedImage)mimg.getImage();
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = g2.getTransform();
		int sx = 0;
		int sy = 0;
		int w = img.getWidth();
		int h = img.getHeight();
		g2.rotate(theta, dx + w / 2, dy + h / 2);
		g2.drawImage(img, dx, dy, dx + w, dy + h, sx, sy, sx + w, sy + h, null);
		g2.setTransform(at);		
	}


	@Override
	public void drawFitImage(@NotNull MineImage image, int dw, int dh) {
		g.drawImage((BufferedImage)image.getImage(), 0, 0, dw, dh, null);
	}
}
