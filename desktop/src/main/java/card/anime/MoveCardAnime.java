package card.anime;

import card.CardWorks;
import card.body.Card;
import mine.paint.PaintBox;

public class MoveCardAnime implements Runnable {
	
	private CardWorks canvas;
	private CardMover cardMover;
	private int max;

	public MoveCardAnime(CardWorks canvas, Card card, int new_x, int new_y, int max){
		this.canvas = canvas;
		cardMover = new CardMover(card, new_x, new_y);
		this.max = max;
	}
	
	public void run(){
		cardMover.init();
		for( int i=1; i<=max; i++ ){
			PaintBox box = new PaintBox();
			box.add(cardMover.getX(), cardMover.getY(), 32, 32);
			cardMover.move(i, max);
			box.add(cardMover.getX(), cardMover.getY(), 32, 32);

			canvas.repaint(box);
			canvas.sleep(8);
		}
	}
}
