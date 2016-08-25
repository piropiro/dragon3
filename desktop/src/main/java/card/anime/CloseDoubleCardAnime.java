package card.anime;

import card.CardWorks;
import card.common.Page;
import mine.paint.PaintBox;
import mine.paint.UnitMap;


public class CloseDoubleCardAnime implements Runnable {

	private CardWorks canvas;
	private UnitMap map;

	public CloseDoubleCardAnime(CardWorks canvas, UnitMap map){
		this.canvas = canvas;
		this.map = map;
	}

	public void run(){
		map.setData(Page.BACK, 3, 10, 0);
		canvas.repaint(new PaintBox(3*32, 10*32, 32, 32));
	}
}
