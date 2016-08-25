package card.anime;

import mine.paint.PaintBox;
import mine.paint.UnitMap;
import card.UnitWorks;
import card.common.Page;


public class CloseDoubleCardAnime implements Runnable {

	private UnitWorks canvas;
	private UnitMap map;

	public CloseDoubleCardAnime(UnitWorks canvas, UnitMap map){
		this.canvas = canvas;
		this.map = map;
	}

	public void run(){
		map.setData(Page.BACK, 3, 10, 0);
		canvas.repaint(new PaintBox(3*32, 10*32, 32, 32));
	}
}
