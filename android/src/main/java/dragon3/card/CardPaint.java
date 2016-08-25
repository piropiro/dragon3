package dragon3.card;

import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.controller.UnitWorks;
import dragon3.map.MapWorks;
import dragon3.paint.EventListener;
import dragon3.panel.PanelManager;

public class CardPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private PanelManager pm;
	
	@SuppressWarnings("unused")
	private Body ba;
	@SuppressWarnings("unused")
	private Body bb;

	@SuppressWarnings("unused")
	private int select;


	/**
	 * @param uw
	 * @param ba
	 * @param bb
	 */
	public CardPaint(UnitWorks uw, Body ba, Body bb) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.pm = uw.getPanelManager();
		
		this.ba = ba;
		this.bb = bb;
		uw.displayCardBattle(ba, bb);
		setHelp();
	}

	/**
	 * 
	 */
	private void setHelp() {
		pm.displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_CARD]);
	}


	/**
	 * 
	 */
	public void action() {
		if (!uw.isCardBattleEnd()) {
			mw.setEventListener(this);
		}
	}

	public void leftPressed() {
	}
	public void rightPressed() {
	}
	public void mouseMoved(int x, int y) {
	}
	public void setSelectBody(Body b) {
	}
	public void setSelectPlace(int x, int y) {
	}

	@Override
	public boolean isNextPoint(int x, int y) {
		return false;
	}

	@Override
	public void accept() {
	}

	@Override
	public void cancel() {
	}

}
