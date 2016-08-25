package card.anime;

import mine.paint.PaintBox;
import card.UnitWorks;
import card.body.Card;


public class CloseCardAnime implements Runnable {

	private UnitWorks canvas;
	private Card card;

	public CloseCardAnime(UnitWorks canvas, Card card){
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
