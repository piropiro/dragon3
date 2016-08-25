package dragon3.anime.listener;

import java.util.ArrayList;
import java.util.List;

import dragon3.anime.AnimeWorks;
import dragon3.anime.item.Arrow;
import dragon3.common.constant.Page;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.PaintBox;
import mine.paint.UnitMap;
import mine.util.Point;

public class SomeArrowAnime implements AnimeListener {

	private int sleep;

	private Arrow[] arr;
	private PaintBox box;

	/*** Constructer ***********************/

	public SomeArrowAnime(UnitMap map, MineImage[] image, int sleep, int startX, int startY) {
		this.sleep = sleep;

		box = map.getPaintBox(Page.P41);
		
		List<Point> points = new ArrayList<Point>();
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 15; y++) {
				if (map.getData(Page.P41, x, y) != 0) {
					points.add(new Point(x * 32 - box.getX(), y * 32 - box.getY()));
				}
			}
			arr = new Arrow[points.size()];

			Point start = new Point(startX - box.getX(), startY - box.getY());
			for (int i = 0; i < arr.length; i++) {
				Point goal = points.get(i);
				arr[i] = new Arrow(start, goal, 16, image, true);
			}
		}
	}

	/*** Animation **********************************/

	public void animation(AnimeWorks ac) {
		ac.setLocation(box.getX(), box.getY());
		ac.setSize(box.getW(), box.getH());
		while (!arr[0].end()) {
			ac.repaint();
			ac.sleep(sleep);
		}
		ac.sleep(sleep);
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		for (int i = 0; i < arr.length; i++) {
			arr[i].move();
			arr[i].paint(g);
		}
	}
}