/*
 * 作成日: 2004/03/23
 */
package dragon3.attack.special;

import java.util.Set;

import mine.paint.UnitMap;
import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.Page;
import dragon3.common.constant.BodyAttribute;

/**
 * @author k-saito
 */
public class Refresh implements SpecialEffect {

	private UnitMap map;

	public Refresh(UnitMap map){
		this.map = map;
	}

	public boolean isEffective(Body ba, Body bb, Set<AttackEffect> effect) {

		if (bb.hasAttr(BodyAttribute.ANTI_ALL))
			return false;
		if (!effect.contains(AttackEffect.REFRESH))
			return false;

		if (map.getData(Page.P30, bb.getX(), bb.getY()) == 0)
			return false;

		return true;
	}


	public void execute(Body ba, Body bb, AnimeManager anime) {

		map.setData(Page.P30, bb.getX(), bb.getY(), 0);
		anime.systemAnime(AnimeManager.ID_REFRESH, bb.getX(), bb.getY());
	}

}
