/*
 * 作成日: 2004/03/23
 */
package dragon3.attack.calc

import dragon3.common.Body
import dragon3.common.constant.AttackEffect
import dragon3.common.constant.BodyAttribute
import dragon3.common.constant.DamageType
import dragon3.common.util.MoveUtils
import mine.paint.UnitMap

/**
 * @author k-saito
 */
object Damage {

    fun calc(damageType: DamageType, map: UnitMap, ba: Body, bb: Body?, effect: Set<AttackEffect>): Int {

        if (bb == null)
            return 0

        val tikei = MoveUtils.getTikei(map, bb)
        var damage = 0
        var attack = 0
        var guard = 0

        when (damageType) {
            DamageType.NONE -> {
            }
            DamageType.SWORD -> {
                attack = ba.str
                guard = bb.def
            }
            DamageType.MAGIC -> {
                attack = ba.mst
                guard = bb.mdf
            }
            DamageType.SWORD_ALL -> attack = ba.str
            DamageType.MAGIC_ALL -> attack = ba.mst
            else -> throw IllegalArgumentException("DamageType unmatch: " + damageType)
        }

        if (ba.hasAttr(BodyAttribute.ATTACK_UP))
            attack += (ba.str + ba.mst) / 4
        if (bb.hasAttr(BodyAttribute.GUARD_UP))
            attack -= (bb.def + bb.mdf) / 4

        if (effect.contains(AttackEffect.ICE) && bb.hasAttr(BodyAttribute.SLEEP))
            attack += attack / 4
        if (effect.contains(AttackEffect.THUNDER) && bb.hasAttr(BodyAttribute.CHARM))
            attack += attack / 4
        if (effect.contains(AttackEffect.ICE) && bb.hasAttr(BodyAttribute.WET))
            attack += attack / 4
        if (effect.contains(AttackEffect.THUNDER) && bb.hasAttr(BodyAttribute.WET))
            attack += attack / 4
        if (effect.contains(AttackEffect.FIRE) && bb.hasAttr(BodyAttribute.OIL))
            attack += attack / 2
        if (effect.contains(AttackEffect.THUNDER) && tikei == MoveUtils.T_SEA)
            attack += attack / 2
        if (effect.contains(AttackEffect.ICE) && tikei == MoveUtils.T_ICE)
            attack += attack / 2
        if (ba.hasAttr(BodyAttribute.DRAGON_KILLER) && bb.hasAttr(BodyAttribute.DRAGON))
            attack += attack / 2
        if (ba.hasAttr(BodyAttribute.UNDEAD_KILLER) && bb.hasAttr(BodyAttribute.UNDEAD))
            attack += attack / 2

        damage = Math.max(0, attack - guard)

        if (effect.contains(AttackEffect.HEAL) && !bb.hasAttr(BodyAttribute.UNDEAD))
            damage *= -1
        return damage
    }

}
