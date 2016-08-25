package card.anime;

import mine.paint.PaintBox;
import card.UnitWorks;
import card.body.Card;

public class MoveDoubleCardAnime implements Runnable {
	
	private UnitWorks canvas;

	private CardMover redMover;
	private CardMover blueMover;

	private int max;

	public MoveDoubleCardAnime(UnitWorks canvas, Card red, int red_x, int red_y, 
		Card blue, int blue_x, int blue_y, int max){
		this.canvas = canvas;
		redMover = new CardMover(red, red_x, red_y);
		blueMover = new CardMover(blue, blue_x, blue_y);
		this.max = max;
	}
	
	public void run(){
		redMover.init();
		blueMover.init();
		for( int i=1; i<=max; i++ ){
			PaintBox box = new PaintBox();
			box.add(redMover.getX(), redMover.getY(), 32, 32);
			box.add(blueMover.getX(), blueMover.getY(), 32, 32);
			redMover.move(i, max);
			blueMover.move(i, max);
			box.add(redMover.getX(), redMover.getY(), 32, 32);
			box.add(blueMover.getX(), blueMover.getY(), 32, 32);
			canvas.repaint(box);
			canvas.sleep(8);
		}
	}
}
