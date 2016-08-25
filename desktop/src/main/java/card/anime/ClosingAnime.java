package card.anime;

import card.CardWorks;
import card.body.Card;
import card.common.Page;
import mine.paint.UnitMap;

public class ClosingAnime implements Runnable {
	
	private CardWorks canvas;
	private UnitMap map;
	private Card[] card;

	public ClosingAnime(CardWorks canvas, UnitMap map, Card[] card){
		this.canvas = canvas;
		this.map = map;
		this.card = card;
	}
	
	public void run(){
		map.clear(Page.WAKU, 0);
		for (int i=0; i<=13; i++) {
			map.fillRect(Page.BACK, 0, i, 11, 1, 1);
			map.fillRect(Page.CHARA, 0, i, 11, 1, -1);
			map.fillRect(Page.BACK, 0, i-1, 11, 1, -1);
			
			for (int j=0; j<card.length; j++) {
				if (i == card[j].getY() / 32) {
					card[j].dispose();
				}
			}
			canvas.repaint();
			canvas.sleep(100);
		}
	}
}
