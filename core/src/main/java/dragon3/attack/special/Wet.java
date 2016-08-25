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
public class Wet implements SpecialEffect {

	public boolean isEffective(UnitMap map, Body ba, Body bb, Set<AttackEffect> effect) {

		if (bb.hasAttr(BodyAttribute.ANTI_ALL))
			return false;
		if (!effect.contains(AttackEffect.WET))
			return false;

		if (bb.hasAttr(BodyAttribute.WET))
			return false;

		return true;
	}



	public void execute(UnitMap map, Body ba, Body bb, AnimeManager anime) {

		anime.statusAnime(AnimeManager.STATUS_WET, bb.getX(), bb.getY());
		bb.addAttr(BodyAttribute.WET);
		bb.addAttr(BodyAttribute.WET_LOCK);
	}

}
