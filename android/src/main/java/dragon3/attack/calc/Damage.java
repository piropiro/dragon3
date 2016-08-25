/*
 * 作成日: 2004/03/23
 */
package dragon3.attack.calc;

import java.util.Set;

import mine.paint.UnitMap;
import dragon3.common.Body;
import dragon3.common.constant.DamageType;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.util.MoveUtils;

/**
 * @author k-saito
 */
public class Damage {

	public static int calc(DamageType damageType, UnitMap map, Body ba, Body bb, Set<AttackEffect> effect) {

		if (bb == null)
			return 0;

		int tikei = MoveUtils.getTikei(map, bb);
		int damage = 0;
		int attack = 0;
		int guard = 0;

		switch (damageType) {
		case NONE:
			break;
		case SWORD:
			attack = ba.getStr();
			guard = bb.getDef();
			break;
		case MAGIC:
			attack = ba.getMst();
			guard = bb.getMdf();
			break;
		case SWORD_ALL:
			attack = ba.getStr();
			break;
		case MAGIC_ALL:
			attack = ba.getMst();
			break;
		default:
			throw new IllegalArgumentException("DamageType unmatch: " + damageType);
		}

		if (ba.hasAttr(BodyAttribute.ATTACK_UP))
			attack += (ba.getStr() + ba.getMst()) / 4;
		if (bb.hasAttr(BodyAttribute.GUARD_UP))
			attack -= (bb.getDef() + bb.getMdf()) / 4;

		if (effect.contains(AttackEffect.ICE) && bb.hasAttr(BodyAttribute.SLEEP))
			attack *= 1.25;
		if (effect.contains(AttackEffect.THUNDER) && bb.hasAttr(BodyAttribute.CHARM))
			attack *= 1.25;
		if (effect.contains(AttackEffect.ICE) && bb.hasAttr(BodyAttribute.WET))
			attack *= 1.25;
		if (effect.contains(AttackEffect.THUNDER) && bb.hasAttr(BodyAttribute.WET))
			attack *= 1.25;
		if (effect.contains(AttackEffect.FIRE) && bb.hasAttr(BodyAttribute.OIL))
			attack *= 1.5;
		if (effect.contains(AttackEffect.THUNDER) && tikei == MoveUtils.T_SEA)
			attack *= 1.5;
		if (effect.contains(AttackEffect.ICE) && tikei == MoveUtils.T_ICE)
			attack *= 1.5;
		if (ba.hasAttr(BodyAttribute.DRAGON_KILLER) && bb.hasAttr(BodyAttribute.DRAGON))
			attack *= 1.5;
		if (ba.hasAttr(BodyAttribute.UNDEAD_KILLER) && bb.hasAttr(BodyAttribute.UNDEAD))
			attack *= 1.5;

		damage = Math.max(0, attack - guard);

		if (effect.contains(AttackEffect.HEAL) && !bb.hasAttr(BodyAttribute.UNDEAD))
			damage *= -1;
		return damage;
	}

}
