package dragon3.edit.deploy.paint;

import dragon3.data.DeployData;
import dragon3.edit.deploy.MainWorks;
import dragon3.edit.deploy.Page;
import mine.paint.UnitMap;

public class BasicPaint implements PaintListener {

	private MainWorks<DeployData> mw;
	private UnitMap map;

	/**
	 * コンストラクタ<p>
	 *
	 * @param mw
	 * @param map
	 */
	public BasicPaint(MainWorks<DeployData> mw, UnitMap map) {
		this.mw = mw;
		this.map = map;
	}


	/* (非 Javadoc)
	 * @see dragon3.edit.enemy.PaintListener#leftPressed(int, int)
	 */
	public void leftPressed(int x, int y) {
		if (map.getData(Page.CHARA, x, y) == -1) {
			mw.addUnit(x, y);
		} else {
			mw.removeUnit(x, y);
		}
	}


	/* (非 Javadoc)
	 * @see dragon3.edit.enemy.PaintListener#rightPressed(int, int)
	 */
	public void rightPressed(int x, int y) {
		if (map.getData(Page.CHARA, x, y) != -1)
			mw.copyUnit(x, y);
	}
}