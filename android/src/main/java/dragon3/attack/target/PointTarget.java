/*
 * 作成日: 2004/03/21
 */
package dragon3.attack.target;

import mine.paint.UnitMap;

/**
 * @author k-saito
 */
public class PointTarget extends Target {

	/**
	 * 
	 */
	public PointTarget() {
		super();
	}

	/* (非 Javadoc)
	 * @see dragon3.attack.target.Target#paint(mine.paint.UnitMap, int, int)
	 */
	protected void paintTarget(UnitMap map, int page, int x, int y) {
		map.setData(page, x, y, 3);
	}

}
