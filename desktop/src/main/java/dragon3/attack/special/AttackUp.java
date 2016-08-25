/*
 * 作成日: 2004/03/23
 */
package dragon3.attack.special;

import java.util.Set;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.BodyAttribute;
import mine.paint.UnitMap;

/**
 * @author k-saito
 */
public class AttackUp implements SpecialEffect {


	public boolean isEffective(UnitMap map, Body ba, Body bb, Set<AttackEffect> effect) {

		if (bb.hasAttr(BodyAttribute.ANTI_ALL))
			return false;
		if (!effect.contains(AttackEffect.ATTACK_UP))
			return false;

		if (bb.hasAttr(BodyAttribute.ATTACK_UP))
			return false;

		return true;
	}


	public void execute(UnitMap map, Body ba, Body bb, AnimeManager anime) {

		anime.statusAnime(AnimeManager.STATUS_ATTACK_UP, bb.getX(), bb.getY());
		bb.addAttr(BodyAttribute.ATTACK_UP);
		bb.addAttr(BodyAttribute.ATTACK_UP_LOCK);

	}

}
