package dragon3.paint;

import mine.util.Point;
import dragon3.attack.FightManager;
import dragon3.common.Body;
import dragon3.common.constant.Page;
import dragon3.controller.UnitWorks;
import dragon3.manage.RewalkManager;
import dragon3.map.MapWorks;
import mine.paint.UnitMap;

public class AttackPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private FightManager fm;
	private RewalkManager rewalkManager;
	private Body ba;
	
	/**
	 * @param uw
	 */
	public AttackPaint(UnitWorks uw, FightManager fm, Body ba) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.rewalkManager = uw.getRewalkManager();
		this.fm = fm;
		this.ba = ba;
	}

	@Override
	public void setSelectPlace(int x, int y) {
		uw.getPanelManager().displayPlace(x, y);
	}
	
	/**
	 * Left Pressed
	 */
	@Override
	public void leftPressed() {
		Point waku = mw.getWaku();
		if (waku.x == ba.getX() && waku.y == ba.getY()) {
			fm.nextSelect();
			return;
		}
	}

	/**
	 * Select Body
	 */
	@Override
	public void setSelectBody(Body b) {
	}

	/**
	 * Next Select
	 */
	@Override
	public boolean isNextPoint(int x, int y) {
		if (map.getData(Page.P10, x, y) == 3) {
			Body b = uw.search(x, y);
			return (b != null);
		}
		return false;
	}


	/**
	 * Mouse Moved
	 */
	@Override
	public void mouseMoved(int x, int y) {
		fm.setTarget(new Point(x, y));
	}

	@Override
	public void accept() {
		Point waku = mw.getWaku();
		if (map.getData(Page.P10, waku.x, waku.y) != 0) {
			if (fm.searchTargets()) {
				PaintUtils.setWaitPaint(uw);
				fm.attack();
				uw.setEnd(ba, false);
			}
		}
	}

	@Override
	public void cancel() {
		rewalkManager.rewalk(ba);
	};
}
