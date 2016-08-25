package dragon3.anime.item;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.util.Bounder;

public class Number {

	private static final Bounder BOUNDER;
	private static final int BOUND_MAX = 40;

	
	private int count;
	private int n;
	private int x;
	private int y;


	static {
		BOUNDER = new Bounder(32, 1, 0.6, BOUND_MAX);
	}

	public Number(int n, int x, int count) {
		this.n = n;
		this.x = x;
		this.y = 0;
		this.count = count;
	}

	public void move() {
		count = Math.min(count + 1, BOUND_MAX);
		y = BOUNDER.getY(count);
	}
	public boolean end() {
		return (count >= BOUND_MAX);
	}

	public void display(MineGraphics g, MineImage[] num) {
		if (y >= 0) {
			g.drawImage(num[n], x, 20 - y);
		}
	}
	/**
	 * @return Returns the n.
	 */
	public int getN() {
		return n;
	}
	/**
	 * @param n The n to set.
	 */
	public void setN(int n) {
		this.n = n;
	}
	/**
	 * @return Returns the x.
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x The x to set.
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return Returns the y.
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y The y to set.
	 */
	public void setY(int y) {
		this.y = y;
	}
}
