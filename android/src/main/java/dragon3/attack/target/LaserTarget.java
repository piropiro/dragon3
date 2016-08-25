/*
 * 作成日: 2004/03/21
 */
package dragon3.attack.target;

import mine.paint.UnitMap;

/**
 * @author k-saito
 */
public class LaserTarget extends Target {

	private int range;
	private int donut;

	/**
	 * 
	 */
	public LaserTarget(int range, int donut) {
		super();
		this.range = range;
		this.donut = donut;
	}

	/* (非 Javadoc)
	 * @see dragon3.attack.target.Target#paint(mine.paint.UnitMap, int, int)
	 */
	protected void paintTarget(UnitMap map, int page, int x, int y) {
		int face = getFace(x, y);
		for (int i = donut + 1; i <= range; i++) {
			switch (face) {
				case NORTH :
					map.setData(page, x, y - i, 3);
					break;
				case SOUTH :
					map.setData(page, x, y + i, 3);
					break;
				case WEST :
					map.setData(page, x - i, y, 3);
					break;
				case EAST :
					map.setData(page, x + i, y, 3);
					break;
			}
		}
	}

}
