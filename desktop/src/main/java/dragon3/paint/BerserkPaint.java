package dragon3.paint;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.Page;
import dragon3.controller.UnitWorks;
import dragon3.manage.TurnManager;
import dragon3.map.MapWorks;
import dragon3.panel.PanelManager;
import mine.paint.UnitMap;
import dragon3.common.constant.BodyAttribute;

/**
 * @author k-saito
 */
public class BerserkPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private AnimeManager anime;
	private PanelManager pm;
	private TurnManager tm;
	
	private Body ba;


	/**
	 * @param uw
	 * @param ba
	 */
	public BerserkPaint(UnitWorks uw, Body ba) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.pm = uw.getPanelManager();
		this.anime = uw.getAnimeManager();
		this.tm = uw.getTurnManager();
		
		this.ba = ba;
		map.clear(Page.P10, 0);
		map.setData(Page.P10, ba.getX(), ba.getY(), 4);
		map.setData(Page.P30, ba.getX(), ba.getY(), 6);
		map.setData(Page.P40, ba.getX(), ba.getY(), 0);
	}

	public void action() {
		map.setData(Page.P30, ba.getX(), ba.getY(), 0);
		anime.systemAnime(AnimeManager.ID_REFRESH, ba.getX(), ba.getY());
		anime.statusAnime(AnimeManager.STATUS_BERSERK, ba.getX(), ba.getY());
		map.setData(Page.P10, ba.getX(), ba.getY(), 0);
		PaintUtils.setBasicPaint(uw);
	}

	private void setStatus() {
		ba.addAttr(BodyAttribute.BERSERK);
		ba.setHp(ba.getHpMax());
		ba.setStr((int) (ba.getStr() * 1.5));
		ba.setDef((int) (ba.getDef() * 1.5));
		ba.setMst((int) (ba.getMst() * 1.5));
		ba.setMdf((int) (ba.getMdf() * 1.5));
		ba.setHit((int) (ba.getHit() * 1.5));
		ba.setMis((int) (ba.getMis() * 1.5));
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
		setStatus();
		uw.bersekChara(ba);
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
