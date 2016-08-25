package card.anime;

import card.CardWorks;
import card.body.Card;


public class BattleAnime implements Runnable {

	private CardWorks canvas;
	private AnimeManager manager;
	private Card red;
	private Card blue;

	public BattleAnime(CardWorks canvas, AnimeManager manager, Card red, Card blue){
		this.canvas = canvas;
		this.manager = manager;
		this.red = red;
		this.blue =blue;
	}

	public void run(){
		manager.moveCard(canvas, red, 32*7, 32*6, blue, 32*3, 32*6, 30 );
		canvas.sleep(300);
		manager.moveCard(canvas, red, 32*4, 32*6, blue, 32*6, 32*6, 30 );
	}
}
