package imo.body;

public class Body {

	protected int xs, ys;
	protected double x, y, angle, v;

	public Body(double x, double y, int xs, int ys, double angle, double v) {
		this.x = x;
		this.y = y;
		this.xs = xs;
		this.ys = ys;
		this.angle = angle;
		this.v = v;
	}

	public void move() {
		x += v * Math.cos(angle);
		y += v * Math.sin(angle);
	}

	public void moveK(boolean u, boolean d, boolean l, boolean r) {
		if (u)
			y -= v;
		if (d)
			y += v;
		if (l)
			x -= v;
		if (r)
			x += v;
	}

	public void moveM(int mx, int my) {
		if ((x - mx) * (x - mx) + (y - my) * (y - my) < v * v) {
			x = mx;
			y = my;
		} else {
			double anga = getAngle(this, mx, my);
			x += v * Math.cos(anga);
			y += v * Math.sin(anga);
		}
	}

	public void moveC(Body a) {
		if (x + xs >= a.x + a.xs)
			x = a.x + a.xs - xs - 1;
		if (y + ys >= a.y + a.ys)
			y = a.y + a.ys - ys - 1;
		if (x < a.x)
			x = a.x;
		if (y < a.y)
			y = a.y;
	}

	public static boolean hit(Body a, Body b) {
		if (a.xs <= 0)
			return false;
		if (a.ys <= 0)
			return false;
		if (b.xs <= 0)
			return false;
		if (b.ys <= 0)
			return false;
		if (a.x + a.xs < b.x)
			return false;
		if (a.x >= b.x + b.xs)
			return false;
		if (a.y + a.ys < b.y)
			return false;
		if (a.y >= b.y + b.ys)
			return false;
		return true;
	}

	static boolean hit(Body a, double x, double y) {
		if (x < a.x)
			return false;
		if (x >= a.x + a.xs)
			return false;
		if (y < a.y)
			return false;
		if (y >= a.y + a.ys)
			return false;
		return true;
	}

	static double getAngle(Body a, Body b) {
		return Math.atan2(
			(b.y + b.ys / 2) - (a.y + a.ys / 2),
			(b.x + b.xs / 2) - (a.x + a.xs / 2));
	}

	static double getAngle(Body a, double x, double y) {
		return Math.atan2(y - (a.y + a.ys / 2), x - (a.x + a.xs / 2));
	}

	public int rnd(int max) {
		return (int)(Math.random() * max);
	}
}
