package card.anime;

import card.CardWorks;
import card.body.Card;
import mine.paint.PaintBox;


public class WinCardAnime implements Runnable {

	private CardWorks canvas;
	private Card card;

	public WinCardAnime(CardWorks canvas, Card card){
		this.canvas = canvas;
		this.card = card;
	}

	public void run(){
		for (int i=1; i<=Card.WINNING_MAX; i++) {
			card.winning(i);
			canvas.repaint(new PaintBox(card.getX(), card.getY(), 32, 32));
			canvas.sleep(50);
		}
	}
}
