/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.camp.Equip;
import dragon3.common.constant.Texts;
import dragon3.panel.PanelWorks;
import dragon3.save.SaveData;
import mine.paint.MineGraphics;

/**
 * @author saito
 */
public class Score2Paint implements DataPanelPainter {

	private SaveData sd;

	public Score2Paint(Equip equip, SaveData sd) {
		this.sd = sd;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		pw.drawLine(Texts.sp[28] + sd.getTurn(), 0, -1, g);
		pw.drawLine(Texts.sp[29] + sd.getEscape(), 0, 0, g);
		pw.drawLine(Texts.sp[30] + sd.getDead(), 0, 1, g);

	}
}
