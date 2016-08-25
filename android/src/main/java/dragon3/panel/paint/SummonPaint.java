/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import mine.paint.MineGraphics;
import mine.paint.MineImage;
import dragon3.common.constant.Texts;
import dragon3.panel.PanelWorks;

/**
 * @author saito
 */
public class SummonPaint implements DataPanelPainter {

	private int turn;
	private int limit;
	private int tikei;
	private MineImage[] stageObj;

	public SummonPaint(int turn, int limit, int tikei, MineImage[] stageObj) {
		this.turn = turn;
		this.limit = limit;
		this.tikei = tikei;
		this.stageObj = stageObj;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		g.drawImage(stageObj[tikei], 10, 10);
		if (limit == 64) {
			pw.drawText(Texts.sp[76], g);

		} else if (limit < turn) {
			g.drawString(Texts.sp[77], 50, 32);
			pw.drawLine(Texts.sp[78] + turn + " / " + limit, 0, 0, g);
			pw.drawLine(Texts.sp[79], 0, 2, g);
		} else {
			g.drawString(Texts.sp[80], 50, 32);
			pw.drawLine(Texts.sp[81] + turn + " / " + limit, 0, 0, g);
			pw.drawLine(Texts.sp[82], 0, 2, g);
			pw.drawLine(Texts.sp[83], 0, 3, g);
		}
	}
}
