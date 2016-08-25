package dragon3.panel.item;

import mine.paint.MineColor;
import mine.paint.MineGraphics;

public class EXPBar {

	private int exp, expMax;

	private static final int bar_width = 60;

	/*** Constructer ****************************/

	public EXPBar() {
		expMax = 100;
	}

	/*** Setup ******************************/

	public void setup(int exp, int expMax) {
		this.exp = exp;
		this.expMax = expMax;
	}

	/*** Paint **************************************/

	public void paint(int x, int y, MineGraphics g) {
		g.setColor(MineColor.WHITE);
		if (exp >= expMax) {
			g.drawString("" + exp, x-4, y);
		} else {
			g.drawString("" + exp, x, y);
		}
		
		drawBar(g, bar_width * exp / expMax, bar_width, x + 30, y - 8);
	}

	/*** StatusBar *******************************************/

	private void drawBar(MineGraphics g, int n, int max, int x, int y) {

		int width = bar_width;
		int height = 6;

		g.setColor(MineColor.WHITE);
		g.drawRect(x - 2, y - 2, width + 3, height + 3);
		g.setColor(MineColor.BLACK);
		g.drawRect(x - 1, y - 1, width + 1, height + 1);

		if (n >= max) {
			g.setColor(MineColor.AQUA);
			g.fillRect(x, y, max, height);
		} else {
			g.setColor(MineColor.LIME);
			g.fillRect(x, y, n, height);
		}


		g.setColor(MineColor.WHITE);
	}
}
