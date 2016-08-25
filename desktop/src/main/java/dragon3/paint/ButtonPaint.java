package dragon3.paint;

import mine.util.Point;
import dragon3.common.Body;
import dragon3.common.constant.Page;
import dragon3.controller.UnitWorks;
import dragon3.manage.TurnManager;
import dragon3.map.MapWorks;
import dragon3.panel.PanelManager;
import mine.paint.UnitMap;

/**
 * @author k-saito
 */
public class ButtonPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private PanelManager pm;
	private TurnManager tm;
	
	private int x;
	private int y;
	private EventListener el;
	private int type;

	/**
	 * @param uw
	 * @param x
	 * @param y
	 * @param pl
	 * @param type
	 */
	public ButtonPaint(UnitWorks uw, int x, int y, EventListener pl, int type) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.pm = uw.getPanelManager();
		this.tm = uw.getTurnManager();

		this.x = x;
		this.y = y;
		this.el = pl;
		this.type = type;

		if (y == 0) {
			map.setData(Page.P40, x, y + 1, type);
		} else {
			map.setData(Page.P40, x, y - 1, type);
		}
		map.setData(Page.P10, x, y, type);
		map.setData(Page.P40, x, y, 0);
		mw.repaint();
	}

	@Override
	public void mouseMoved(int x, int y) {
		cancel();
	}

	@Override
	public void leftPressed() {

	}

	/*** Place *****************************************/

	@Override
	public void setSelectPlace(int x, int y) {
		pm.displayPlace(tm, x, y);
	}

	/*** Select Body *****************************************/

	@Override
	public void setSelectBody(Body b) {
		pm.displayStatus(b);
	}

	@Override
	public boolean isNextPoint(int x, int y) {
		return false;
	}

	/*** Mouse Moved ***********************************/

	/*** Event ************************************/

	@Override
	public void accept() {
		Point ps = mw.getWaku();
		if (ps.x == x && ps.y == y) {
			switch (type) {
				case 5 :
					uw.enemyTurnStart();
					break;
				case 6 :
					uw.finishPutPlayers();
					break;
				case 7 :
					uw.backToCamp();
					break;
			}
		} else {
			cancel();
		}
	}

	@Override
	public void cancel() {
		map.setData(Page.P40, x, y - 1, 0);
		map.setData(Page.P40, x, y + 1, 0);
		map.setData(Page.P10, x, y, 0);

		uw.setEventListener(el);
		mw.repaint();
	};

	
}
