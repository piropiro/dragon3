package card.body;

import card.manage.CardManager;


public class Player {

	private CardManager cardManager;

	private int openCardNum;
	private int closeCardNum;


	public Player(CardManager cardManager){
		this.cardManager = cardManager;
		openCardNum = 0;
		closeCardNum = 7;
	}

	public boolean openCard(int n){
		if (closeCardNum == 0) return false;
		if (openCardNum >= 3) return false;

		if (cardManager.openBlue(n)) {
			closeCardNum--;
			openCardNum++;
			return true;
		} else {
			return false;
		}
	}
	
	public Card selectCard(int n){
		openCardNum--;
		return cardManager.getBlueCard(n);
	}
	
	public void doubleCard(){
		openCardNum--;
	}
	
	public boolean hasCard(){
		return (openCardNum + closeCardNum > 0);
	}
}
