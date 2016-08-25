/*
 * 作成日: 2004/03/21
 */
package dragon3.attack.range;

import mine.paint.UnitMap;

/**
 * @author k-saito
 */
public class BreathRange implements Range {

	private int range;
	private int donut;

	/**
	 * 
	 */
	public BreathRange(int range, int donut) {
		this.range = range;
		this.donut = donut;
	}

	/* (非 Javadoc)
	 * @see dragon3.attack.range.Range#paint(mine.paint.UnitMap, int, int)
	 */
	public void paint(UnitMap map, int page, int x, int y) {
		map.clear(page, 0);
		for (int i = donut + 1; i <= range; i++) {
			for (int j = -i + 1; j <= i - 1; j++) {
				map.setData(page, x - i, y + j, 2);
				map.setData(page, x + i, y + j, 2);
				map.setData(page, x + j, y - i, 2);
				map.setData(page, x + j, y + i, 2);
			}
		}
	}

}
