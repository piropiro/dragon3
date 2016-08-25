/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import java.util.HashSet;
import java.util.Set;

import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.Texts;
import dragon3.data.WazaData;
import dragon3.panel.PanelWorks;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

/**
 * @author saito
 */
public class WazaPaint implements DataPanelPainter {

	private WazaData waza;
	private int n;
	private MineImage backImage;


	public WazaPaint(WazaData waza, MineImage backImage) {
		this.waza = waza;
		this.backImage = backImage;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		g.drawImage(backImage, 10, 10);

		//g.drawImage(bodyImageList.getImage(ba.getImage()), 10, 10);
		g.drawString(
			waza.getName().substring(0, Math.min(7, waza.getName().length())),
			50,
			22);


		if (waza.getDamageType() != null) {
			pw.drawLine(waza.getDamageType().getText(), 0, 0, g);
		} else {
			pw.drawLine(Texts.sp[33], 0, 0, g);
		}

		pw.drawLine(waza.getTargetType().getText(), 1, 0, g);

		Set<AttackEffect> ect = new HashSet<>(waza.getEffect());

		int hit = 0;
		if (ect.contains(AttackEffect.MISS_4));
			hit -= 4;
		if (ect.contains(AttackEffect.HIT_4))
			hit += 4;
		if (ect.contains(AttackEffect.HIT_12))
			hit += 12;
		if (ect.contains(AttackEffect.HICHU))
			hit = 32;
		switch (hit) {
			case -4 :
				pw.drawLine(Texts.sp[34], 0, 1, g);
				break;
			case 4 :
				pw.drawLine(Texts.sp[35], 0, 1, g);
				break;
			case 12 :
				pw.drawLine(Texts.sp[36], 0, 1, g);
				break;
			case 16 :
				pw.drawLine(Texts.sp[37], 0, 1, g);
				break;
			case 32 :
				pw.drawLine(Texts.sp[38], 0, 1, g);
				break;
		}
		
		pw.drawLine(waza.getEnergyType().getText(), 1, 1, g);

		n = 4;
		drawWazaEffect(pw, g, ect, AttackEffect.DAMAGE_150);
		drawWazaEffect(pw, g, ect, AttackEffect.DAMAGE_200);
		drawWazaEffect(pw, g, ect, AttackEffect.DAMAGE_300);
		drawWazaEffect(pw, g, ect, AttackEffect.TAME);
		drawWazaEffect(pw, g, ect, AttackEffect.COUNTER_ONLY);

		drawWazaEffect(pw, g, ect, AttackEffect.RIKU_0);
		drawWazaEffect(pw, g, ect, AttackEffect.RIKU_150);
		drawWazaEffect(pw, g, ect, AttackEffect.MIZU_0);
		drawWazaEffect(pw, g, ect, AttackEffect.MIZU_100);
		drawWazaEffect(pw, g, ect, AttackEffect.MIZU_200);

		drawWazaEffect(pw, g, ect, AttackEffect.FIRE);
		drawWazaEffect(pw, g, ect, AttackEffect.ICE);
		drawWazaEffect(pw, g, ect, AttackEffect.THUNDER);
		drawWazaEffect(pw, g, ect, AttackEffect.SORA_200);
		drawWazaEffect(pw, g, ect, AttackEffect.DRAGON_200);
		drawWazaEffect(pw, g, ect, AttackEffect.UNDEAD_200);

		drawWazaEffect(pw, g, ect, AttackEffect.UPPER);
		drawWazaEffect(pw, g, ect, AttackEffect.CHOP);
		drawWazaEffect(pw, g, ect, AttackEffect.CRITICAL);
		drawWazaEffect(pw, g, ect, AttackEffect.DEATH);
		drawWazaEffect(pw, g, ect, AttackEffect.SLEEP);
		drawWazaEffect(pw, g, ect, AttackEffect.POISON);
		drawWazaEffect(pw, g, ect, AttackEffect.WET);
		drawWazaEffect(pw, g, ect, AttackEffect.CHARM);
		drawWazaEffect(pw, g, ect, AttackEffect.ATTACK_UP);
		drawWazaEffect(pw, g, ect, AttackEffect.GUARD_UP);
		drawWazaEffect(pw, g, ect, AttackEffect.REFRESH);
		drawWazaEffect(pw, g, ect, AttackEffect.HEAL);
		drawWazaEffect(pw, g, ect, AttackEffect.OIL);
	}

	private boolean drawWazaEffect(PanelWorks pw, MineGraphics g, Set<AttackEffect> effectSet, AttackEffect effect) {
		if (n == 8)
			return false;
		if (!effectSet.contains(effect))
			return false;
		pw.drawLine(effect.getText(), n % 2, n / 2, g);
		n++;
		return true;
	}
}
