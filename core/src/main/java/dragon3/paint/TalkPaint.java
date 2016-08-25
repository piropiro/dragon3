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
import mine.paint.UnitMap;
import mine.util.Point;

public class TalkPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private StageMap map;
	private PanelManager pm;
	private RewalkManager rewalkManager;
	private TurnManager tm;
	
	private Body ba, bb;
	private Body[] target;

	/**
	 * @param uw
	 * @param cp
	 * @param ba
	 */
	public TalkPaint(UnitWorks uw, Body ba) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getStageMap();
		this.pm = uw.getPanelManager();
		this.rewalkManager = uw.getRewalkManager();
		this.tm = uw.getTurnManager();
		this.ba = ba;
		target = new Body[4];
		target[0] = getTarget(ba.getX() - 1, ba.getY());
		target[1] = getTarget(ba.getX() + 1, ba.getY());
		target[2] = getTarget(ba.getX(), ba.getY() - 1);
		target[3] = getTarget(ba.getX(), ba.getY() + 1);
	}

	/**
	 * 
	 */
	public void show() {
		UnitMap map = this.map.getMap();
		map.clear(Page.P10, 0);
		map.setData(Page.P30, ba.getX(), ba.getY(), 3);
		map.setData(Page.P10, ba.getX() - 1, ba.getY(), 2);
		map.setData(Page.P10, ba.getX() + 1, ba.getY(), 2);
		map.setData(Page.P10, ba.getX(), ba.getY() - 1, 2);
		map.setData(Page.P10, ba.getX(), ba.getY() + 1, 2);
		for (int i = 0; i < target.length; i++) {
			if (target[i] != null) {
				map.setData(Page.P10, target[i].getX(), target[i].getY(), 3);
			}
		}
		setHelp();
	}

	/**
	 * 
	 */
	private void setHelp() {
		pm.displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_TALK]);
	}

	/**
	 * @return
	 */
	public boolean isEnable() {
		for (int i = 0; i < target.length; i++) {
			if (target[i] != null)
				return true;
		}
		return false;
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	private Body getTarget(int x, int y) {
		Body bb = map.search(x, y);

		if (bb == null)
			return null;
		if (bb.getColor() == ba.getColor())
			return null;
		if (bb.hasAttr(BodyAttribute.SLEEP))
			return null;
		if (ba.getLevel() != 0) {
			if (bb.getLevel() > ba.getLevel())
				return null;
		}

		if (uw.have(bb))
			return null;

		if (bb.hasAttr(BodyAttribute.TALKABLE)) {
			return bb;
		}

		return null;
	}

	/**
	 * 
	 */
	public void action() {
		uw.displayCardBattle(ba, bb);
	}

	@Override
	public void leftPressed() {
		Point p = mw.getWaku();
		if (p.x == ba.getX() && p.y == ba.getY()) {
			PaintUtils.setEndPaint(uw, ba);
			mw.repaint();
		}
	}

	@Override
	public boolean isNextPoint(int x, int y) {
		return (map.getMap().getData(Page.P10, x, y) == 3);
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
		Point p = mw.getWaku();
		if (map.getMap().getData(Page.P10, p.x, p.y) != 3)
			return;
		bb = getTarget(p.x, p.y);
		if (bb != null) {
			map.getMap().clear(Page.P10, 0);
			mw.repaint();
			pm.closeData();
			action();
		}
	}

	@Override
	public void cancel() {
		rewalkManager.rewalk(ba);
	};

}
