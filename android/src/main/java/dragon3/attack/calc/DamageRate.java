/*
 * 作成日: 2004/03/23
 */
package dragon3.attack.calc;

import java.util.Set;

import mine.paint.UnitMap;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.MoveType;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.util.MoveUtils;

/**
 * @author k-saito
 */
public class DamageRate {

	public static int calc(UnitMap map, Body ba, Body bb, Set<AttackEffect> effect) {
		if (bb == null)
			return 0;
		int tikei = MoveUtils.getTikei(map, bb);
		int rate = 100;
		if (effect.contains(AttackEffect.DAMAGE_200))
			rate += 100;
		if (effect.contains(AttackEffect.DAMAGE_300))
			rate += 200;
		if (effect.contains(AttackEffect.DAMAGE_150))
			rate += 50;
		if (effect.contains(AttackEffect.FIRE) && bb.hasAttr(BodyAttribute.FIRE_200))
			rate += 100;
		if (effect.contains(AttackEffect.ICE) && bb.hasAttr(BodyAttribute.ICE_200))
			rate += 100;
		if (effect.contains(AttackEffect.THUNDER) && bb.hasAttr(BodyAttribute.THUNDER_200))
			rate += 100;
		if (effect.contains(AttackEffect.SORA_200) && tikei == MoveUtils.T_SKY)
			rate += 100;
		if (effect.contains(AttackEffect.MIZU_200) && tikei == MoveUtils.T_SEA)
			rate += 100;
		if (effect.contains(AttackEffect.RIKU_150) && tikei == MoveUtils.T_LAND)
			rate += 50;
		if (effect.contains(AttackEffect.DRAGON_200) && bb.hasAttr(BodyAttribute.DRAGON))
			rate += 100;
		if (effect.contains(AttackEffect.UNDEAD_200) && bb.hasAttr(BodyAttribute.UNDEAD))
			rate += 100;
		if (bb.hasAttr(BodyAttribute.SLEEP))
			rate += 150;

		if (effect.contains(AttackEffect.FIRE) && bb.hasAttr(BodyAttribute.WET))
			rate /= 2;
		if (effect.contains(AttackEffect.FIRE) && bb.hasAttr(BodyAttribute.FIRE_50))
			rate /= 2;
		if (effect.contains(AttackEffect.ICE) && bb.hasAttr(BodyAttribute.ICE_50))
			rate /= 2;
		if (effect.contains(AttackEffect.THUNDER) && bb.hasAttr(BodyAttribute.THUNDER_50))
			rate /= 2;
		if (effect.contains(AttackEffect.FIRE) && tikei == MoveUtils.T_SEA)
			rate /= 2;
		if (effect.contains(AttackEffect.FIRE) && tikei == MoveUtils.T_ICE)
			rate /= 2;

		// Aquatic Guard
		if (bb.base.getMoveType().equals(MoveType.SWIM) || bb.base.getMoveType().equals(MoveType.TWIN)) {
			if (!effect.contains(AttackEffect.MIZU_100)) {
				if (tikei == MoveUtils.T_SEA && !effect.contains(AttackEffect.THUNDER))
					rate /= 2;
				if (tikei == MoveUtils.T_POOL && !effect.contains(AttackEffect.FIRE))
					rate /= 2;
			}
		}
		// Aquatic Attack
		if (bb.base.getMoveType().equals(MoveType.SWIM) || bb.base.getMoveType().equals(MoveType.TWIN)) {
			if (MoveUtils.getTikei(map, ba) == MoveUtils.T_SKY)
				rate /= 2;
			if (MoveUtils.getTikei(map, ba) == MoveUtils.T_LAND)
				rate /= 2;
		}

		if (effect.contains(AttackEffect.RIKU_0) && tikei != MoveUtils.T_LAND)
			rate = 0;
		if (effect.contains(AttackEffect.MIZU_0) && tikei != MoveUtils.T_SEA)
			rate = 0;
		if (effect.contains(AttackEffect.FIRE) && bb.hasAttr(BodyAttribute.FIRE_0))
			rate = 0;
		if (effect.contains(AttackEffect.ICE) && bb.hasAttr(BodyAttribute.ICE_0))
			rate = 0;
		if (effect.contains(AttackEffect.THUNDER) && bb.hasAttr(BodyAttribute.THUNDER_0))
			rate = 0;
		return rate;
	}

}
