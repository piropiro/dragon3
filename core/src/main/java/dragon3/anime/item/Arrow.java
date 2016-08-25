package dragon3.anime.item;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.util.Point;

public class Arrow {

	private Point start;
	private Point goal;
	private int max;
	private int x;
	private int y;
	private int n;
	private MineImage[] image;

	private boolean mflag;
	private double theta;

	/*** Constructer *************************************/

	public Arrow(Point start, Point goal, int max, MineImage[] image, boolean mflag) {
		this.start = start;
		this.goal = goal;
		this.max = max;
		this.image = image;
		this.mflag = mflag;
		theta = Math.atan2(goal.y - start.y, goal.x - start.x);
		n = 0;
	}

	/*** Move ***********************************************/

	public void move() {
		n = Math.min(n + 1, max);
		x = start.x + (goal.x - start.x) * n / max;
		y = start.y + (goal.y - start.y) * n / max;
	}

	/*** End Judge ********************************************/

	public boolean end() {
		return (n == max);
	}

	/*** Paint ************************************************/

	public void paint(MineGraphics g) {

		int dx = (mflag) ? x : 0;
		int dy = (mflag) ? y : 0;
		int i = (image.length - 1) * n / max;

		g.drawRotateImage(image[i], dx, dy, theta);
	}

	/**
	 * @return Returns the x.
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return Returns the y.
	 */
	public int getY() {
		return y;
	}
	/**
	 * @return Returns the theta.
	 */
	public double getTheta() {
		return theta;
	}
	/**
	 * @param theta The theta to set.
	 */
	public void setTheta(double theta) {
		this.theta = theta;
	}
	
	public void addTheta(double value) {
		this.theta += value;
	}
}
