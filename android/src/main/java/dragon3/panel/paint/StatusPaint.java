/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import mine.paint.MineGraphics;
import dragon3.common.Body;
import dragon3.common.constant.Texts;
import dragon3.panel.PanelWorks;

/**
 * @author saito
 */
public class StatusPaint implements DataPanelPainter {

	private Body ba;

	public StatusPaint(Body ba) {
		this.ba = ba;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		pw.drawMain(ba, g);
		pw.drawHp(ba, g);

		pw.drawLine(Texts.sp[49], ba.getStr(), 0, 1, g);
		pw.drawLine(Texts.sp[50], ba.getDef(), 1, 1, g);
		pw.drawLine(Texts.sp[51], ba.getMst(), 0, 2, g);
		pw.drawLine(Texts.sp[52], ba.getMdf(), 1, 2, g);
		pw.drawLine(Texts.sp[53], ba.getHit(), 0, 3, g);
		pw.drawLine(Texts.sp[54], ba.getMis(), 1, 3, g);
	}
}
