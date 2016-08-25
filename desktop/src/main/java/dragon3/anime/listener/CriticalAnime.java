package dragon3.anime.listener;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.UnitMap;
import dragon3.anime.AnimeWorks;
import dragon3.common.constant.Page;
import dragon3.map.MapWorks;

public class CriticalAnime implements AnimeListener {

	private MapWorks mw;
	private UnitMap map;
	private int bodyX;
	private int bodyY;
	private MineImage img;

	private int count;

	private static final int MAX = 8;

	/*** Constructer ***********************/

	public CriticalAnime(MapWorks mw, UnitMap map, int bodyX, int bodyY) {
		this.mw = mw;
		this.map = map;
		this.bodyX = bodyX;
		this.bodyY = bodyY;
		this.img = map.getTile(Page.P20, map.getData(Page.P20, bodyX, bodyY));
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {
		map.setData(Page.P20, bodyX, bodyY, 0);
		mw.update();
		for (count = 0; count <= MAX; count++) {
			ac.repaint();
			ac.sleep(50);
		}
	}

	/*** Paint **********************************/

	public void paint(MineGraphics g) {
		g.setAlpha(1.0 * (MAX - count) / MAX);
		halfPaint(g, 32 + count * 4, 0, true);
		halfPaint(g, 32 - count * 2, 0, false);
		g.setAlpha(1.0);
	}

	private void halfPaint(MineGraphics g, int x, int y, boolean flag) {
		int sx = 0;
		int sy = (flag) ? 0 : 16;
		int w = 32;
		int h = 16;
		int dx = x;
		int dy = (flag) ? y : y + 16;
		g.drawImage(img, sx, sy, w, h, dx, dy);
	}
}
