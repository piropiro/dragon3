package dragon3.anime.listener;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.UnitMap;
import dragon3.anime.AnimeWorks;
import dragon3.anime.item.Number;
import dragon3.common.constant.Page;
import dragon3.map.MapWorks;

public class StatusAnime implements AnimeListener {

	private MapWorks mw;
	private UnitMap map;
	private int bodyX;
	private int bodyY;
	private int status;
	private MineImage[] statusImage;

	private Number n;

	/*** Constructer ***********************/

	public StatusAnime(MapWorks mw, UnitMap map, int status, int bodyX, int bodyY, MineImage[] statusImage) {
		this.mw = mw;
		this.map = map;
		this.bodyX = bodyX;
		this.bodyY = bodyY;
		this.status = status;
		this.statusImage = statusImage;
		n = new Number(0, 1, 0);
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {
		map.setData(Page.P50, bodyX, bodyY, 0);
		mw.update();
		while (!n.end()) {
			n.move();
			ac.repaint();
			ac.sleep(25);
		}
		map.setData(Page.P50, bodyX, bodyY, status);
		mw.update();
		ac.sleep(100);
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		g.drawImage(statusImage[status], n.getX(), 16 - n.getY());
	}
}
