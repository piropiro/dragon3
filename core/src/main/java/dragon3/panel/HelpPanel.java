package dragon3.panel;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dragon3.common.constant.GameColor;
import dragon3.controller.UnitWorks;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.paint.MineColor;
import mine.paint.MineGraphics;

@Singleton
public class HelpPanel implements PaintListener {

	public static final int WIDTH = 288;
	public static final int HEIGHT = 48;
	
	private PaintComponent panel;

	private String[] lines = new String[0];
	private boolean leftf;
	private boolean upf;
	private GameColor bgcolor = GameColor.BLUE;

	private UnitWorks uw;

	@Inject
	public HelpPanel(@Named("helpC") PaintComponent panel) {
		this.panel = panel;
		panel.setVisible(false);
		//panel.setFontSize(14);
		panel.setPaintListener(this);
	}

	public void setLine(GameColor bgcolor, String... lines) {
		this.bgcolor = bgcolor;
		this.lines = lines;
	}

	/*** Locate ***********************************************/

	public void setLocate(int x, int y, boolean flag) {

		boolean leftfs = leftf;
		boolean upfs = upf;
		if (flag) {
			leftf = (x > 5);
			upf = (y > 5);
		} else {
			if (leftf) {
				if (x < 4)
					leftf = false;
			} else {
				if (x > 15)
					leftf = true;
			}
			if (upf) {
				if (y < 4)
					upf = false;
			} else {
				if (y > 10)
					upf = true;
			}
		}

		if (flag || leftf != leftfs || upf != upfs) {
			int mx = (leftf) ? 16 : (20 * 32 - WIDTH - 16);
			int my = (upf) ? 8 : (15 * 32 - HEIGHT - 8);
			panel.setLocation(mx, my);
		}
	}

	/*** Paint ******************************************/

	@Override
	public void paint(MineGraphics g) {
		g.setFont("serif", 14);
		clear(bgcolor, g);

		for (int i = 0; i < lines.length; i++) {
			g.drawString(lines[i], 10, 19 + 19 * i);
		}
	}

	/*** Clear *********************************************/

	public boolean clear(GameColor color, MineGraphics g) {
		g.setColor(color.getAlphaBg());
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(MineColor.WHITE);
		g.drawRect(2, 2, WIDTH - 5, HEIGHT - 5);
		return true;
	}
	
	public void setVisible(boolean flag) {
		panel.setVisible(flag);
	}
	
	public void repaint() {
		panel.update();
		uw.repaint();
	}

	public void setUw(UnitWorks uw) {
		this.uw = uw;
	}
}
