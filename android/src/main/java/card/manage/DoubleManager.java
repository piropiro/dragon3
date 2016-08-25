package card.manage;

import card.anime.AnimeManager;
import card.body.Card;


public class DoubleManager {

	private AnimeManager anime;
	private CardManager cardManager;
	
	private boolean doubleFlag;
	private Card left;
	private Card right;

	public DoubleManager( 
		CardManager cardManager, AnimeManager anime){
		this.anime = anime;
		this.cardManager = cardManager;
	}
	
	public void initialize(){
		doubleFlag = false;
		left = null;
		right = null;
	}
	
	public void checkDoubleCard(){
		Card[] blue = cardManager.getOpenBlueCards();
		boolean newFlag = false;
		for (int i=0; i<blue.length-1; i++) {
			for (int j=i+1; j<blue.length; j++) {
				if (blue[i].getNumber() == blue[j].getNumber()) {
					left = blue[i];
					right = blue[j];
					newFlag = true;
					break;
				}
			}
		}

		if (newFlag && !doubleFlag) {
			doubleFlag = true;
			anime.openDoubleCard();
		}
		
		if (!newFlag && doubleFlag) {
			doubleFlag = false;
			anime.closeDoubleCard();
		}
	}
	
	public boolean clickDoubleCard(){
		if (doubleFlag) {
			anime.doubleCard(left, right);
			doubleFlag = false;
			return true;
		} else {
			return false;
		}
	}
	
}
