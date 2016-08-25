package card.anime;

import card.CardWorks;
import card.body.Card;
import mine.paint.PaintBox;


public class CloseCardAnime implements Runnable {

	private CardWorks canvas;
	private Card card;

	public CloseCardAnime(CardWorks canvas, Card card){
		this.canvas = canvas;
		this.card = card;
	}

	public void run(){
		for (int i=0; i<6; i++) {
			card.opening(i);
			canvas.repaint(new PaintBox(card.getX(), card.getY(), 32, 32));
			canvas.sleep(40);
		}
		card.close();
	}
}
