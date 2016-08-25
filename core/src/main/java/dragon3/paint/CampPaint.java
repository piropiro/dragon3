package dragon3.paint;

import dragon3.camp.Camp;
import dragon3.common.Body;
import dragon3.common.constant.BodyKind;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.controller.UnitWorks;
import dragon3.manage.TurnManager;
import dragon3.map.MapWorks;
import dragon3.panel.PanelManager;
import mine.paint.UnitMap;
import mine.util.Point;

public class CampPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private PanelManager pm;
	private TurnManager tm;
	
	private Camp camp;

	//Body ba; // Selected Body

	/*** Constructer **********************************/

	public CampPaint(UnitWorks uw, Camp camp) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.pm = uw.getPanelManager();
		this.tm = uw.getTurnManager();
		
		this.camp = camp;

		setHelp();
	}

	public void setHelp() {
		String[] line;

		Body ba = camp.getBa();
		
		if (ba == null) {
			Point p = mw.getWaku();
			if (map.getData(Page.P30, p.x, p.y) != 0) {
				if (p.x < 14) {
					line = Texts.help[Texts.H_CAMP1];
				} else {
					line = Texts.help[Texts.H_CAMP2];
				}
			} else {
				line = Texts.help[Texts.H_CAMP3];
			}
		} else {
			switch (ba.base.getKind()) {
			case SOUL:
			case WEPON:
			case ARMOR:
			case ITEM:
				line = Texts.help[Texts.H_CAMP4];
				break;
			default:
				line = Texts.help[Texts.H_CAMP6];
			}
		}
		pm.displayHelp(uw.getMapWorks().getWaku(), GameColor.BLUE, line);
	}


	/*** Left Pressed ***********************************/

	@Override
	public void leftPressed() {
		Point p = mw.getWaku();
		Body ba = camp.getBa();
		Body b = camp.getEquip().search(p.x, p.y);
		if (b != null)
			pm.displayWazaList(b);

		switch (map.getData(Page.P10, p.x, p.y)) {
			case Camp.T_NONE :
				if (camp.isSortf())
					return;
				if (ba != null) {
					camp.changeChara(p.x, p.y);
				} else {
					if (b != null) {
						if (b.base.getKind() == BodyKind.WAZA) {
							camp.removeChara1(p.x, p.y);
						} else {
							camp.pickChara(p.x, p.y);
						}
					}
				}
				break;
			case Camp.T_FREE :
			case Camp.T_STORE :
				if (ba != null)
					camp.putChara2(p.x, p.y);
				else
					camp.pickChara(p.x, p.y);
				break;
			case Camp.T_PASTE :
				if (ba != null)
					camp.changeSortChara(p.x, p.y);
				else
					camp.removeChara1(p.x, p.y);
				break;
			case Camp.T_ERASE :
				if (camp.isSortf())
					return;
				if (map.getData(Page.P30, p.x, p.y) == 0) {
					if (ba != null)
						camp.putChara(p.x, p.y, ba);
					else
						camp.pickChara(p.x, p.y);
				} else {
					if (ba == null)
						camp.removeChara2(p.x, p.y);
				}
				break;
		}
		setHelp();
	}

	/*** Right Pressed ******************************/


	/*** Mouse Moved ***********************************/

	@Override
	public void mouseMoved(int x, int y) {
		pm.setHelpLocation(x, y);
		Body b = camp.getEquip().search(x, y);
		if (b != null) {
			if (b.getColor() == GameColor.BLUE) {
				camp.getEquip().equip(b);
			}
			pm.displayStatus(b);
		}
		camp.moveChara(x, y);
		mw.wakuPaint(x, y, true);
	}

	/*** Next Point *************************************/

	@Override
	public boolean isNextPoint(int x, int y) {
		if (map.getData(Page.P10, x, y) != 0)
			return false;

		Body b = camp.getEquip().search(x, y);
		return (b != null);
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


	/*** Event ************************************/

	@Override
	public void accept() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() {
		Point p = mw.getWaku();
		Body ba = camp.getBa();
		if (camp.isSortf()) {
			camp.moveChara(ba.getGoalX(), ba.getGoalY());
			camp.putChara2(ba.getGoalX(), ba.getGoalY());
			setHelp();
			return;
		}
		switch (map.getData(Page.P10, p.x, p.y)) {
			case Camp.T_NONE :
			case Camp.T_FREE :
			case Camp.T_STORE :
			case Camp.T_PASTE :
				if (ba != null) {
					camp.backChara();
				} else {
					Body b = camp.getEquip().search(p.x, p.y);
					if (b != null) {
						if (b.base.getKind() == BodyKind.WAZA) {
							camp.removeChara1(p.x, p.y);
						} else {
							pm.displayAnalyze(b);
						}
					} else {
						leftPressed();
					}
				}
				break;
			case Camp.T_ERASE :
				camp.removeCancel(p.x, p.y);
				break;
		}
		setHelp();
	};

}
