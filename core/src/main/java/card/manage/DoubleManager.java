package card.manage;

import javax.inject.Inject;
import javax.inject.Singleton;

import card.CardWorks;
import card.anime.AnimeManager;
import card.body.Card;

@Singleton
public class DoubleManager {

	@Inject AnimeManager anime;
	@Inject CardManager cardManager;
	
	private boolean doubleFlag;
	private Card left;
	private Card right;

	@Inject
	public DoubleManager(){
	}
	
	public void initialize(){
		doubleFlag = false;
		left = null;
		right = null;
	}
	
	public void checkDoubleCard(CardWorks uw){
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
			anime.openDoubleCard(uw);
		}
		
		if (!newFlag && doubleFlag) {
			doubleFlag = false;
			anime.closeDoubleCard(uw);
		}
	}
	
	public boolean clickDoubleCard(CardWorks uw){
		if (doubleFlag) {
			anime.doubleCard(uw, left, right);
			doubleFlag = false;
			return true;
		} else {
			return false;
		}
	}
	
}
