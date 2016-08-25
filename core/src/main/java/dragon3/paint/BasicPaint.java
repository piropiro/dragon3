package dragon3.paint;

import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.controller.UnitWorks;
import dragon3.manage.TurnManager;
import dragon3.map.MapWorks;
import dragon3.map.StageMap;
import dragon3.panel.PanelManager;
import mine.util.Point;

public class BasicPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private StageMap map;
	private PanelManager pm;
	private TurnManager tm;
	
	/**
	 * @param uw
	 */
	public BasicPaint(UnitWorks uw) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getStageMap();
		this.pm = uw.getPanelManager();
		this.tm = uw.getTurnManager();
		setHelp();
	}

	/**
	 * 
	 */
	private void setHelp() {
		pm.displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_BASIC]);
	}

	/**
	 * @param ba
	 */
	private void gotoWalk(Body ba) {

		if (map.getMap().getData(Page.P30, ba.getX(), ba.getY()) == 0) {
			PaintUtils.setWalkPaint(uw, ba);
			mw.repaint();
			pm.displayWazaList(ba);
		} else if (uw.getSaveManager().getSaveData().getAllClear() && ba.hasAttr(BodyAttribute.SISTER)) {
			PaintUtils.setKakuseiPaint(uw, ba);
			mw.repaint();
		} else if (!ba.hasAttr(BodyAttribute.BERSERK)) {
			PaintUtils.setBerserkPaint(uw, ba);
			mw.repaint();
		} else {
			Body bb = uw.getChangeChara(ba);
			if (bb != null) {
				PaintUtils.setChangePaint(uw, ba, bb);
				mw.repaint();
				bb.setX(ba.getX());
				bb.setY(ba.getY());
				pm.displayStatus(bb);
			}
		}
	}

	@Override
	public void leftPressed() {
		Point p = mw.getWaku();
		Body b = map.search(p.x, p.y);
		if (b != null) {
			gotoWalk(b);
		} else {
			pm.displayData(tm, p.x, p.y);
		}
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

	}

	@Override
	public void cancel() {
		Point p = mw.getWaku();
		Body b = map.search(p.x, p.y);
		if (b != null) {
			pm.displayAnalyze(b);
		} else {
			PaintUtils.setButtonPaint(uw, p.x, p.y, this, 5);
		}
	};

}
