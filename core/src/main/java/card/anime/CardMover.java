package card.anime;

import card.body.Card;


public class CardMover {

	private Card card;
	private int old_x;
	private int old_y;
	private int new_x;
	private int new_y;

	public CardMover(Card card, int new_x, int new_y){
		this.card = card;
		this.new_x = new_x;
		this.new_y = new_y;
		init();
	}
	
	public void init(){
		old_x = card.getX();
		old_y = card.getY();
	}

	public void move(int n, int max) {
		int x = old_x + ( new_x - old_x )*n/max;
		int y = old_y + ( new_y - old_y )*n/max;
		card.setLocation(x, y);
	}
	
	public int getX(){
		return card.getX();
	}
	public int getY(){
		return card.getY();
	}
}
