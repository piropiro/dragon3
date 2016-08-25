/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;


import mine.paint.MineGraphics;
import dragon3.Statics;
import dragon3.common.Body;
import dragon3.data.Data;
import dragon3.panel.PanelWorks;

/**
 * @author saito
 */
public class WazaListPaint implements DataPanelPainter {

	private Body ba;

	public WazaListPaint(Body ba) {
		this.ba = ba;
	}

	public void paint(PanelWorks pw, MineGraphics g) {
		pw.drawMain(ba, g);
		int n = 0;

		for (String wazaId : ba.getWazaList()) {
			if (wazaId == null) { 
				continue;
			}
			Data waza = Statics.wazaList.getData(wazaId);
			pw.drawLine(waza.getName(), 0, n++, g);
			if (n == 4)
				break;
		}
	}
}
