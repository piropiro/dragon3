package dragon3.paint;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.controller.UnitWorks;
import dragon3.manage.TurnManager;
import dragon3.panel.PanelManager;

public class TitlePaint implements EventListener {

	private UnitWorks uw;
	private AnimeManager anime;
	private PanelManager pm;
	private TurnManager tm;
	/**
	 * @param uw
	 */
	public TitlePaint(UnitWorks uw) {
		this.uw = uw;
		this.anime = uw.getAnimeManager();
		this.pm = uw.getPanelManager();
		this.tm = uw.getTurnManager();
	}

	/**
	 * 
	 */
	public void action() {
		anime.closeTitleOut();
		uw.startup();
		anime.closeTitleIn();
	}

	@Override
	public void leftPressed() {
		accept();
	}

	@Override
	public void setSelectBody(Body b) {
	}

	@Override
	public void mouseMoved(int x, int y) {
	}
	
	/*** Place *****************************************/

	@Override
	public void setSelectPlace(int x, int y) {
		pm.displayPlace(tm, x, y);
	}

	/*** Select Body *****************************************/

	@Override
	public boolean isNextPoint(int x, int y) {
		return false;
	}

	/*** Mouse Moved ***********************************/

	/*** Event ************************************/

	@Override
	public void accept() {
		action();
	}

	@Override
	public void cancel() {
	};

}
