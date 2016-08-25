package dragon3.anime.listener;

import mine.util.Point;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import dragon3.anime.AnimeWorks;
import dragon3.anime.item.Arrow;

public class RotateAnime implements AnimeListener {

	private int sleep;
	private Arrow arr;
	private int startX;
	private int startY;
	private double r;

	/*** Constructer ****************************/

	public RotateAnime(MineImage[] image, int sleep, int startX, int startY, int goalX, int goalY) {
		this.sleep = sleep;
		this.startX = startX;
		this.startY = startY;

		r = Math.sqrt((startX - goalX) * (startX - goalX) + (startY - goalY) * (startY - goalY));
		arr = new Arrow(new Point(startX, startY), new Point(goalX, goalY), 32, image, false);
		arr.setTheta(Math.atan2(goalY - startY, goalX - startX));
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {
		while (!arr.end()) {
			arr.move();
			arr.addTheta(Math.PI * 2 / 36);
			int x = startX + (int) (r * Math.cos(arr.getTheta()));
			int y = startY + (int) (r * Math.sin(arr.getTheta()));
			ac.setLocation(x, y);
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
