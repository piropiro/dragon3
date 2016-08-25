/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.camp.Equip;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.panel.PanelWorks;
import dragon3.save.SaveData;
import lombok.Getter;
import mine.paint.MineGraphics;
import mine.util.Point;

/**
 * @author saito
 */
public class Score2Paint implements DataPanelPainter {

	private SaveData sd;
	@Getter private Point location;
	@Getter private GameColor color;

	public Score2Paint(Equip equip, SaveData sd) {
		this.sd = sd;
		this.location = new Point(3, 1);
		this.color = GameColor.BLUE;
	}
	
	public void paint(PanelWorks pw, MineGraphics g) {
		pw.drawLine(Texts.sp[28] + sd.getTurn(), 0, -1, g);
		pw.drawLine(Texts.sp[29] + sd.getEscape(), 0, 0, g);
		pw.drawLine(Texts.sp[30] + sd.getDead(), 0, 1, g);

	}
	
	@Override
	public Point getPoint1() {
		return location;
	}

	@Override
	public Point getPoint2() {
		return location;
	}
}
