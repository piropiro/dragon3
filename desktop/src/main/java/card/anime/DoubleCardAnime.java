package card.anime;

import card.CardWorks;
import card.body.Card;
import card.common.Page;
import mine.paint.UnitMap;

public class DoubleCardAnime implements Runnable {

	private CardWorks uw;
	private UnitMap map;
	private AnimeManager anime;
	private Card left;
	private Card right;

	public DoubleCardAnime(CardWorks uw, AnimeManager anime, UnitMap map, Card left, Card right){
		this.uw = uw;
		this.anime = anime;
		this.map = map;
		this.left = left;
		this.right = right;
	}
	
	public void run(){
		int old_x = left.getX();
		int old_y = left.getY();
		anime.moveCard(uw, left, 3*32, 10*32, right, 3*32, 10*32, 30);
		map.setData(Page.BACK, 3, 10, 0);
		right.dispose();
		left.setNumber(left.getNumber()*2);
		anime.openCard(uw, left);
		anime.moveCard(uw, left, old_x, old_y, 30);
	}
}
