/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import dragon3.Statics;
import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.data.Data;
import dragon3.panel.PanelWorks;
import mine.paint.MineGraphics;
import mine.util.Point;

/**
 * @author saito
 */
public class WazaListPaint implements DataPanelPainter {

	private Statics statics;
	
	private Body ba;

	public WazaListPaint(Statics statics, Body ba) {
		this.statics = statics;
		this.ba = ba;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		pw.drawMain(ba, g);
		int n = 0;

		for (String wazaId : ba.getWazaList()) {
			if (wazaId == null) { 
				continue;
			}
			Data waza = statics.getWazaData(wazaId);
			pw.drawLine(waza.getName(), 0, n++, g);
			if (n == 4)
				break;
		}
	}
	
	@Override
	public GameColor getColor() {
		return ba.getColor();
	}

	@Override
	public Point getPoint1() {
		return new Point(ba.getX(), ba.getY());
	}

	@Override
	public Point getPoint2() {
		return getPoint1();
	}
}
