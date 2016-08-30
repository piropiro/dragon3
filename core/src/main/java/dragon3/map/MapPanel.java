package dragon3.map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dragon3.common.constant.Page;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.paint.MineGraphics;
import mine.util.Point;

@Singleton
public class MapPanel implements MapWorks, PaintListener {

	private PaintComponent panel;
	
	private int wx, wy, wxs, wys;


	@Inject StageMap map;

	/*** Constructer *****************************************************/

	@Inject
	public MapPanel(@Named("mapC") PaintComponent panel) {
		super();
		System.out.println("mappanel");
		this.panel = panel;
		panel.setPaintListener(this);
	}

	/*** Get and Set Data ************************************************/
	@Override
	public Point getWaku() {
		return new Point(wx, wy);
	}

	/*** Waku **************************************************************/
//	@Override
//	public void wakuMove(int x, int y) {
//		this.wx = x;
//		this.wy = y;
//		Body b = map.search(wx, wy);
//		if (b != null) {
//			el.setSelectBody(b);
//		} else {
//			el.setSelectPlace(x, y);
//		}
//		//uw.getPanelManager().setHelpLocation(wx, wy);
//	}
	@Override
	public void wakuPaint(int wx, int wy, boolean flag) {
		this.wx = wx;
		this.wy = wy;
		map.getMap().setData(Page.P40, wxs, wys, 0);
		map.getMap().setData(Page.P40, wx, wy, 1);
		if (flag) {
			int x = Math.min(wx, wxs) * 32;
			int y = Math.min(wy, wys) * 32;
			int xs = Math.abs(wx - wxs) * 32 + 32;
			int ys = Math.abs(wy - wys) * 32 + 32;
			panel.update();
			//uw.repaint(x, y, xs, ys);
		}
		wxs = wx;
		wys = wy;
	}

	/*** Paint ************************************************/

	@Override
	public void ppaint(int px, int py) {
		panel.update();
		//uw.repaint(px * 32, py * 32, 32, 32);
	}
	@Override
	public void ppaint(int[] box) {
		panel.update();
		//uw.repaint(box[0] * 32, box[1] * 32, box[2] * 32, box[3] * 32);
	}
	@Override
	public void repaint() {
		panel.update();
		//uw.repaint();
	}
	@Override
	public void update() {
		panel.update();
	}
	
	/*** Paint *****************************************************/

	@Override
	public void paint(MineGraphics g) {
		map.getMap().draw(g);
	}

}
