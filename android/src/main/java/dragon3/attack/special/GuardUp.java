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
public class GuardUp implements SpecialEffect {


	public boolean isEffective(Body ba, Body bb, Set<AttackEffect> effect) {

		if (bb.hasAttr(BodyAttribute.ANTI_ALL))
			return false;
		if (!effect.contains(AttackEffect.GUARD_UP))
			return false;

		if (bb.hasAttr(BodyAttribute.GUARD_UP))
			return false;

		return true;
	}

	public void execute(Body ba, Body bb, AnimeManager anime) {

		anime.statusAnime(AnimeManager.STATUS_GUARD_UP, bb.getX(), bb.getY());
		bb.addAttr(BodyAttribute.GUARD_UP);
		bb.addAttr(BodyAttribute.GUARD_UP_LOCK);
	}

}
