/*
 * 作成日: 2004/03/23
 */
package dragon3.attack.calc

import dragon3.common.Body
import dragon3.common.constant.AttackEffect
import dragon3.common.constant.BodyAttribute
import mine.MineUtils

/**
 * @author k-saito
 */
object HitRate {

    const val SINGLE_HIT = 10
    const val DOUBLE_HIT = SINGLE_HIT * 2

    fun calcPredict(ba: Body, bb: Body, effect: Set<AttackEffect>): Int {
        return calc(ba, bb, effect, 0)
    }

    fun calcReal(ba: Body, bb: Body, effect: Set<AttackEffect>): Int {
        //		return calc(ba, bb, effect, Luck.rnd(1, ba));
        return calc(ba, bb, effect, 0)
    }

    private fun calc(ba: Body, bb: Body?, effect: Set<AttackEffect>, rnd: Int): Int {
        if (bb == null)
            return 0
        if (ba.hit == 0)
            return 0

        if (effect.contains(AttackEffect.HICHU))
            return SINGLE_HIT

        var hit = DOUBLE_HIT - bb.mis * DOUBLE_HIT / ba.hit

        hit += rnd

        if (effect.contains(AttackEffect.HIT_12))
            hit += 12
        if (effect.contains(AttackEffect.HIT_4))
            hit += 4
        if (effect.contains(AttackEffect.MISS_4))
            hit -= 4

        if (bb.hasAttr(BodyAttribute.SLEEP)) {
            hit = Math.max(SINGLE_HIT - bb.store, hit)
        }
        if (bb.hasAttr(BodyAttribute.RIKU)) {
            hit = Math.max(SINGLE_HIT - bb.store, hit)
        }
        if (effect.contains(AttackEffect.COMBO)) {
            hit = Math.max(2, hit)
        } else {
            hit = Math.max(2, Math.min(hit, DOUBLE_HIT - bb.store - 1))
        }
        if (ba.hasAttr(BodyAttribute.ATTACK_UP)) {
            hit += 2
        }
        if (bb.hasAttr(BodyAttribute.GUARD_UP)) {
            hit -= 2
        }
        return hit
    }

}
