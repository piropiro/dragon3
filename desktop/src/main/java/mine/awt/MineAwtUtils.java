/*
 * Created on 2004/10/10
 */
package mine.awt;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.UIManager;

/**
 * @author saito
 */
public class MineAwtUtils {

	/**
	 * Look&Feel Metal
	 */
	public static final int METAL = 1;
	
	/**
	 * Look&Feel Motif
	 */
	public static final int MOTIF = 2;
	
	/**
	 * Look&Feel Motif
	 */
	public static final int WINDOWS = 3;

	/**
	 * 指定されたサイズのDialogフォントを返す。<p>
	 * 
	 * @param size
	 * @return
	 */
	public static Font getFont(int size) {
		return new Font("Dialog", Font.PLAIN, size);
	}

	/**
	 * コンポーネントのサイズを設定する。<p>
	 * 
	 * @param c
	 * @param width
	 * @param height
	 */
	public static void setSize(JComponent c, int width, int height) {
		Dimension size = new Dimension(width, height);
		c.setSize(size);
		c.setPreferredSize(size);
		c.setMinimumSize(size);
		c.setMaximumSize(size);
	}

	/**
	 * Look&Feelを設定する。<p>
	 * 
	 * @param type
	 */
	public static void setLookAndFeel(int type) {
		try {
			switch (type) {
				case METAL :
					String metal = "javax.swing.plaf.metal.MetalLookAndFeel";
					UIManager.setLookAndFeel(metal);
					break;
				case MOTIF :
					String motif =
						"com.sun.java.swing.plaf.motif.MotifLookAndFeel";
					UIManager.setLookAndFeel(motif);
					break;
				case WINDOWS :
					String windows =
						"com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
					UIManager.setLookAndFeel(windows);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文字列を中央に描画する。<p>
	 * 
	 * @param s
	 * @param x
	 * @param y
	 * @param xs
	 * @param g
	 */
	public static void drawString(String s, int x, int y, int xs, Graphics g) {
		Font f = g.getFont();
		int width = g.getFontMetrics(f).stringWidth(s);
		g.drawString(s, x + (xs - width) / 2, y);
	}

	/**
	 * ウインドウを中央に移動する。<p>
	 * 
	 * @param w
	 */
	public static void setCenter(Window w) {
		Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension f = w.getSize();
		f.height = Math.min(f.height, s.height);
		f.width = Math.min(f.width, s.width);
		w.setLocation((s.width - f.width) / 2, (s.height - f.height) / 2);
	}

	/**
	 * アルファ値を設定する。<p>
	 * 
	 * @param g
	 * @param alpha
	 */
	public static void setAlpha(Graphics g, double alpha) {
		AlphaComposite ac =
			AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha);
		((Graphics2D) g).setComposite(ac);
	}

	/**
	 * アンチエイリアシングを設定する。<p>
	 * 
	 * @param g
	 * @param flag
	 */
	public static void setAntialias(Graphics g, boolean flag) {
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


	/**
	 * 縁取りした文字を描画する。<p>
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param xs
	 * @param g
	 */
	public static void drawText(String text, int x, int y, int xs, Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Font f = g.getFont();
		int width = g.getFontMetrics(f).stringWidth(text);
		FontRenderContext frc = g2.getFontRenderContext();
		TextLayout tl = new TextLayout(text, f, frc);
		Shape sha = tl.getOutline(AffineTransform.getTranslateInstance(x + (xs - width), y));
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(2));
		g2.draw(sha);
		g2.setColor(Color.white);
		g2.fill(sha);
	}
}
