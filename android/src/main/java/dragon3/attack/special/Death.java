/*
 * 作成日: 2004/03/23
 */
package dragon3.attack.special;

import java.util.Set;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.BodyAttribute;

/**
 * @author k-saito
 */
public class Death implements SpecialEffect {

	public boolean isEffective(Body ba, Body bb, Set<AttackEffect> effect) {

		if (bb.hasAttr(BodyAttribute.ANTI_ALL))
			return false;

		if (!effect.contains(AttackEffect.DEATH))
			return false;
		if (bb.hasAttr(BodyAttribute.ANTI_CRITICAL))
			return false;
		if (ba.getLevel() < bb.getLevel())
			return false;
		if (bb.hasAttr(BodyAttribute.GUARD_UP))
			return false;
		if (bb.hasAttr(BodyAttribute.UNDEAD))
			return false;
		if (bb.hasAttr(BodyAttribute.ANTI_DEATH))
			return false;
		if (bb.hasAttr(BodyAttribute.POISON))
			return true;

		if (bb.getMdf() >= ba.getMst())
			return false;
		if (bb.getHp() > bb.getHpMax() * 3 / 4)
			return false;

		return true;
	}


	public void execute(Body ba, Body bb, AnimeManager anime) {
		anime.systemAnime(AnimeManager.ID_DEATH, bb.getX(), bb.getY());
		anime.criticalAnime(bb.getX(), bb.getY());
		anime.slideText(AnimeManager.TEXT_DEATH, bb.getX(), bb.getY());
	}

}
