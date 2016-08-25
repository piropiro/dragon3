package dragon3.anime.listener;

import java.util.ArrayList;
import java.util.List;

import dragon3.anime.AnimeWorks;
import dragon3.common.constant.Page;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.PaintBox;
import mine.paint.UnitMap;
import mine.util.Point;

public class AllAnime implements AnimeListener {

	private int sleep;
	private PaintBox box;
	private MineImage[] image;
	private Point[] ps;
	private int n;

	/*** Constructer ***********************/

	public AllAnime(UnitMap map, MineImage[] image, int sleep) {
		this.image = image;
		this.sleep = sleep;


		box = map.getPaintBox(Page.P41);
		List<Point> points = new ArrayList<Point>();
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 15; y++) {
				if (map.getData(Page.P41, x, y) != 0) {
					points.add(
						new Point(x * 32 - box.getX(), y * 32 - box.getY()));
				}
			}
		}
		ps = points.toArray(new Point[0]);
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {
		ac.setLocation(box.getX(), box.getY());
		ac.setSize(box.getW(), box.getH());
		for (n = 0; n < image.length; n++) {
			ac.repaint();
			ac.sleep(sleep);
		}
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		for (int i = 0; i < ps.length; i++) {
			Point p = ps[i];
			g.drawImage(image[n], p.x, p.y);
		}
	}
}
