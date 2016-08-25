package imo.paint;

import java.awt.event.KeyEvent;

import imo.ImoEventListener;
import imo.ImoWorks;
import imo.common.ImageList;
import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.thread.Engine;

public class EndImoEvent implements ImoEventListener {

	private int count;

	private ImoWorks mw;

	private ImageList imageList;

	/**
	 * Constructer
	 */
	public EndImoEvent(ImoWorks mw, ImageList imageList) {
		this.mw = mw;
		this.imageList = imageList;
		count = 0;
	}

	public void paint(MineGraphics g) {
		int size = Math.min(300, count * 6);

		g.drawImage(imageList.getEndImage(), 0, 0, 300, size, 0, 0);

		if (count / 12 % 2 == 0) {
			g.setColor(MineColor.BLACK);
			g.drawString("press  C  to close", 20, 280);
		}
	}

	public void keyReleased(char character, int keyCode) {
	}
	public void keyPressed(char character, int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_ESCAPE :
				mw.gameStart();
				break;
		}

		switch (character) {
			case 'c' :
				mw.gameExit();
				break;
		}
	}

	public void run() {
		Engine.Companion.sleep(30);
		mw.repaint();
		count++;
	}
}
