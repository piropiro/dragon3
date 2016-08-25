package dragon3.panel.item;

import mine.paint.MineColor;
import mine.paint.MineGraphics;

public class HPBar {

	private boolean hitFlag; // Hit Flag
	private boolean deadFlag; // Dead Flag
	private boolean damageFlag; // Damaging
	private int w, ws, we;
	private int hp, hpMax, hpMin;

	private static final int bar_width = 60;

	/*** Constructer ****************************/

	public HPBar() {
		hpMax = 10;
	}

	/*** Setup ******************************/

	public void setup(boolean hit, int hp, int hpMax) {
		this.hitFlag = hit;
		this.hp = hp;
		this.hpMax = Math.max(1, hpMax);
		setMin(hp, false);
	}

	/*** Paint **************************************/

	public void paint(int x, int y, MineGraphics g) {
		g.setColor(MineColor.WHITE);
		g.drawString("" + hp, x, y);
		drawBar(g, we, w, bar_width, x + 30, y - 8);
	}

	/*** StatusBar *******************************************/

	private void drawBar(MineGraphics g, int min, int n, int max, int x, int y) {

		int width = bar_width;
		int height = 6;

		g.setColor(MineColor.WHITE);
		g.drawRect(x - 2, y - 2, width + 3, height + 3);
		g.setColor(MineColor.BLACK);
		g.drawRect(x - 1, y - 1, width + 1, height + 1);

		if (hpMin <= hp) {
			if (damageFlag)
				g.setColor(128, 128, 0);
			else if (hitFlag && deadFlag)
				g.setColor(150, 200, 255);
			else
				g.setColor(MineColor.ORANGE);
			g.fillRect(x, y, width * n / max, height);
			g.setColor(MineColor.YELLOW);
			g.fillRect(x, y, width * min / max, height);
			if (n != 0 && !damageFlag && !hitFlag) {
				g.fillRect(x + width * n / max - 2, y, 1, height);
			}
		} else {
			if (damageFlag)
				g.setColor(150, 200, 255);
			else
				g.setColor(100, 150, 200);
			g.fillRect(x, y, width * min / max, height);
			g.setColor(MineColor.YELLOW);
			g.fillRect(x, y, width * n / max, height);
		}

		g.setColor(MineColor.WHITE);
	}

	/*** Damage **********************************************/

	public void setMin(int hps, boolean dpaint) {
		this.hpMin = Math.max(0, Math.min(hps, hpMax));
		this.damageFlag = dpaint;
		deadFlag = (hps < 0);
		w = bar_width * hp / hpMax;
		we = bar_width * hpMin / hpMax;
		ws = bar_width * hp / hpMax;
	}

	/*** Henka **************************************************/

	public boolean henka() {
		if (w == we)
			return false;
		if (w > we) {
			w--;
			hp = Math.max(hpMin, Math.min(w * hpMax / bar_width, hp));
		} else {
			w++;
			hp = Math.max(hp, Math.min(w * hpMax / bar_width, hpMin));
		}
		return true;
	}

	/*** Sleep Time ****************************************/

	public int getSleepTime() {
		return 400 / Math.max(1, Math.abs(ws - we));
	}
}
