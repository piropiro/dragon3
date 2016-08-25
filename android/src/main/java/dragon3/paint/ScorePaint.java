package dragon3.paint;

import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.controller.UnitWorks;
import dragon3.map.MapWorks;
import dragon3.panel.PanelManager;
import mine.paint.UnitMap;

public class ScorePaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private PanelManager pm;
	
	/**
	 * @param uw
	 */
	public ScorePaint(UnitWorks uw) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.pm = uw.getPanelManager();
		
		map.clear(Page.P40, 0);
		mw.repaint();
		setHelp();
	}

	/**
	 * 
	 */
	private void setHelp() {
		pm.displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_SCORE]);
	}

	@Override
	public void setSelectBody(Body b) {
	}

	@Override
	public void mouseMoved(int x, int y) {
	}
	@Override
	public void leftPressed() {
		uw.backToCamp();
	}

	/*** Place *****************************************/

	@Override
	public void setSelectPlace(int x, int y) {
		uw.getPanelManager().displayPlace(x, y);
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
		uw.backToCamp();
	}

	@Override
	public void cancel() {
		uw.backToCamp();
	};

}
