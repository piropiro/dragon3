package card.anime;

import card.UnitWorks;
import card.body.Card;


public class BattleAnime implements Runnable {

	private UnitWorks canvas;
	private AnimeManager manager;
	private Card red;
	private Card blue;

	public BattleAnime(UnitWorks canvas, AnimeManager manager, Card red, Card blue){
		this.canvas = canvas;
		this.manager = manager;
		this.red = red;
		this.blue =blue;
	}

	public void run(){
		manager.moveCard(red, 32*7, 32*6, blue, 32*3, 32*6, 30 );
		canvas.sleep(300);
		manager.moveCard(red, 32*4, 32*6, blue, 32*6, 32*6, 30 );
	}
}
