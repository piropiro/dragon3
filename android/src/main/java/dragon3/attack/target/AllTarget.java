/*
 * 作成日: 2004/03/21
 */
package dragon3.attack.target;

import mine.paint.UnitMap;
import dragon3.common.constant.Page;

/**
 * @author k-saito
 */
public class AllTarget extends Target {

	/**
	 * 
	 */
	public AllTarget() {
		super();
	}

	/* (非 Javadoc)
	 * @see dragon3.attack.target.Target#paint(mine.paint.UnitMap, int, int)
	 */
	protected void paintTarget(UnitMap map, int page, int x, int y) {
		map.change(Page.P10, 0, page, 0, 3);
	}

}
