package card.anime;

import mine.paint.PaintBox;
import mine.paint.UnitMap;
import card.UnitWorks;
import card.body.Card;
import card.common.ImageList;
import card.common.Page;


public class OpenDoubleCardAnime implements Runnable {

	private UnitWorks canvas;
	private AnimeManager anime;
	private UnitMap map;
	private ImageList il;

	public OpenDoubleCardAnime(UnitWorks canvas, AnimeManager anime, UnitMap map, ImageList il){
		this.canvas = canvas;
		this.anime = anime;
		this.map = map;
		this.il = il;
	}

	public void run(){
		Card card = new Card(0, 3*32, 10*32, Card.BLUE, il);
		card.close();
		canvas.addCard(card);
		anime.openCard(card);
		map.setData(Page.BACK, 3, 10, 2);
		canvas.removeCard(card);
		canvas.repaint(new PaintBox(3*32, 10*32, 32, 32));
	}
}
