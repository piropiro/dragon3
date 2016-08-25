/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import mine.paint.MineGraphics;
import dragon3.attack.Attack;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.Texts;
import dragon3.common.constant.BodyAttribute;
import dragon3.panel.PanelWorks;

/**
 * @author saito
 */
public class AttackPaint implements DataPanelPainter {

	private Body bb;
	private Attack attack;

	public AttackPaint(Body bb, Attack attack) {
		this.bb = bb;
		this.attack = attack;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		if (attack == null) {
			return;
		}
		pw.drawMain(attack.getAttacker(), g);
		pw.drawHp(attack.getAttacker(), g);
		
		pw.drawLine(attack.getName(), 0, 1, g);
		if (attack.hasEffect(AttackEffect.NO_ATTACK)) {
			paintEffect(pw, g);
		} else {
			pw.drawLine(Texts.sp[55], Math.abs(attack.getDamage()), 0, 2, g);
			double rate = (double) attack.getRate();
			pw.drawLine(Texts.sp[56] + rate / 100, 1, 2, g);
		}
		int meichu = attack.getMeichu();
		int store = attack.getStore();

		pw.drawLine(Texts.sp[57] + meichu, 0, 3, g);
		pw.drawLine(Texts.sp[58] + store, 1, 3, g);
	}
	
	private void paintEffect(PanelWorks pw, MineGraphics g) {
		String s = "NO EFFECT";
		if (attack.isEffective(AttackEffect.REFRESH))
			s = "REFRESH";
		if (attack.isEffective(AttackEffect.OIL))
			s = "OIL";
		if (attack.isEffective(AttackEffect.ATTACK_UP))
			s = "ATTACK";
		if (attack.isEffective(AttackEffect.GUARD_UP))
			s = "GUARD";
		if (attack.isEffective(AttackEffect.UPPER))
			s = "UP";
		if (attack.isEffective(AttackEffect.CHOP))
			s = "DOWN";
		if (attack.isEffective(AttackEffect.WET))
			s = "CLOSE";
		if (attack.isEffective(AttackEffect.POISON))
			s = "POISON";
		if (attack.isEffective(AttackEffect.SLEEP))
			s = "SLEEP";
		if (attack.isEffective(AttackEffect.CHARM))
			s = "CHARM";
		if (attack.isEffective(AttackEffect.CRITICAL))
			s = "FINISH";
		if (attack.isEffective(AttackEffect.DEATH))
			s = "DEATH";
		if (s.equals("SLEEP")) {
			if (bb.hasAttr(BodyAttribute.SLEEP_LOCK)) {
				s = "SLEEP_LOCK";
			}
		}
		if (s.equals("CHARM")) {
			if (bb.hasAttr(BodyAttribute.CHARM_LOCK)) {
				s = "CHARM_LOCK";
			}
		}
		pw.drawLine(Texts.sp[59] + s, 0, 2, g);
	}
}
