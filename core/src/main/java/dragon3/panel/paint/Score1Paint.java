/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.panel.PanelWorks;
import dragon3.save.SaveData;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.util.Point;

/**
 * @author saito
 */
public class Score1Paint implements DataPanelPainter {

	private SaveData sd;
	private MineImage cBlueImage;
	private Point location;
	private GameColor color;

	public Score1Paint(SaveData sd, MineImage cBlueImage) {
		this.sd = sd;
		this.cBlueImage = cBlueImage;
		this.location = new Point(2, 1);
		this.color = GameColor.BLUE;
	}
	
	
	@Override
	public void paint(PanelWorks pw, MineGraphics g) {
		g.drawImage(cBlueImage, 10, 10);
		g.drawString(sd.getPlayerName(), 50, 32);
		pw.drawLine(Texts.sp[24] + sd.sumStars(), 0, 0, g);
		pw.drawLine(Texts.sp[25] + sd.getItem(), 0, 1, g);
		pw.drawLine(Texts.sp[26] + sd.getKill(), 0, 2, g);
		long time = sd.getPlayTime();
		long hour = time / 3600000;
		long min = time % 3600000 / 60000;
		long sec = time % 60000 / 1000;
		String times = "";
		times += (hour > 9) ? ("" + hour) : ("0" + hour);
		times += ":";
		times += (min > 9) ? ("" + min) : ("0" + min);
		times += ":";
		times += (sec > 9) ? ("" + sec) : ("0" + sec);
		pw.drawLine(Texts.sp[27] + times, 0, 3, g);

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

