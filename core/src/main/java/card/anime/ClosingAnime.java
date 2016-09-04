package card.anime;

import java.util.List;

import card.CardWorks;
import card.body.Card;
import card.common.Page;
import mine.paint.UnitMap;

public class ClosingAnime implements Runnable {
	
	private CardWorks canvas;
	private UnitMap map;
	private List<Card> cards;

	public ClosingAnime(CardWorks canvas, UnitMap map, List<Card> cards){
		this.canvas = canvas;
		this.map = map;
		this.cards = cards;
	}
	
	public void run(){
		map.clear(Page.WAKU, 0);
		for (int i=0; i<=13; i++) {
			map.fillRect(Page.BACK, 0, i, 11, 1, 1);
			map.fillRect(Page.CHARA, 0, i, 11, 1, -1);
			map.fillRect(Page.BACK, 0, i-1, 11, 1, -1);

			for (Card card : cards) {
				if (i == card.getY() / 32) {
					card.dispose();
				}
			}
			canvas.repaint();
			canvas.sleep(100);
		}
	}
}
