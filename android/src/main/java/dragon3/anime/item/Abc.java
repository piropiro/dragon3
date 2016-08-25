package dragon3.anime.item;

public class Abc {

	private static final int v = 2;

	private int w; // Character Size
	private int xf; // End Point
	private int x;
	private int y;
	private int wait;
	private int time1;
	private int time2;

	public Abc(int w, int xf, int wait) {
		this.w = w;
		this.xf = xf;
		this.wait = wait;
		this.x = xf - 32;
		this.y = 10;
		time1 = 0;
		time2 = 0;
	}

	public void move1() {
		if (++time1 > wait) {
			x = Math.min(x + v, xf);
		}
	}
	public void move2() {
		if (++time2 > wait) {
			x += v;
		}
	}
	public boolean end1() {
		return (x == xf);
	}
	public boolean end2() {
		return (x > 32);
	}
	/**
	 * @return Returns the w.
	 */
	public int getW() {
		return w;
	}
	/**
	 * @return Returns the x.
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return Returns the xf.
	 */
	public int getXf() {
		return xf;
	}
	/**
	 * @return Returns the y.
	 */
	public int getY() {
		return y;
	}
}
