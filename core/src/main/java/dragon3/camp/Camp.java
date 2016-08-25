package dragon3.camp;

import java.util.ArrayList;
import java.util.List;

import dragon3.common.Body;
import dragon3.common.constant.BodyKind;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.controller.UnitWorks;
import dragon3.manage.TreasureManager;
import dragon3.map.MapWorks;
import dragon3.panel.PanelManager;
import dragon3.panel.paint.CampDataPaint;
import lombok.Getter;
import mine.paint.UnitMap;
import mine.util.Point;

public class Camp {

	// private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private PanelManager pm;
	
	@Getter Equip equip;
	List<Body> equips;

	Point ps; // Last Position
	Point end;
	@Getter Body ba; // Selected Body
	Body[] items; // Stock Item
	@Getter boolean sortf; // Sorting Flag

	public static final int T_NONE = 0;
	public static final int T_FREE = 1;
	public static final int T_PASTE = 2;
	public static final int T_ERASE = 3;
	public static final int T_STORE = 4;
	
	public static final int COL_CHARA1 = 1;
	public static final int COL_CHARA2 = 8;
	
	public static final int OFFSET_CLASS = 1;
	public static final int OFFSET_WEPON = 2;
	public static final int OFFSET_ARMOR = 3;
	public static final int OFFSET_ITEM = 4;
	public static final int OFFSET_SOUL = 5;

	/*** Constructer **********************************/

	public Camp(UnitWorks uw, TreasureManager treasure, Equip equip) {
		// this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.pm = uw.getPanelManager();
		
		this.equip = equip;
		this.mw = uw.getMapWorks();
		equips = equip.getEquips();
		setEquip();
		if (treasure != null) {
			setSource(treasure.getSources(), false);
		}

	}


	/*** Repaint ******************************************/

	public void repaint(int[][] data) {
		map.setPage(Page.P10, data);
		map.clear(Page.P01, 0);
		map.clear(Page.P20, 0);
		map.clear(Page.P30, 0);
		map.clear(Page.P40, 0);
		map.clear(Page.P50, 0);
		for (Body b : equips) {
			if (map.getData(Page.P10, b.getX(), b.getY()) == T_FREE) {
				map.setData(Page.P10, b.getX(), b.getY(), T_PASTE);
			}
			map.setData(Page.P20, b.getX(), b.getY(), b.getImageNum());
		}
	}
	
	/*** Deploy Main Soul ************************************/
	
//	public void createMainSoul(Body b) {
//		Body mainSoul = equip.search(b.getX() + 1, b.getY());
//		if (mainSoul == null) {
//			mainSoul = BodyDataLoader.loadBodyData(b.getId());
//			mainSoul.setImage(b.getSoulType().getImage());
//			mainSoul.setImageNum(im.getBodyList().getNum(mainSoul.getImage()));
//			mainSoul.setX(b.getX() + 1);
//			mainSoul.setY(b.getY());
//			mainSoul.setGoalX(b.getGoalX() + 1);
//			mainSoul.setGoalY(b.getGoalY());
//			mainSoul.setColor(GameColors.GREEN);
//			equip.addBody(mainSoul);
//		}
//	}

	/*** Deploy Equip ****************************************/

	private void setEquip() {
		for (int i = equips.size() - 1; i >= 0; i--) {
			Body b = (Body) equips.get(i);
			b.setMax();
			b.resetAttr();
			if (GameColor.Companion.isPlayer(b.getColor())) {
				b.setX(b.getGoalX());
				b.setY(b.getGoalY());
			}
			if (b.getX() > 13 && b.getY() == 14) {
				equips.remove(i);
				continue;
			}
			map.setData(Page.P20, b.getX(), b.getY(), b.getImageNum());
		}
	}

	/*** Deploy Source **************************************/

