package dragon3.edit.deploy.paint;

import java.util.ArrayList;
import java.util.List;

import dragon3.data.DeployData;
import dragon3.edit.deploy.MainWorks;
import dragon3.edit.deploy.Page;
import mine.paint.UnitMap;

public class SortPaint implements PaintListener {

	private MainWorks<DeployData> mw;
	private UnitMap map;
	private List<DeployData> dstData; // 写し先データ

	/**
	 * コンストラクタ
	 *
	 * @param mw
	 * @param map
	 */
	public SortPaint(MainWorks<DeployData> mw, UnitMap map) {
		this.mw = mw;
		this.map = map;
		dstData = new ArrayList<>();
	}

	@Override
	public void leftPressed(int x, int y) {
		if (map.getData(Page.CHARA, x, y) == -1) {
			mw.moveNextUnit(dstData);
		} else {
			mw.moveUnit(x, y, dstData);
		}
	}

	@Override
	public void rightPressed(int x, int y) {
		if (dstData.size() != -1) {
			mw.undoUnit(dstData);
		}
	}
}
