package card.anime;

import mine.paint.PaintBox;
import card.UnitWorks;
import card.body.Card;


public class LoseCardAnime implements Runnable {

	private UnitWorks canvas;
	private Card card;

	public LoseCardAnime(UnitWorks canvas, Card card){
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
