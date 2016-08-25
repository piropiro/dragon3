package dragon3.anime.listener;

import mine.util.Point;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import dragon3.anime.AnimeWorks;
import dragon3.anime.item.Arrow;

public class ArrowAnime implements AnimeListener {

	private Arrow arr;
	private int sleep;

	/*** Constructer ***********************/

	public ArrowAnime(MineImage[] image, int sleep, int startX, int startY, int goalX, int goalY) {
		this.sleep = sleep;
		arr = new Arrow(new Point(startX, startY), new Point(goalX, goalY), 16, image, false);
	}

	/*** Animation **********************************/

	public void animation(AnimeWorks ac) {
		while (!arr.end()) {
			arr.move();
			ac.setLocation(arr.getX(), arr.getY());
			ac.repaint();
			ac.sleep(sleep);
		}
		ac.sleep(sleep);
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		arr.paint(g);
	}
}