	private void setSource(List<Body> Sources, boolean reversef) {
		if (Sources == null)
			return;

		for (int i = 1; i < 15; i++) {
			int y = (reversef) ? (14 - i) : i;
			for (int x = 14; x < 20; x++) {
				if (Sources.size() == 0)
					break;
				if (map.getData(Page.P20, x, y) != 0)
					continue;
				Body b = (Body) Sources.get(0);
				b.setX(x);
				b.setY(y);
				b.setMax();
				equips.add(b);
				Sources.remove(b);
				map.setData(Page.P20, b.getX(), b.getY(), b.getImageNum());
			}
		}
	}

	/*** Remove Dust ********************************/

	public void removeDust() {
		for (int i = equips.size() - 1; i >= 0; i--) {
			Body b = (Body) equips.get(i);
			if (b.base.getKind() == BodyKind.WAZA
				|| map.getData(Page.P10, b.getX(), b.getY()) == T_ERASE) {
				equips.remove(i);
				map.setData(Page.P20, b.getX(), b.getY(), 0);
				mw.ppaint(b.getX(), b.getY());
			}
		}
	}

	/*** Sort Item **********************************/

	public void sortItem() {
		List<Body> itemList = new ArrayList<>();
		List<Body> wazaList = new ArrayList<>();

		for (int i = equips.size() - 1; i >= 0; i--) {
			Body b = (Body) equips.get(i);
			if (b.getY() == 0 || b.getY() == 14)
				continue;
			if (b.getX() < 14)
				continue;
			if (b.base.getKind() == BodyKind.WAZA) {
				wazaList.add(b);
			} else {
				itemList.add(b);
			}
			equips.remove(i);
			map.setData(Page.P20, b.getX(), b.getY(), 0);
		}
		setSource(itemList, false);
		mw.repaint();
	}

	/*** Back to Source **************************************/

	public void backChara() {
		for (int y = 1; y < 15; y++) {
			for (int x = 14; x < 20; x++) {
				if (map.getData(Page.P20, x, y) == 0) {
					moveChara(x, y);
					putChara(x, y, ba);
					mw.repaint();
					return;
				}
			}
		}
	}

	/*** Move Chara ************************/

	public void moveChara(int x, int y) {
		if (end != null) {
			removeCancel(end.x, end.y);
			end = null;
		}
		if (ba == null)
			return;

		if (ps != null) {
			map.setData(Page.P20, ps.x, ps.y, 0);
		}
		if (map.getData(Page.P20, x, y) == 0) {
			map.setData(Page.P20, x, y, ba.getImageNum());
			ps = new Point(x, y);
		} else {
			ps = null;
		}
	}

	/*** Put Chara ***************************/

	public void putChara(int x, int y, Body b) {
		if (equip.search(x, y) != null)
			return;
		equips.add(b);
		b.setX(x);
		b.setY(y);
		map.setData(Page.P20, x, y, b.getImageNum());
		ps = null;
		ba = null;
	}

