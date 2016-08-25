package dragon3.paint;

import java.util.List;

import dragon3.anime.AnimeManager;
import dragon3.attack.FightManager;
import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.common.util.MoveUtils;
import dragon3.controller.UnitWorks;
import dragon3.manage.RewalkManager;
import dragon3.manage.TurnManager;
import dragon3.map.MapWorks;
import dragon3.panel.PanelManager;
import mine.paint.UnitMap;
import mine.util.Point;

public class WalkPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private AnimeManager anime;
	private PanelManager pm;
	private RewalkManager rewalkManager;
	private FightManager fm;
	private TurnManager tm;
	
	private Body ba;
	private int step;

	/**
	 * @param uw
	 * @param ba
	 */
	public WalkPaint(UnitWorks uw, Body ba) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.anime = uw.getAnimeManager();
		this.pm = uw.getPanelManager();
		this.rewalkManager = uw.getRewalkManager();
		this.fm = uw.getFightManager();
		this.tm = uw.getTurnManager();
		
		this.ba = ba;
		List<Body> charaList = uw.getCharaList();

		step = ba.getMoveStep();
		int[] stepList = MoveUtils.INSTANCE.getStepList(ba);

		for (int i = 0; i < stepList.length; i++) {
			if (stepList[i] > step) {
				stepList[i] = 255;
			}
		}
		map.clear(Page.P02, 0);
		map.change(Page.P01, Page.P02, stepList);
		map.change(Page.P02, 0, Page.P02, 1);
		map.copyPage(Page.P02, Page.P12);
		for (Body b : charaList) {
			if (!b.isAlive())
				continue;
			map.setData(Page.P02, b.getX(), b.getY(), 255);
		}
		map.setData(Page.P02, ba.getX(), ba.getY(), 1);
		map.paintStep(Page.P02, Page.P03, ba.getX(), ba.getY(), step);
		map.change(Page.P03, UnitMap.FALSE, Page.P10, 0, 1);
		for (Body b : charaList) {
			if (!b.isAlive())
				continue;
			map.setData(Page.P10, b.getX(), b.getY(), 0);
		}
		map.setData(Page.P10, ba.getX(), ba.getY(), 1);
		setHelp();
	}

	/**
	 *
	 */
	private void setHelp() {
		if (!GameColor.Companion.isPlayer(ba.getColor()))
			return;
		uw.getPanelManager().displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_WALK]);
	}

	/**
	 *
	 */
	public void action() {
		rewalkManager.set(ba);
		Point waku = mw.getWaku();
		walk(waku.x, waku.y);
		map.clear(Page.P10, 0);
		fm.setup(ba);
		fm.nextSelect();
	}

	/**
	 * @param x
	 * @param y
	 */
	private void walk(int x, int y) {
		map.paintStep(Page.P02, Page.P03, x, y, step);
		anime.walkAnime(ba.getX(), ba.getY());
		ba.setX(x);
		ba.setY(y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public void enemy(int x, int y) {
		rewalkManager.set(ba);
		walk(x, y);
		map.clear(Page.P10, 0);
	}

	@Override
	public void leftPressed() {
		Point waku = mw.getWaku();
		if (map.getData(Page.P10, waku.x, waku.y) == 0) {
			cancel();
			return;
		}

		if (ba.hasAttr(BodyAttribute.SLEEP))
			return;
		if (GameColor.Companion.isPlayer(ba.getColor())) {
			if (ba.hasAttr(BodyAttribute.CHARM))
				return;
		} else {
			if (!ba.hasAttr(BodyAttribute.CHARM))
				return;
		}
		action();
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
		map.clear(Page.P10, 0);
		PaintUtils.setBasicPaint(uw);
		mw.repaint();
	};

}
