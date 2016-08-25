/*
 * 作成日: 2004/03/21
 */
package dragon3.attack.target;

import mine.paint.UnitMap;

/**
 * @author k-saito
 */
public abstract class Target {
	
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int WEST = 2;
	public static final int EAST = 3;
	
	private int bodyX;
	private int bodyY;
	private int targetX;
	private int targetY;

	public Target() {
	}
	
	public void setBasePoint(int bodyX, int bodyY) {
		this.bodyX = bodyX;
		this.bodyY = bodyY;
	}

	public int getFace(int x, int y) {
		int xs = x - bodyX;
		int ys = y - bodyY;
		if (Math.abs(xs) < Math.abs(ys)) {
			if (ys < 0) {
				return NORTH;
			} else {
				return SOUTH;
			}
		} else {
			if (xs < 0) {
				return WEST;
			} else {
				return EAST;
			}
		}
	}

	public int getX() {
		return targetX;
	}
	public int getY() {
		return targetY;
	}

	public void setSearchField(UnitMap map, int page, int x, int y) {
		map.setData(page, x, y, 1);
	}
	
	public void paint(UnitMap map, int page, int targetX_, int targetY_) {
		this.targetX = targetX_;
		this.targetY = targetY_;
		paintTarget(map, page, targetX, targetY);
	}
	
	protected abstract void paintTarget(UnitMap map, int page, int x, int y);
}
