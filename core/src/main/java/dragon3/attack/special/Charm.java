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
public class Charm implements SpecialEffect {


	public boolean isEffective(UnitMap map, Body ba, Body bb, Set<AttackEffect> effect) {

		if (bb.hasAttr(BodyAttribute.ANTI_ALL))
			return false;
		if (!effect.contains(AttackEffect.CHARM))
			return false;

		if (bb.getMdf() * 2 >= ba.getMst())
			return false;
		if (bb.hasAttr(BodyAttribute.GUARD_UP))
			return false;

		if (bb.hasAttr(BodyAttribute.ANTI_CHARM))
			return false;
		if (bb.hasAttr(BodyAttribute.CHARM))
			return false;

		return true;
	}


	public void execute(UnitMap map, Body ba, Body bb, AnimeManager anime) {

		if (bb.hasAttr(BodyAttribute.CHARM_LOCK)) {
			bb.removeAttr(BodyAttribute.CHARM_LOCK);
		} else {
			anime.statusAnime(AnimeManager.STATUS_CHARM, bb.getX(), bb.getY());
			bb.addAttr(BodyAttribute.CHARM);
			bb.addAttr(BodyAttribute.CHARM_LOCK);
		}
	}

}
