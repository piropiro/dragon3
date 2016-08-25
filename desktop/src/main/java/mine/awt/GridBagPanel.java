package mine.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * GridBagLayoutを使うためのパネル。
 * サイズを固定することができる。
 * @author k-saito
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GridBagPanel extends JPanel {

	public static final int NONE = GridBagConstraints.NONE;
	public static final int BOTH = GridBagConstraints.BOTH;
	public static final int HORIZONTAL = GridBagConstraints.HORIZONTAL;
	public static final int VERTICAL = GridBagConstraints.VERTICAL;

	private GridBagLayout layout;
	private GridBagConstraints c;
	private int width, height;
	private boolean flexible;

	/**
	 * コンストラクタ（サイズ可変）
	 */
	public GridBagPanel() {
		super();
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		setFill(BOTH);
		setLayout(layout);
		setFlexible(true);
	}

	/**
	 * コンストラクタ（サイズ固定）
	 * @param width - 指定された幅
	 * @param height - 指定された高さ
	 */
	public GridBagPanel(int width, int height) {
		this();
		this.setSize(width, height);
		this.setFlexible(false);
	}

	/**
	 * コンポーネントを配置する
	 * @param obj - 配置するコンポーネント
	 * @param x - 配置位置X
	 * @param y - 配置位置Y
	 * @param xs - 配置サイズX
	 * @param ys - 配置サイズY
	 */
	public void add(Component obj, int x, int y, int xs, int ys) {
		add(obj, x, y, xs, ys, 0, 0);
	}

	/**
	 * コンポーネントを配置する
	 * @param obj - 配置するコンポーネント
	 * @param x - 配置位置X
	 * @param y - 配置位置Y
	 * @param xs - 配置サイズX
	 * @param ys - 配置サイズY
	 * @param wx - 配置比重X
	 * @param wy - 配置比重Y
	 */
	public void add(
		Component obj,
		int x,
		int y,
		int xs,
		int ys,
		int wx,
		int wy) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = xs;
		c.gridheight = ys;
		c.weightx = wx;
		c.weighty = wy;
		layout.setConstraints(obj, c);
		add(obj);
	}

	/**
	 * GridBagLayoutのFillを指定する。
	 */
	public void setFill(int f) {
		c.fill = f;
	}

	/**
	 * 指定されたサイズに変更する。
	 * @param width - 指定された幅
	 * @param height - 指定された高さ
	 */
	public void setSize(int width, int height) {
		super.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	/**
	 * サイズが可変か固定かを指定する。
	 * @param flexible - サイズが可変ならtrue
	 */
	public void setFlexible(boolean flexible) {
		this.flexible = flexible;
	}

	/**
	 * 最小サイズを返す。
	 * @return 最小サイズ
	 */
	public Dimension getMinimumSize() {
		if (flexible) {
			return super.getMinimumSize();
		} else {
			return new Dimension(width, height);

		}
	}

	/**
	 * 最大サイズを返す。
	 * @return 最大サイズ
	 */
	public Dimension getMaximumSize() {
		if (flexible) {
			return super.getMaximumSize();
		} else {
			return new Dimension(width, height);

		}
	}

	/**
	 * 推奨サイズを返す。
	 * @return 推奨サイズ
	 */
	public Dimension getPreferredSize() {
		if (flexible) {
			return super.getPreferredSize();
		} else {
			return new Dimension(width, height);
		}
	}

	/**
	 * バックグラウンドカラーで画面をクリアする。
	 */
	public void clear(Graphics g) {
		Color current = g.getColor();
		g.setColor(getBackground());
		g.fillRect(0, 0, width, height);
		g.setColor(current);
	}
}
