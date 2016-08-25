package card.anime;

import card.CardWorks;
import card.body.Card;
import mine.paint.PaintBox;


public class LoseCardAnime implements Runnable {

	private CardWorks canvas;
	private Card card;

	public LoseCardAnime(CardWorks canvas, Card card){
		this.canvas = canvas;
		this.card = card;
	}

	public void run(){
		for (int i=1; i<=Card.SLASHING_MAX; i++) {
			card.slashing(i);
			canvas.repaint(new PaintBox(card.getX() - 64, card.getY(), 160, 32));
			canvas.sleep(50);
		}
		card.dispose();
	}
}
