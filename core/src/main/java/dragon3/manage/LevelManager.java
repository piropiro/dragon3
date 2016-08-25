package dragon3.manage;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Inject;

import dragon3.Statics;
import dragon3.camp.Equip;
import dragon3.common.Body;
import dragon3.common.constant.BodyKind;
import dragon3.common.constant.Texts;
import dragon3.data.WazaData;
import dragon3.panel.PanelManager;

public class LevelManager {

	public static final int MAX_EXP = 100;

	@Inject Statics statics;
	
	private Equip equip;
	private PanelManager pm;

	/*** Level Up **************************************/

	public LevelManager(Equip equip, PanelManager pm) {
		this.equip = equip;
		this.pm = pm;
	}
	
	public void levelup(Body ba) {
		
		// 装備レベルアップ
		for (Body item : equip.getEquipOf(ba).values()) {
			itemLevelup(item);
		}

		// キャラレベルアップ
		charaLevelup(ba);
		
		// 技習得
		learnWaza(ba);
		
		pm.startMessage(ba);
	}

	private void charaLevelup(Body ba) {
		while (ba.getExp() >= MAX_EXP) {
			ba.setExp(ba.getExp() - MAX_EXP);
			if (ba.base.getName().length() <= 1) {
				pm.addMessage(ba.base.getName() + Texts.ha + Texts.equip1);
			} else {
				pm.addMessage(ba.base.getName() + Texts.ha);
				pm.addMessage(Texts.equip1);
			}
			statusup(ba);
			ba.setLevel(ba.getLevel() + 1);
		}
	}
	
	private void itemLevelup(Body item) {
		if (item == null)
			return;
		
		if (item.getExp() >= MAX_EXP) {
			item.setExp(MAX_EXP);
			
			if (!item.getMaster()) {
				item.setMaster(true);
				pm.addMessage(item.base.getKind().getText() + Texts.equip1);
			}
		}
	}

	private void learnWaza(Body ba) {
		List<String> wazaList = equip.equipWaza(ba);
		
		for (String wazaId : wazaList) {
			if (!ba.getWazaList().contains(wazaId)) {
				WazaData waza = statics.getWazaData(wazaId);
				if (waza.getName().length() <= 5) {
					pm.addMessage(waza.getName() + Texts.wo + Texts.equip2);
				} else {
					pm.addMessage(waza.getName() + Texts.wo);
					pm.addMessage(Texts.equip2);
				}
			}
		}
		ba.setWazaList(wazaList);
	}

	private void statusup(String name, Supplier<Integer> baseGet, Consumer<Integer> baseSet, int up,
			Supplier<Integer> currentGet, Consumer<Integer> currentSet) {
		
		// ベースステータス上昇
		int before = baseGet.get();
		baseSet.accept(before + up);
		
		// 上昇値を計算
		int ns = (before + up) / 10 - before / 10;
		if (ns > 0)
			pm.addMessage(name + Texts.ga + " " + ns + Texts.equip3);
		
		// 現在のステータスに上昇値を加算
		int current = currentGet.get();
		currentSet.accept(current + ns);
	}

	private void statusup(Body ba) {
		
		Map<BodyKind, Body> list = equip.getEquipOf(ba);
		Body clazz = list.get(BodyKind.CLASS);
		
		statusup(Texts.hp, ba.base::getHp, ba.base::setHp, clazz.getHp(),
				ba::getHpMax, ba::setHpMax);
		statusup(Texts.kougekiryoku, ba.base::getStr, ba.base::setStr, clazz.getStr(),
				ba::getStr, ba::setStr);
		statusup(Texts.bougyoryoku, ba.base::getDef, ba.base::setDef, clazz.getDef(),
				ba::getDef, ba::setDef);
		statusup(Texts.mahouryoku, ba.base::getMst, ba.base::setMst, clazz.getMst(),
				ba::getMst, ba::setMst);
		statusup(Texts.teikouryoku, ba.base::getMdf, ba.base::setMdf, clazz.getMdf(),
				ba::getMdf, ba::setMdf);
		statusup(Texts.meichuritu, ba.base::getHit, ba.base::setHit, clazz.getHit(),
				ba::getHit, ba::setHit);
		statusup(Texts.kaihiritu, ba.base::getMis, ba.base::setMis, clazz.getMis(),
				ba::getMis, ba::setMis);
	}
}
