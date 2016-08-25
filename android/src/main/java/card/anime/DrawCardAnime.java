package card.anime;

import mine.paint.PaintBox;
import card.UnitWorks;
import card.body.Card;


public class DrawCardAnime implements Runnable {

	private UnitWorks canvas;
	private Card red;
	private Card blue;

	public DrawCardAnime(UnitWorks canvas, Card red, Card blue){
		this.canvas = canvas;
		this.red = red;
		this.blue = blue;
	}

	public void run(){
		PaintBox box = new PaintBox();
		box.add(red.getX() - 64, red.getY(), 160, 32);
		box.add(blue.getX() - 64, blue.getY(), 160, 32);

		for (int i=1; i<=Card.SLASHING_MAX; i++) {
			red.slashing(i);
			blue.slashing(i);
			canvas.repaint(box);
			canvas.sleep(50);
		}
		red.dispose();
		blue.dispose();
	}
}
