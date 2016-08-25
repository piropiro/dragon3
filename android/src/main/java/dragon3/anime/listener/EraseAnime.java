package dragon3.anime.listener;

import dragon3.anime.AnimeWorks;
import dragon3.common.constant.Page;
import dragon3.map.MapWorks;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.UnitMap;

public class EraseAnime implements AnimeListener {

	private MapWorks mw;
	private UnitMap map;

	private int bodyX;
	private int bodyY;
	private MineImage offi;
	private int count;

	/*** Constructer ***********************/

	public EraseAnime(MapWorks mw, UnitMap map, int x, int y) {
		this.mw = mw;
		this.map = map;
		this.bodyX = x;
		this.bodyY = y;
		offi = createOffi(map, x, y);
	}

	/*** SourceImage ********************/

	private MineImage createOffi(UnitMap map, int x, int y) {
		int img = map.getData(Page.P20, x, y);
		int sts = map.getData(Page.P50, x, y);
		map.setData(Page.P20, x, y, 0);
		map.setData(Page.P30, x, y, 0);
		map.setData(Page.P50, x, y, 0);
		MineImage offi = map.getBuffer(x, y).getCopy();
		map.setData(Page.P20, x, y, img);
		map.setData(Page.P50, x, y, sts);
		return offi;
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {
		for (count = 1; count <= 4; count++) {
			ac.repaint();
			ac.sleep(100);
		}
		map.setData(Page.P20, bodyX, bodyY, 0);
		map.setData(Page.P50, bodyX, bodyY, 0);
		mw.update();
		ac.setVisible(false);
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		for (int x = 0; x < 8; x++) {
			int sx = x * 4;
			int sy = 0;
			int w = count;
			int h = 32;
			g.drawImage(offi, sx, sy, w, h, sx, sy);
		}
		for (int y = 0; y < 8; y++) {
			int sx = 0;
			int sy = y * 4;
			int w = 32;
			int h = count;
			g.drawImage(offi, sx, sy, w, h, sx, sy);
		}
	}
}
