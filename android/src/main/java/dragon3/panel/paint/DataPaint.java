/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.common.constant.Texts;
import dragon3.panel.PanelWorks;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

/**
 * @author saito
 */
public class DataPaint implements DataPanelPainter {

	private int turn;
	private int treasureLimit;
	private String treasureCount;
	private MineImage cBlueImage;

	public DataPaint(int turn, int treasureLimit, String treasureCount, MineImage cBlueImage) {
		this.turn = turn;
		this.treasureLimit = treasureLimit;
		this.treasureCount = treasureCount;
		this.cBlueImage = cBlueImage;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		g.drawImage(cBlueImage, 10, 10);
		g.drawString(Texts.sp[60], 50, 32);
		pw.drawLine(
			Texts.sp[61] + turn + " / " + treasureLimit,
			0,
			0,
			g);
		pw.drawLine(Texts.sp[63] + treasureCount, 0, 2, g);
		switch (turn % 3) {
			case 0 :
				pw.drawLine(Texts.sp[64], 0, 3, g);
				break;
			case 1 :
				pw.drawLine(Texts.sp[65], 0, 3, g);
				break;
			case 2 :
				pw.drawLine(Texts.sp[66], 0, 3, g);
				break;
		}
	}
}