	public void putChara2(int x, int y) {
		if (equip.search(x, y) != null)
			return;
		Body bb;

		switch (ba.base.getKind()) {
		case CLASS:
			if (x == COL_CHARA1 + OFFSET_CLASS || x == COL_CHARA2 + OFFSET_CLASS) {
				bb = charaCheck(x - OFFSET_CLASS, y);
				if (bb == null)
					return;
				if (!equipCheck(bb, ba)) {
					pm.displayLarge(Texts.warning3, GameColor.RED, 1000);
					return;
				}

				if (!levelCheck(bb, ba))
					return;
				if (equip.search(bb.getX() + OFFSET_WEPON, y) != null) {
					pm.displayLarge(Texts.warning1, GameColor.RED, 1000);
					return;
				}
				map.setData(Page.P10, x, y, T_PASTE);
				putChara(x, y, ba);
				equip.equip(bb);
				return;
			}
			break;
		case WEPON:
			if (x == COL_CHARA1 + OFFSET_WEPON || x == COL_CHARA2 + OFFSET_WEPON) {
				bb = charaCheck(x - OFFSET_WEPON, y);
				if (bb == null)
					return;
				if (!equipCheck(bb, ba)) {
					pm.displayLarge(Texts.warning4, GameColor.RED, 1000);
					return;
				}
				if (!levelCheck(bb, ba))
					return;
				putChara(x, y, ba);
				return;
			}
			break;
		case ARMOR:
			if (x == COL_CHARA1 + OFFSET_ARMOR || x == COL_CHARA2 + OFFSET_ARMOR) {
				bb = charaCheck(x - OFFSET_ARMOR, y);
				if (bb == null)
					return;
				if (!levelCheck(bb, ba))
					return;
				putChara(x, y, ba);
				return;
			}
			break;
		case ITEM:
			if (x == COL_CHARA1 + OFFSET_ITEM || x == COL_CHARA2 + OFFSET_ITEM) {
				bb = charaCheck(x - 4, y);
				if (bb == null)
					return;
				if (!levelCheck(bb, ba))
					return;
				putChara(x, y, ba);
				return;
			}
			break;
		case SOUL:
			if (x == COL_CHARA1 + OFFSET_SOUL || x == COL_CHARA2 + OFFSET_SOUL) {
				bb = charaCheck(x - OFFSET_SOUL, y);
				if (bb == null)
					return;
				if (!levelCheck(bb, ba))
					return;
				putChara(x, y, ba);
				return;
			}
			break;
		case WAZA:
			break;
		default:
			if (x == COL_CHARA1 || x == COL_CHARA2) {
				if (sortf) {
					putSortItems(x, y, items);
				} else {
					ba.setGoalX(x);
					ba.setGoalY(y);
					ba.setColor(GameColor.BLUE);
					map.setData(Page.P10, x, y, T_PASTE);
					putChara(x, y, ba);
				}
				return;
			}
			break;
		}
		alarm(ba);
	}

	/*** Alarm ***********************************/

	private void alarm(Body ba) {
		pm.displayLarge(Texts.sokoni + ba.base.getKind().getText() + Texts.haokemasen, GameColor.RED, 1000);
	}

	private Body charaCheck(int x, int y) {
		Body bb = equip.search(x, y);
		if (bb != null)
			return bb;
		pm.displayLarge(Texts.warning2, GameColor.RED, 1000);
		return null;
	}

	private boolean equipCheck(Body ba, Body bb) {
		if (ba == null)
			return false;
		if (bb == null)
			return false;

		switch (bb.base.getKind()) {
		case WEPON:
			return ba.base.getWeponType().equals(bb.base.getWeponType());
		case ARMOR:
			return ba.base.getArmorType().equals(bb.base.getArmorType());
		default:
		}

		return false;
	}

	private boolean levelCheck(Body ba, Body bb) {
		if (ba.getLevel() >= bb.getLevel())
			return true;
		pm.displayLarge(Texts.warning5, GameColor.RED, 1000);
		return false;
	}

	private void help(int x, int y) {
		switch (map.getData(Page.P10, x, y)) {
			case T_FREE :
			case T_STORE :
				if (x == 1) {
					pm.displayCampData(x, y, CampDataPaint.C_CHARA1, GameColor.BLUE);
				} else if (x == 8) {
					pm.displayCampData(x, y, CampDataPaint.C_CHARA2, GameColor.BLUE);
				} else if (x == 2 || x == 9) {
					pm.displayCampData(x, y, CampDataPaint.C_CLASS, GameColor.BLUE);
				} else if (x == 3 || x == 10) {
					pm.displayCampData(x, y, CampDataPaint.C_WEPON, GameColor.BLUE);
				} else if (x == 4 || x == 11) {
					pm.displayCampData(x, y, CampDataPaint.C_ARMOR, GameColor.BLUE);
				} else if (x == 5 || x == 12) {
					pm.displayCampData(x, y, CampDataPaint.C_ITEM, GameColor.BLUE);
				}
				break;
			case T_ERASE :
				pm.displayCampData(x, y, CampDataPaint.C_DUST, GameColor.RED);
				break;
		}
	}

	/*** Pick Chara ***************************/

