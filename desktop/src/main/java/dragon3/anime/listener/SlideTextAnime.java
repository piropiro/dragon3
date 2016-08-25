package dragon3.anime.listener;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import dragon3.anime.AnimeManager;
import dragon3.anime.AnimeWorks;
import dragon3.anime.item.Abc;

public class SlideTextAnime implements AnimeListener {

	private Abc[] abc;

	private int type;
	
	private MineImage[] text;

	/*** Constructer ***********************/

	public SlideTextAnime(int type, MineImage[] text) {
		this.type = type;
		this.text = text;
		setText(type);
	}

	/*** Text *************************/

	private void setText(int type) {
		switch (type) {
			case AnimeManager.TEXT_FINISH :
				abc = new Abc[6];
				abc[0] = new Abc(6, 1, 10);
				abc[1] = new Abc(3, 7, 8);
				abc[2] = new Abc(6, 10, 6);
				abc[3] = new Abc(3, 16, 4);
				abc[4] = new Abc(5, 19, 2);
				abc[5] = new Abc(6, 24, 0);
				break;
			case AnimeManager.TEXT_DEATH :
				abc = new Abc[5];
				abc[0] = new Abc(7, 1, 10);
				abc[1] = new Abc(6, 8, 8);
				abc[2] = new Abc(6, 14, 6);
				abc[3] = new Abc(5, 20, 2);
				abc[4] = new Abc(6, 25, 0);
				break;
		}
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {

		while (!abc[0].end1()) {
			for (int i = 0; i < abc.length; i++) {
				abc[i].move1();
			}
			ac.repaint();
			ac.sleep(25);
		}
		ac.sleep(500);
		while (!abc[0].end2()) {
			for (int i = 0; i < abc.length; i++) {
				abc[i].move2();
			}
			ac.repaint();
			ac.sleep(15);
		}
		ac.sleep(100);
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		if (abc == null)
			return;
		for (int i = 0; i < abc.length; i++) {
			int sx = abc[i].getXf();
			int sy = 0;
			int w = abc[i].getW();
			int h = 12;
			int dx = abc[i].getX();
			int dy = abc[i].getY();
			g.drawImage(text[type], sx, sy, w, h, dx, dy);
		}
	}
}
