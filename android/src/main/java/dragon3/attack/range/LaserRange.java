/*
 * 作成日: 2004/03/21
 */
package dragon3.attack.range;

import mine.paint.UnitMap;

/**
 * @author k-saito
 */
public class LaserRange implements Range {

	private int range;
	private int donut;

	/**
	 * 
	 */
	public LaserRange(int range, int donut) {
		this.range = range;
		this.donut = donut;
	}

	/* (非 Javadoc)
	 * @see dragon3.attack.range.Range#paint(mine.paint.UnitMap, int, int)
	 */
	public void paint(UnitMap map, int page, int x, int y) {
		map.clear(page, 0);
		for (int i = donut + 1; i <= range; i++) {
			map.setData(page, x - i, y, 2);
			map.setData(page, x + i, y, 2);
			map.setData(page, x, y - i, 2);
			map.setData(page, x, y + i, 2);
		}
	}

}
