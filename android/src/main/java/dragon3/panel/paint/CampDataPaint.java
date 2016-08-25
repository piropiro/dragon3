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
public class CampDataPaint implements DataPanelPainter {

	public static final int C_CHARA1 = 0;
	public static final int C_CHARA2 = 1;
	public static final int C_CLASS = 2;
	public static final int C_WEPON = 3;
	public static final int C_ARMOR = 4;
	public static final int C_ITEM = 5;
	public static final int C_SOUL = 6;
	public static final int C_DUST = 7;
	public static final int C_DITEM1 = 8;
	public static final int C_DITEM2 = 9;
	public static final int C_DWEPON = 10;
	public static final int C_DARMOR = 11;

	private int tikei;
	
	private MineImage whiteBack;
	private MineImage[][] waku;

	public CampDataPaint(int tikei, MineImage whiteBack, MineImage[][] waku) {
		this.tikei = tikei;
		this.whiteBack = whiteBack;
		this.waku = waku;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		g.drawImage(whiteBack, 10, 10);
		switch (tikei) {
			case C_CHARA1 :
				g.drawImage(waku[1][1], 10, 10);
				pw.drawText(Texts.sp[0], g);
				break;
			case C_CHARA2 :
				g.drawImage(waku[1][1], 10, 10);
				pw.drawText(Texts.sp[1], g);
				break;
			case C_CLASS :
				g.drawImage(waku[1][1], 10, 10);
				pw.drawText(Texts.sp[2], g);
				break;
			case C_WEPON :
				g.drawImage(waku[1][4], 10, 10);
				pw.drawText(Texts.sp[3], g);
				break;
			case C_ARMOR :
				g.drawImage(waku[1][4], 10, 10);
				pw.drawText(Texts.sp[4], g);
				break;
			case C_ITEM :
				g.drawImage(waku[1][4], 10, 10);
				pw.drawText(Texts.sp[5], g);
				break;
			case C_DUST :
				g.drawImage(waku[1][3], 10, 10);
				pw.drawText(Texts.sp[6], g);
				break;
			case C_SOUL :
				g.drawImage(waku[1][4], 10, 10);
				pw.drawText(Texts.sp[7], g);
				break;
			case C_DITEM1 :
				g.drawImage(waku[1][4], 10, 10);
				pw.drawText(Texts.sp[8], g);
				break;
			case C_DITEM2 :
				g.drawImage(waku[1][4], 10, 10);
				pw.drawText(Texts.sp[9], g);
				break;
			case C_DWEPON :
				g.drawImage(waku[1][4], 10, 10);
				pw.drawText(Texts.sp[10], g);
				break;
			case C_DARMOR :
				g.drawImage(waku[1][4], 10, 10);
				pw.drawText(Texts.sp[11], g);
				break;
		}
	}
}
