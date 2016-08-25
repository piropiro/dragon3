/*
 * 作成日: 2004/03/21
 */
package dragon3.attack.target;

import mine.paint.UnitMap;

/**
 * @author k-saito
 */
public class SpreadTarget extends Target {

	private int range;

	/**
	 * 
	 */
	public SpreadTarget(int range) {
		super();
		this.range = range;
	}

	public void setSearchField(UnitMap map, int page, int x, int y){
		map.fillDia(page, x, y, range, 1);
	}

	/* (非 Javadoc)
	 * @see dragon3.attack.target.Target#paint(mine.paint.UnitMap, int, int)
	 */
	protected void paintTarget(UnitMap map, int page, int x, int y) {
		map.fillDia(page, x, y, range, 3);
	}

}
