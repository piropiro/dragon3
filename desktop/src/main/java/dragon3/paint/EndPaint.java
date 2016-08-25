package dragon3.paint;

import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.controller.UnitWorks;
import dragon3.manage.RewalkManager;
import dragon3.manage.TurnManager;
import dragon3.map.MapWorks;
import dragon3.map.StageMap;
import dragon3.panel.PanelManager;
import mine.util.Point;

public class EndPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private StageMap map;
	private PanelManager pm;
	private RewalkManager rewalkManager;
	private TurnManager tm;
	
	private Body ba;


	/**
	 * @param uw
	 * @param ba
	 */
	public EndPaint(UnitWorks uw, Body ba) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getStageMap();
		this.pm = uw.getPanelManager();
		this.rewalkManager = uw.getRewalkManager();
		this.tm = uw.getTurnManager();
		this.ba = ba;
		map.getMap().clear(Page.P10, 0);
		map.getMap().setData(Page.P10, ba.getX(), ba.getY(), 3);
		map.getMap().setData(Page.P30, ba.getX(), ba.getY(), 1);
		setHelp();
	}

	/**
	 * 
	 */
	private void setHelp() {
		if (!GameColor.Companion.isPlayer(ba.getColor()))
			return;
		pm.displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_END]);
	}

	/**
	 * 
	 */
	public void action() {
		Point p = mw.getWaku();
		if (p.x == ba.getX() && p.y == ba.getY()) {
			uw.setEnd(ba, false);
		} else {
			uw.setEnd(ba, true);
		}
	}


	@Override
	public void leftPressed() {
	}



	@Override
	public boolean isNextPoint(int x, int y) {
		Body b = map.search(x, y);
		if (b == null)
			return false;
		if (b.hasAttr(BodyAttribute.SLEEP))
			return false;
		if (map.getMap().getData(Page.P30, x, y) != 0)
			return false;
		if (GameColor.Companion.isPlayer(b.getColor())) {
			if (b.hasAttr(BodyAttribute.CHARM))
				return false;
		} else {
			if (!b.hasAttr(BodyAttribute.CHARM))
				return false;
		}
		return true;
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

	/*** Mouse Moved ***********************************/

	@Override
	public void mouseMoved(int x, int y) {
		pm.setHelpLocation(x, y);
		mw.wakuPaint(x, y, true);
	}

	/*** Event ************************************/

	@Override
	public void accept() {
		map.getMap().setData(Page.P10, ba.getX(), ba.getY(), 0);
		action();
	}

	@Override
	public void cancel() {
		rewalkManager.rewalk(ba);
	};

}