	public void pickChara(int x, int y) {
		Body b = equip.search(x, y);
		if (b == null) {
			help(x, y);
		} else {
			b.setColor(GameColor.GREEN);
			equips.remove(b);
			ps = new Point(x, y);
			ba = b;
		}
	}

	/*** Change Chara ***********************/

	public void changeChara(int x, int y) {
		if (equip.search(x, y) == null) {
			putChara(x, y, ba);
			return;
		} else {
			pickChara(x, y);
			Body bb = ba;
			putChara(x, y, ba);
			ba = bb;
		}
	}

	/*** Remove Chara **********************/

	public void removeChara1(int x, int y) {
		Body bb = equip.search(x, y);
		if (bb == null)
			return;
		if (bb.getColor() == GameColor.BLUE) {
			items = pickSortItems(x, y);
			return;
		}

		end = new Point(x, y);
		map.setData(Page.P10, x, y, T_ERASE);
		map.setData(Page.P30, x, y, 4);
		map.setData(Page.P40, x, y, 0);
		mw.ppaint(x, y);
	}
	public void removeChara2(int x, int y) {
		Body bb = equip.search(x, y);
		equips.remove(bb);

		end = null;
		switch (bb.base.getKind()) {
		case CLASS:
			map.setData(Page.P10, x, y, T_FREE);
			List<Body> list = new ArrayList<>();
			bb.setExp(0);
			list.add(bb);
			setSource(list, false);
			mw.ppaint(bb.getX(), bb.getY());
			break;
		case WAZA:
			map.setData(Page.P10, x, y, T_NONE);
			break;
		default:
		}
		map.setData(Page.P20, x, y, 0);
		map.setData(Page.P30, x, y, 0);
		mw.ppaint(x, y);
	}

	/*** Remove Cancel ***********************************/

	public void removeCancel(int x, int y) {
		if (map.getData(Page.P30, x, y) == 0)
			return;
		Body bb = equip.search(x, y);
		switch (bb.base.getKind()) {
		case SOUL:
			map.setData(Page.P10, x, y, T_PASTE);
			break;
		case WAZA:
			map.setData(Page.P10, x, y, T_NONE);
			break;
		default:
		}
		map.setData(Page.P30, x, y, 0);
		mw.ppaint(x, y);
	}

	/*** Sort ***************************************/

	public Body[] pickSortItems(int x, int y) {
		Body[] tmp = new Body[5];
		for (int i = 1; i <= 4; i++) {
			tmp[i] = equip.search(x + i, y);
			if (tmp[i] == null)
				continue;
			map.setData(Page.P20, x + i, y, 0);
			tmp[i].setColor(GameColor.GREEN);
			equips.remove(tmp[i]);
			sortf = true;
		}
		map.setData(Page.P10, x, y, T_FREE);
		map.setData(Page.P10, x + 1, y, T_FREE);
		pickChara(x, y);
		tmp[0] = ba;
		mw.repaint();
		return tmp;
	}

	private void putSortItems(int x, int y, Body[] tmp) {
		for (int i = 1; i <= 4; i++) {
			if (tmp[i] == null)
				continue;
			equips.add(tmp[i]);
			tmp[i].setX(x + i);
			tmp[i].setY(y);
			map.setData(Page.P20, x + i, y, tmp[i].getImageNum());
		}
		tmp[0].setGoalX(x);
		tmp[0].setGoalY(y);
		tmp[0].setColor(GameColor.BLUE);
		map.setData(Page.P10, x, y, T_PASTE);
		putChara(x, y, tmp[0]);
		if (tmp[1] != null)
			map.setData(Page.P10, x + 1, y, T_PASTE);
		sortf = false;
		mw.repaint();
	}

	public void changeSortChara(int x, int y) {
		if (x != 1 && x != 8)
			return;
		if (!sortf)
			return;
		Body[] tmp = pickSortItems(x, y);
		putSortItems(x, y, items);
		items = tmp;
		ba = tmp[0];
		for (int i = 1; i <= 4; i++) {
			if (tmp[i] != null)
				sortf = true;
		}
		mw.repaint();
	}

}
