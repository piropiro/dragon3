package dragon3.paint;

import java.util.List;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.Page;
import dragon3.controller.UnitWorks;
import dragon3.manage.TurnManager;
import dragon3.map.MapWorks;
import dragon3.panel.PanelManager;
import mine.paint.UnitMap;

public class KakuseiPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private AnimeManager anime;
	private PanelManager pm;
	private TurnManager tm;
	private Body sister;
	private Body kakusei;

	/**
	 * @param uw
	 * @param sister
	 */
	public KakuseiPaint(UnitWorks uw, Body sister) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.pm = uw.getPanelManager();
		this.anime = uw.getAnimeManager();
		this.tm = uw.getTurnManager();
		
		this.sister = sister;
		map.clear(Page.P10, 0);
		map.setData(Page.P10, sister.getX(), sister.getY(), 4);
		map.setData(Page.P30, sister.getX(), sister.getY(), 2);
		map.setData(Page.P40, sister.getX(), sister.getY(), 0);
	}

	/**
	 *
	 */
	public void action() {
		map.setData(Page.P10, sister.getX(), sister.getY(), 0);
		map.setData(Page.P30, kakusei.getX(), kakusei.getY(), 0);
		map.setData(Page.P50, kakusei.getX(), kakusei.getY(), 0);
		anime.systemAnime(AnimeManager.ID_KAKUSEI, kakusei.getX(), kakusei.getY());
		map.setData(Page.P20, kakusei.getX(), kakusei.getY(), kakusei.getImageNum());
		mw.repaint();
		PaintUtils.setBasicPaint(uw);
	}

	/**
	 * @return
	 */
	private Body getKakuseiData() {
		List<Body> data = uw.loadEnemyData("kakusei", 0);
		return (Body) data.get(0);
	}

	/**
	 *
	 */
	private void setStatus() {
		int t = sister.getDef() + sister.getMdf() + sister.getMis();
		kakusei.setStr(sister.getStr() * 2 + t);
		kakusei.setMst(sister.getMst() + t);
		kakusei.setHit(sister.getHit() + t);
		kakusei.setMax();
		kakusei.setX(sister.getX());
		kakusei.setY(sister.getY());
		kakusei.setGoalX(sister.getGoalX());
		kakusei.setGoalY(sister.getGoalY());
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
		kakusei = getKakuseiData();
		setStatus();
		uw.changeChara(sister, kakusei);
		action();
	}

	@Override
	public void cancel() {
		map.setData(Page.P10, sister.getX(), sister.getY(), 0);
		map.setData(Page.P30, sister.getX(), sister.getY(), 1);
		mw.repaint();
		PaintUtils.setBasicPaint(uw);
	};

}
