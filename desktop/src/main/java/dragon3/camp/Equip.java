package dragon3.camp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dragon3.common.Body;
import dragon3.common.constant.BodyKind;
import dragon3.common.constant.GameColor;

public class Equip {

	public static final int BASE_EXP = 20;
	
	private List<Body> equipList;

	/*** Constructer **************************************/

	public Equip(List<Body> equipList) {
		this.equipList = equipList;
	}

	/*** Search ********************************************/

	public Body search(int x, int y) {
		for (Body b : equipList) {
			if (b != null && b.getX() == x && b.getY() == y) {
				return b;
			}
		}
		return null;
	}
	public Body searchItem(int x, int y) {
		for (Body b : equipList) {
			if (b.getColor() == GameColor.GREEN && b.getX() == x && b.getY() == y) {
				return b;
			}
		}
		return null;
	}

	public void addBody(Body b) {
		equipList.add(b);
	}
	public void removeBody(Body b) {
		equipList.remove(b);
	}
	public List<Body> getEquips() {
		return equipList;
	}

	/*** EXP ****************************************/

	public void getExp(Body ba, Body bb) {

		int exp = (int)(BASE_EXP * Math.pow(1.5, bb.getLevel() - ba.getLevel()));
		ba.setExp(ba.getExp() + exp);
		bb.setExp(0);

		// Item Exp
		for (Body item : getEquipOf(ba).values()) {
			item.setExp(item.getExp() + exp);
		}
	}
	
	public Map<BodyKind, Body> getEquipOf(Body ba) {
		Map<BodyKind, Body> list = new HashMap<>();
		
		for (int offset = 1; offset < 5; offset++) {
			Body item = searchItem(ba.getGoalX() + offset, ba.getGoalY());
			if (item != null) {
				list.put(item.getBase().getKind(), item);
			}
		}
		return list;
	}
	

	/*** Player ************************************/

	public List<Body> getPlayers() {
		
		List<Body> playerList = new ArrayList<>();
		
		for (Body b : equipList) {
			if (!GameColor.Companion.isPlayer(b.getColor()))
				continue;
			b.setMax();
			equip(b);
			playerList.add(b);
		}
		
		// Campの配置位置でソート
		playerList.sort((b1, b2) -> b1.getGoalY() - b2.getGoalY());

		return playerList;
	}

	public Body getChangeChara(Body ba) {
		Body bb = null;
		for (Body b : equipList) {
			if (!GameColor.Companion.isPlayer(b.getColor()))
				continue;
			if (b.getGoalX() == ba.getGoalX() + 7 && b.getGoalY() == ba.getGoalY()) {
				bb = b;
				break;
			}
		}
		if (bb == null)
			return null;
		bb.setMax();
		equip(bb);
		return bb;
	}

	/*** Equip **********************************************/

	public void equip(Body ba) {
		ba.setMax();
		ba.resetAttr();
		
		Map<BodyKind, Body> list = getEquipOf(ba);
		equip(ba, list.get(BodyKind.WEPON));
		equip(ba, list.get(BodyKind.ARMOR));
		equip(ba, list.get(BodyKind.ITEM));
		equip(ba, list.get(BodyKind.SOUL));
		
		List<String> wazaList = equipWaza(ba);
		
		ba.setWazaList(wazaList);
		ba.restrict();
	}


	private void equip(Body ba, Body bb) {
		if (bb == null)
			return;
		ba.setStr(ba.getStr() + bb.getStr());
		ba.setDef(ba.getDef() + bb.getDef());
		ba.setMst(ba.getMst() + bb.getMst());
		ba.setMdf(ba.getMdf() + bb.getMdf());
		ba.setHit(ba.getHit() + bb.getHit());
		ba.setMis(ba.getMis() + bb.getMis());
	}

	/***  *****************************************/

	// 0 - Normal Attack
	// 1 - Wepon Attack
	// 2 - Armor Attack
	// 3 - Class Attack 1
	// 4 - Class Attack 2
	// 5 - Item Attack 1

	public List<String> equipWaza(Body ba) {
		List<String> wazaList = new ArrayList<>();
		
		Map<BodyKind, Body> list = getEquipOf(ba);
		
		
		// 技0 = 武器0 > 職業0 > キャラ0
		if (checkAddWaza(ba, list.get(BodyKind.WEPON), 0, false, false, wazaList)) {
		} else if (checkAddWaza(ba, list.get(BodyKind.CLASS), 0, false, false, wazaList)) {
		} else if (checkAddWaza(ba, ba, 0, false, false, wazaList)) {
		}

		// 武器1
		checkAddWaza(ba, list.get(BodyKind.WEPON), 1, true, true, wazaList);
		// 防具0
		checkAddWaza(ba, list.get(BodyKind.ARMOR), 0, true, true, wazaList);
		// 小物0
		checkAddWaza(ba, list.get(BodyKind.ITEM), 0, true, true, wazaList);
		// 魂玉0
		checkAddWaza(ba, list.get(BodyKind.SOUL), 0, true, true, wazaList);
		
		// キャラ1〜
		for (int i = 1; i < ba.base.getWazaList().size(); i++) {
			checkAddWaza(ba, ba, 1, false, false, wazaList);
		}
		
		return wazaList;
	}
	
	private boolean checkAddWaza(Body body, Body item, int i, boolean levelLimit, boolean masterLimit, List<String> wazaList) {
		if (item == null)
			return false;
		
		String waza = item.base.getWazaList().get(i);
		
		if (waza.equals("none")) 
			return false;
		
		if (wazaList.contains(waza))
			return false;
	
		if (levelLimit && body.getLevel() < item.getLevel())
			return false;
		
		if (masterLimit && !item.getMaster()) {
			return false;
		}
		
		wazaList.add(waza);
		return true;
	}



	/*** Have *************************************/

	public boolean have(Body ba) {
		for (Body b : equipList) {
			if (!ba.base.getId().equals(b.base.getId())) {
				continue;
			} else if (isDust(b)) {
				continue;
			} else {
				return true;
			}
		}
		return false;
	}

	/*** Dust ***********************************/

	private boolean isDust(Body b) {
		if (b.getY() != 14)
			return false;
		if (b.getX() < 14)
			return false;
		return true;
	}

}
