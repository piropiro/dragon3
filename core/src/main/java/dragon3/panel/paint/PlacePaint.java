/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.common.util.MoveUtils;
import dragon3.panel.PanelWorks;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.util.Point;

/**
 * @author saito
 */
public class PlacePaint implements DataPanelPainter {

	private MineImage[] back;
	private int tikei;
	private Point location;
	private GameColor color;
	
	public PlacePaint(int tikei, MineImage[] back, Point location) {
		this.tikei = tikei;
		this.back = back;
		this.location = location;
		this.color = GameColor.GREEN;
	}
	

	@Override
	public void paint(PanelWorks pw, MineGraphics g) {
		g.drawImage(back[tikei], 10, 10);
		switch (tikei) {
			case MoveUtils.C_BLUE :
				pw.drawText(Texts.sp[12], g);
				break;
			case MoveUtils.C_RED :
				pw.drawText(Texts.sp[13], g);
				break;
			case MoveUtils.S_BLUE :
				pw.drawText(Texts.sp[14], g);
				break;
			case MoveUtils.YELLOW :
				pw.drawText(Texts.sp[15], g);
				break;
			case MoveUtils.GREEN :
				pw.drawText(Texts.sp[16], g);
				break;
			case MoveUtils.AQUA :
				pw.drawText(Texts.sp[17], g);
				break;
			case MoveUtils.BLUE :
				pw.drawText(Texts.sp[18], g);
				break;
			case MoveUtils.BLACK :
				pw.drawText(Texts.sp[19], g);
				break;
			case MoveUtils.ICE :
				pw.drawText(Texts.sp[20], g);
				break;
			case MoveUtils.POISONP :
				pw.drawText(Texts.sp[21], g);
				break;
			case MoveUtils.OILP :
				pw.drawText(Texts.sp[22], g);
				break;
			case MoveUtils.FIREP :
				pw.drawText(Texts.sp[23], g);
				break;
			case MoveUtils.SKYP :
				pw.drawText(Texts.sp[84], g);
				break;
		}
	}
	
	@Override
	public Point getPoint1() {
		return location;
	}

	@Override
	public Point getPoint2() {
		return location;
	}

	@Override
	public GameColor getColor() {
		return color;
	}
}


