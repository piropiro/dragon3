/*
 * Created on 2005/01/03
 */
package dragon3.panel.paint;

import mine.paint.MineGraphics;
import mine.util.Point;
import dragon3.common.constant.GameColor;
import dragon3.panel.PanelWorks;

/**
 * @author saito
 */
public interface DataPanelPainter {
	public GameColor getColor();
	public Point getPoint1();
	public Point getPoint2();
	public void paint(PanelWorks pw, MineGraphics g);
}
