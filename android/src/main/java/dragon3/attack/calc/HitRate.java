/*
 * 作成日: 2004/03/23
 */
package dragon3.attack.calc;

import java.util.Set;

import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.BodyAttribute;
import mine.MineUtils;

/**
 * @author k-saito
 */
public class HitRate {

	public static final int SINGLE_HIT = 10;
	public static final int DOUBLE_HIT = SINGLE_HIT * 2;

	public static int calcPredict(Body ba, Body bb, Set<AttackEffect> effect) {
		return calc(ba, bb, effect, 0);
	}

	public static int calcReal(Body ba, Body bb, Set<AttackEffect> effect) {
//		return calc(ba, bb, effect, Luck.rnd(1, ba));
		return calc(ba, bb, effect, 0);
	}

	private static int calc(Body ba, Body bb, Set<AttackEffect> effect, int rnd) {
		if (bb == null)
			return 0;
		if (ba.getHit() == 0)
			return 0;

		if (effect.contains(AttackEffect.HICHU))
			return SINGLE_HIT;

		int hit = DOUBLE_HIT - bb.getMis() * DOUBLE_HIT / ba.getHit();

		hit += rnd;

		if (effect.contains(AttackEffect.HIT_12))
			hit += 12;
		if (effect.contains(AttackEffect.HIT_4))
			hit += 4;
		if (effect.contains(AttackEffect.MISS_4))
			hit -= 4;

		if (bb.hasAttr(BodyAttribute.SLEEP)) {
			hit = Math.max(SINGLE_HIT - bb.getStore(), hit);
		}
		if (bb.hasAttr(BodyAttribute.RIKU)) {
			hit = Math.max(SINGLE_HIT - bb.getStore(), hit);
		}
		if (effect.contains(AttackEffect.COMBO)) {
			hit = Math.max(2, hit);
		} else {
			hit = MineUtils.mid(2, hit, DOUBLE_HIT - bb.getStore() - 1);
		}
		if (ba.hasAttr(BodyAttribute.ATTACK_UP)) {
			hit += 2;
		}
		if (bb.hasAttr(BodyAttribute.GUARD_UP)) {
			hit -= 2;
		}
		return hit;
	}

}
