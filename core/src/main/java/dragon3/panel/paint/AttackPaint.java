/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.attack.Attack;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.panel.PanelWorks;
import lombok.Getter;
import mine.paint.MineGraphics;
import mine.util.Point;

/**
 * @author saito
 */
public class AttackPaint implements DataPanelPainter {

	private Body ba;
	private Body bb;
	private Attack attack;
	@Getter private GameColor color;

	public AttackPaint(Attack attack) {
		ba = attack.getAttacker();
		bb = attack.getReceiver();
		this.attack = attack;
		this.color = bb.getColor();
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

	@Override
	public Point getPoint1() {
		return new Point(ba.getX(), ba.getY());
	}

	@Override
	public Point getPoint2() {
		return new Point(bb.getX(), bb.getY());
	}
}
