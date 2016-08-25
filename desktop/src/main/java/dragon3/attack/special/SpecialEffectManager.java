/*
 * 作成日: 2004/03/23
 */
package dragon3.attack.special;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import mine.paint.UnitMap;
import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;

/**
 * @author k-saito
 */
@Singleton
public class SpecialEffectManager {

	private Map<AttackEffect, SpecialEffect> specialEffectMap;

	@Inject
	public SpecialEffectManager() {
		specialEffectMap = new HashMap<AttackEffect, SpecialEffect>();
		specialEffectMap.put(AttackEffect.CRITICAL, new Critical());
		specialEffectMap.put(AttackEffect.DEATH, new Death());
		specialEffectMap.put(AttackEffect.SLEEP, new Sleep());
		specialEffectMap.put(AttackEffect.CHARM, new Charm());
		specialEffectMap.put(AttackEffect.POISON, new Poison());
		specialEffectMap.put(AttackEffect.REGENE, new Regene());
		specialEffectMap.put(AttackEffect.WET, new Wet());
		specialEffectMap.put(AttackEffect.OIL, new Oil());
		specialEffectMap.put(AttackEffect.ATTACK_UP, new AttackUp());
		specialEffectMap.put(AttackEffect.GUARD_UP, new GuardUp());
		specialEffectMap.put(AttackEffect.UPPER, new Upper());
		specialEffectMap.put(AttackEffect.CHOP, new Chop());
		specialEffectMap.put(AttackEffect.REFRESH, new Refresh());
	}

	public boolean isEffective(UnitMap map, Body ba, Body bb, Set<AttackEffect> effectSet, AttackEffect effect) {
		SpecialEffect se = specialEffectMap.get(effect);
		return se.isEffective(map, ba, bb, effectSet);
	}

	public void execute(UnitMap map, Body ba, Body bb, AnimeManager anime, Set<AttackEffect> effectSet, AttackEffect effect) {
		SpecialEffect se = specialEffectMap.get(effect);
		if (se.isEffective(map, ba, bb, effectSet)) {
			se.execute(map, ba, bb, anime);
		}
	}

}
