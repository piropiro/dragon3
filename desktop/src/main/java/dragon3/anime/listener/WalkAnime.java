package dragon3.anime.listener;

import dragon3.anime.AnimeWorks;
import dragon3.common.constant.Page;
import dragon3.common.util.UnitUtils;
import dragon3.map.MapWorks;
import mine.paint.MineGraphics;
import mine.paint.UnitMap;
import mine.util.Point;

public class WalkAnime implements AnimeListener {

	private static final int MAX = 8;

	MapWorks mw;
	private UnitMap map;


	private Point start, goal;
	private int img;
	private int sts;

	/*** Constructer ************************/

	public WalkAnime(MapWorks mw, UnitMap map, int x, int y) {
		this.mw = mw;
		this.map = map;
		this.start = new Point(x, y);
		img = map.getData(Page.P20, start.x, start.y);
		sts = map.getData(Page.P50, start.x, start.y);
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {

		UnitUtils unitUtils = new UnitUtils(map);
		map.setData(Page.P20, start.x, start.y, 0);
		map.setData(Page.P50, start.x, start.y, 0);
		mw.update();
		while ((goal = unitUtils.moveS(Page.P03, start.x, start.y)) != null) {
			for (int i = 1; i <= MAX; i++) {
				int x = start.x * 32 + (goal.x - start.x) * 32 * i / MAX;
				int y = start.y * 32 + (goal.y - start.y) * 32 * i / MAX;
				ac.setLocation(x, y);
				ac.repaint();
				ac.sleep(15);
			}
			start = goal;
		}
		map.setData(Page.P20, start.x, start.y, img);
		map.setData(Page.P50, start.x, start.y, sts);
		mw.update();
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		g.drawImage(map.getTile(Page.P20, img), 0, 0);
		g.drawImage(map.getTile(Page.P50, sts), 0, 0);
	}
}
