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
public class Critical implements SpecialEffect {

	public boolean isEffective(UnitMap map, Body ba, Body bb, Set<AttackEffect> effect) {

		if (bb.hasAttr(BodyAttribute.ANTI_ALL))
			return false;

		if (!effect.contains(AttackEffect.CRITICAL))
			return false;
		if (bb.hasAttr(BodyAttribute.ANTI_CRITICAL))
			return false;
		if (ba.getLevel() < bb.getLevel())
			return false;
		if (bb.hasAttr(BodyAttribute.GUARD_UP))
			return false;
		if (bb.hasAttr(BodyAttribute.POISON))
			return true;
		if (bb.getDef() >= ba.getStr())
			return false;

		return true;
	}

	/* (非 Javadoc)
	 * @see dragon3.attack.special.SpecialEffect#execute(dragon3.common.Body, dragon3.common.Body)
	 */
	public void execute(UnitMap map, Body ba, Body bb, AnimeManager anime) {
		anime.systemAnime(AnimeManager.ID_FINISH, bb.getX(), bb.getY());
		anime.criticalAnime(bb.getX(), bb.getY());
		anime.slideText(AnimeManager.TEXT_FINISH, bb.getX(), bb.getY());
	}

}
