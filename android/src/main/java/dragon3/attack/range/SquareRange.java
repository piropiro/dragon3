/*
 * 作成日: 2004/03/21
 */
package dragon3.attack.range;

import mine.paint.UnitMap;

/**
 * @author k-saito
 */
public class SquareRange implements Range {

	private int range;

	/**
	 * 
	 */
	public SquareRange(int range) {
		this.range = range;
	}

	/* (非 Javadoc)
	 * @see dragon3.attack.range.Range#paint(mine.paint.UnitMap, int, int)
	 */
	public void paint(UnitMap map, int page, int x, int y) {
		map.clear(page, 0);
		map.fillRect(page, x - range, y - range, range * 2 + 1, range * 2 + 1, 2);
		map.setData(page, x, y, 0);
	}

}
