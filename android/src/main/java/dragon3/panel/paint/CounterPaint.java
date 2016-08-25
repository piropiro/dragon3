/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import mine.paint.MineGraphics;
import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.panel.PanelWorks;

/**
 * @author saito
 */
public class CounterPaint implements DataPanelPainter {

	private Body ba;

	public CounterPaint(Body ba) {
		this.ba = ba;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		pw.drawMain(ba, g);
		pw.drawHp(ba, g);
		
		if (ba.hasAttr(BodyAttribute.SLEEP)) {
			pw.drawLine("  SLEEPING...", 0, 2, g);
		}
	}
}
