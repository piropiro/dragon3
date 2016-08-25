package dragon3.anime.listener;

import dragon3.anime.AnimeWorks;
import dragon3.anime.item.Number;
import dragon3.common.constant.Page;
import dragon3.map.MapWorks;
import mine.paint.MineGraphics;
import mine.paint.UnitMap;

public class SummonAnime implements AnimeListener {

	private MapWorks mw;
	private UnitMap map;

	private int bodyX;
	private int bodyY;
	private int image;
	private Number n;

	/*** Constructer ***********************/

	public SummonAnime(MapWorks mw, UnitMap map, int image, int bodyX, int bodyY) {
		this.mw = mw;
		this.map = map;
		this.image = image;
		this.bodyX = bodyX;
		this.bodyY = bodyY;
		n = new Number(0, 0, 0);
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {
		ac.setSize(32, 48);
		for (int i=0; i<8; i++) {
			n.setY(4*i);
			ac.repaint();
			ac.sleep(30);
		}
		ac.setSize(32, 64);
		while (!n.end()) {
			n.move();
			ac.repaint();
			ac.sleep(30);
		}
		map.setData(Page.P20, bodyX, bodyY, image);
		mw.update();
		ac.sleep(100);
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		g.drawImage(map.getTile(Page.P20, image), n.getX(), 32 - n.getY());
	}
}
