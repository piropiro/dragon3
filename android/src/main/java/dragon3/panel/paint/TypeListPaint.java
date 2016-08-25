/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.panel.PanelWorks;
import mine.paint.MineGraphics;

/**
 * @author saito
 */
public class TypeListPaint implements DataPanelPainter {

	private Body ba;
	private int n;

	public TypeListPaint(Body ba) {
		this.ba = ba;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		pw.drawMain(ba, g);
		n = 0;
		drawType(pw, g, BodyAttribute.DRAGON);
		drawType(pw, g, BodyAttribute.UNDEAD);
		drawType(pw, g, BodyAttribute.DRAGON_KILLER);
		drawType(pw, g, BodyAttribute.UNDEAD_KILLER);
		drawType(pw, g, BodyAttribute.MAGIC_50);

		drawType(pw, g, BodyAttribute.FIRE_200, BodyAttribute.FIRE_50, BodyAttribute.FIRE_0);
		drawType(pw, g, BodyAttribute.ICE_200, BodyAttribute.ICE_50, BodyAttribute.ICE_0);
		drawType(pw, g, BodyAttribute.THUNDER_200, BodyAttribute.THUNDER_50, BodyAttribute.THUNDER_0);

		if (!drawType(pw, g, BodyAttribute.ANTI_ALL)) {
			drawType(pw, g, BodyAttribute.ANTI_CRITICAL);
			drawType(pw, g, BodyAttribute.ANTI_DEATH);

			drawType(pw, g, BodyAttribute.ANTI_SLEEP);
			drawType(pw, g, BodyAttribute.ANTI_POISON);
			drawType(pw, g, BodyAttribute.ANTI_CHARM);
			drawType(pw, g, BodyAttribute.POISON);
			drawType(pw, g, BodyAttribute.REGENE);
		}

		drawType(pw, g, BodyAttribute.FLY_ABLE);
		drawType(pw, g, BodyAttribute.SWIM_ABLE);
		drawType(pw, g, BodyAttribute.LITE_WALK);

		int ido = 0;
		if (ba.hasAttr(BodyAttribute.MOVE_UP_1))
			ido++;
		if (ba.hasAttr(BodyAttribute.MOVE_UP_2))
			ido += 2;
		if (ba.hasAttr(BodyAttribute.MOVE_DOWN_1))
			ido--;
		switch (ido) {
			case -1 :
				drawType(pw, g, BodyAttribute.MOVE_DOWN_1);
				break;
			case 1 :
				drawType(pw, g, BodyAttribute.MOVE_UP_1);
				break;
			case 2 :
				drawType(pw, g, BodyAttribute.MOVE_UP_2);
				break;
		}

		drawType(pw, g, BodyAttribute.SLEEP_LOCK);
		drawType(pw, g, BodyAttribute.CHARM_LOCK);
	}

	private boolean drawType(PanelWorks pw, MineGraphics g, BodyAttribute t2, BodyAttribute th, BodyAttribute t0) {
		if (drawType(pw, g, t0))
			return true;
		if (ba.hasAttr(t2) && ba.hasAttr(th))
			return false;
		drawType(pw, g, t2);
		drawType(pw, g, th);
		return true;
	}

	private boolean drawType(PanelWorks pw, MineGraphics g, BodyAttribute type) {
		if (n == 8)
			return false;
		if (!ba.hasAttr(type))
			return false;
		pw.drawLine(type.getText(), n / 4, n % 4, g);
		n++;
		return true;
	}
}
