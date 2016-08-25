package card.anime;

import mine.paint.PaintBox;
import card.UnitWorks;
import card.body.Card;


public class WinCardAnime implements Runnable {

	private UnitWorks canvas;
	private Card card;

	public WinCardAnime(UnitWorks canvas, Card card){
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
