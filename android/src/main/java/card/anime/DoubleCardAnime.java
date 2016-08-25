package card.anime;

import mine.paint.UnitMap;
import card.body.Card;
import card.common.Page;

public class DoubleCardAnime implements Runnable {

	private UnitMap map;
	private AnimeManager anime;
	private Card left;
	private Card right;

	public DoubleCardAnime(AnimeManager anime, UnitMap map, Card left, Card right){
		this.anime = anime;
		this.map = map;
		this.left = left;
		this.right = right;
	}
	
	public void run(){
		int old_x = left.getX();
		int old_y = left.getY();
		anime.moveCard(left, 3*32, 10*32, right, 3*32, 10*32, 30);
		map.setData(Page.BACK, 3, 10, 0);
		right.dispose();
		left.setNumber(left.getNumber()*2);
		anime.openCard(left);
		anime.moveCard(left, old_x, old_y, 30);
	}
}
