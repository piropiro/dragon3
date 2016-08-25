/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.common.Body;
import dragon3.common.constant.Texts;
import dragon3.panel.PanelWorks;
import mine.paint.MineGraphics;

/**
 * @author saito
 */
public class AnalyzePaint implements DataPanelPainter {

	private Body ba;

	public AnalyzePaint(Body ba) {
		this.ba = ba;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		pw.drawMain(ba, g);
		pw.drawExp(ba, g);
		pw.drawLine(ba.base.getMoveType().getText(), ba.getMoveStep(), 0, 1, g);
		pw.drawLine(Texts.sp[45], ba.getStore(), 1, 1, g);
		pw.drawLine(Texts.sp[46], ba.getRange(), 0, 2, g);
		pw.drawLine(Texts.sp[47], ba.getScope(), 1, 2, g);
		pw.drawLine(Texts.sp[48], ba.getLimitTurn(), 0, 3, g);
		//pw.drawLine("EXP ", ba.getExp(), 1, 3, g);

	}
}
