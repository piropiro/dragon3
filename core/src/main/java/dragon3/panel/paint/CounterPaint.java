/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.attack.Attack;
import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.panel.PanelWorks;
import lombok.Getter;
import mine.paint.MineGraphics;
import mine.util.Point;

/**
 * @author saito
 */
public class CounterPaint implements DataPanelPainter {

	private Body ba;
	private Body bb;
	@Getter private Point location;
	@Getter private GameColor color;
	
	public CounterPaint(Attack counter) {
		this.ba = counter.getAttacker();
		this.bb = counter.getReceiver();
		this.color = ba.getColor();
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		pw.drawMain(ba, g);
		pw.drawHp(ba, g);
		
		if (ba.hasAttr(BodyAttribute.SLEEP)) {
			pw.drawLine("  SLEEPING...", 0, 2, g);
		}
	}
	
	@Override
	public Point getPoint1() {
		return new Point(ba.getX(), ba.getY());
	}

	@Override
	public Point getPoint2() {
		return new Point(bb.getX(), bb.getY());
	}

}
