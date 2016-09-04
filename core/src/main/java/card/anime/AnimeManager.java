package card.anime;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import card.CardMap;
import card.CardWorks;
import card.body.Card;
import card.common.ImageList;

@Singleton
public class AnimeManager {

	@Inject CardMap map;
	@Inject ImageList il;
	
	@Inject
	public AnimeManager(){
	}
	
	public void opening(CardWorks canvas, List<Card> red, List<Card> blue){
		new OpeningAnime(canvas, this, map.getMap(), red, blue, il).run();
	}
	public void closing(CardWorks canvas, List<Card> cards){
		new ClosingAnime(canvas, map.getMap(), cards).run();
	}
	
	public void battle(CardWorks canvas, Card red, Card blue){
		new BattleAnime(canvas, this, red, blue).run();
	}

	public void openCard(CardWorks canvas, Card card){
		new OpenCardAnime(canvas, card).run();
	}
	public void closeCard(CardWorks canvas, Card card){
		new CloseCardAnime(canvas, card).run();
	}

	public void loseCard(CardWorks canvas, Card card){
		new LoseCardAnime(canvas, card).run();
	}
	public void winCard(CardWorks canvas, Card card){
		new WinCardAnime(canvas, card).run();
	}
	public void drawCard(CardWorks canvas, Card red, Card blue){
		new DrawCardAnime(canvas, red, blue).run();
	}

	public void moveCard(CardWorks canvas, Card card, int new_x, int new_y, int max){
		new MoveCardAnime(canvas, card, new_x, new_y, max).run();
	}

	public void moveCard(CardWorks canvas, Card red, int red_x, int red_y, Card blue, int blue_x, int blue_y, int max){
		new MoveDoubleCardAnime(canvas, red, red_x, red_y, blue, blue_x, blue_y, max).run();
	}
	
	public void openDoubleCard(CardWorks canvas){
		new OpenDoubleCardAnime(canvas, this, map.getMap(), il).run();
	}
	
	public void closeDoubleCard(CardWorks canvas){
		new CloseDoubleCardAnime(canvas, map.getMap()).run();
	}
	public void doubleCard(CardWorks canvas, Card left, Card right){
		new DoubleCardAnime(canvas, this, map.getMap(), left, right).run();
	}
}
