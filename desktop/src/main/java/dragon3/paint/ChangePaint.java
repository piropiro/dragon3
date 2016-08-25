package dragon3.paint;

import dragon3.anime.AnimeManager;
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
public class ChangePaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private AnimeManager anime;
	private PanelManager pm;
	private TurnManager tm;
	
	private Body ba;
	private Body bb;

	/**
	 * @param uw
	 * @param ba
	 * @param bb
	 */
	public ChangePaint(UnitWorks uw, Body ba, Body bb) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.pm = uw.getPanelManager();
		this.anime = uw.getAnimeManager();
		this.tm = uw.getTurnManager();
		
		this.ba = ba;
		this.bb = bb;
		map.clear(Page.P10, 0);
		map.setData(Page.P10, ba.getX(), ba.getY(), 4);
		map.setData(Page.P30, ba.getX(), ba.getY(), 5);
		map.setData(Page.P40, ba.getX(), ba.getY(), 0);
	}

	/**
	 * 
	 */
	public void action() {
		anime.eraseAnime(ba.getX(), ba.getY());
		anime.summonAnime(bb.getImageNum(), bb.getX(), bb.getY());
		map.setData(Page.P10, bb.getX(), bb.getY(), 0);
		map.setData(Page.P20, bb.getX(), bb.getY(), bb.getImageNum());
		mw.repaint();
		PaintUtils.setBasicPaint(uw);
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
		bb.setX(ba.getX());
		bb.setY(ba.getY());
		uw.changeChara(ba, bb);
		action();
	}

	@Override
	public void cancel() {
		map.setData(Page.P10, ba.getX(), ba.getY(), 0);
		map.setData(Page.P30, ba.getX(), ba.getY(), 1);
		mw.repaint();
		PaintUtils.setBasicPaint(uw);
	};

}
