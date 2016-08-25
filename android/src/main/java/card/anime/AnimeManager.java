package card.anime;

import card.UnitWorks;
import card.body.Card;
import card.common.ImageList;
import mine.paint.UnitMap;

public class AnimeManager {

	private UnitWorks canvas;
	private UnitMap map;
	private ImageList il;
	

	public AnimeManager(UnitWorks canvas, UnitMap map, ImageList il){
		this.canvas = canvas;
		this.map = map;
		this.il = il;
	}
	
	public void opening(Card[] red, Card[] blue){
		new OpeningAnime(canvas, this, map, red, blue, il).run();
	}
	public void closing(Card[] cards){
		new ClosingAnime(canvas, map, cards).run();
	}
	
	public void battle(Card red, Card blue){
		new BattleAnime(canvas, this, red, blue).run();
	}

	public void openCard(Card card){
		new OpenCardAnime(canvas, card).run();
	}
	public void closeCard(Card card){
		new CloseCardAnime(canvas, card).run();
	}

	public void loseCard(Card card){
		new LoseCardAnime(canvas, card).run();
	}
	public void winCard(Card card){
		new WinCardAnime(canvas, card).run();
	}
	public void drawCard(Card red, Card blue){
		new DrawCardAnime(canvas, red, blue).run();
	}

	public void moveCard(Card card, int new_x, int new_y, int max){
		new MoveCardAnime(canvas, card, new_x, new_y, max).run();
	}

	public void moveCard(Card red, int red_x, int red_y, Card blue, int blue_x, int blue_y, int max){
		new MoveDoubleCardAnime(canvas, red, red_x, red_y, blue, blue_x, blue_y, max).run();
	}
	
	public void openDoubleCard(){
		new OpenDoubleCardAnime(canvas, this, map, il).run();
	}
	
	public void closeDoubleCard(){
		new CloseDoubleCardAnime(canvas, map).run();
	}
	public void doubleCard(Card left, Card right){
		new DoubleCardAnime(this, map, left, right).run();
	}
}
