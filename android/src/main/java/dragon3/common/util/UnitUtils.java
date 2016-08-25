package dragon3.common.util;

import mine.util.Point;

import mine.paint.UnitMap;

public class UnitUtils {

	private UnitMap map;

	private int face; // used in moveS
	private static final int F_UP = 0;
	private static final int F_DOWN = 1;
	private static final int F_LEFT = 2;
	private static final int F_RIGHT = 3;

	/*** Constructer *************************************************/

	public UnitUtils(UnitMap map) {
		this.map = map;
		face = 0;
	}

	/*** Shortest Root ************************************************/

	public Point moveS(int p, int x, int y) {

		Point next = new Point(x, y);
		int bd = map.getData(p, x, y);

		switch (face) {
			case F_UP :
				bd = moves(p, x, y - 1, bd, next, F_UP);
				break;
			case F_DOWN :
				bd = moves(p, x, y + 1, bd, next, F_DOWN);
				break;
			case F_LEFT :
				bd = moves(p, x - 1, y, bd, next, F_LEFT);
				break;
			case F_RIGHT :
				bd = moves(p, x + 1, y, bd, next, F_RIGHT);
				break;
			default :
				}
		bd = moves(p, x, y - 1, bd, next, F_UP);
		bd = moves(p, x, y + 1, bd, next, F_DOWN);
		bd = moves(p, x - 1, y, bd, next, F_LEFT);
		bd = moves(p, x + 1, y, bd, next, F_RIGHT);
		if (next.x != x)
			return next;
		if (next.y != y)
			return next;
		return null;
	}

	private int moves(int p, int x, int y, int bd, Point s, int f) {
		int bds = map.getData(p, x, y);
		if (bd > bds) {
			s.x = x;
			s.y = y;
			face = f;
			return bds;
		}
		return bd;
	}
}
