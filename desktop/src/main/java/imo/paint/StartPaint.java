package imo.paint;



import imo.MainWorks;
import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.thread.Engine;


public class StartPaint implements PaintListener {

	private MainWorks mw;
	private int count;

	public StartPaint(MainWorks mw) {
		this.mw = mw;
		count = 0;
	}

	public void paint(MineGraphics g) {
		g.setColor(MineColor.BLACK);
		g.drawString("操作方法", 200, 240);
		g.drawString("攻撃 : Z", 200, 260);
		g.drawString("移動 : ↑←↓→", 200, 280);


		if (count / 12 % 2 == 0) {
			g.setColor(MineColor.BLACK);
		} else {
			g.setColor(180, 240, 180);
		}

		g.drawString("press  Z  to start", 100, 140);
	}

	public void run() {
		Engine.Companion.sleep(30);
		mw.repaint();
		count++;
	}

	public void keyReleased(char character, int keyCode) {
	}
	public void keyPressed(char character, int keyCode) {
		switch (character) {
			case 'z' :
				mw.gameStart();
				break;
		}
	}

}
