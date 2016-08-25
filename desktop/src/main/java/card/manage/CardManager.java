package card.manage;

import card.CardWorks;
import card.anime.AnimeManager;
import card.body.Card;
import card.common.ImageList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CardManager {

	@Inject AnimeManager anime;
	@Inject ImageList il;
	private Card[] red;
	private Card[] blue;
	
	@Inject
	public CardManager(){
	}

	public void setRedCards(CardWorks uw, int[] n){
		randomize(uw, n);
		red = new Card[7];
		for (int i=0; i<red.length; i++) {
			red[i] = new Card(n[i], 32*(2+i), 32*4, Card.RED, il);
			uw.addCard(red[i]);
		}
	}
	
	public void setBlueCards(CardWorks uw, int[] n){
		randomize(uw, n);
		blue = new Card[7];
		for (int i=0; i<blue.length; i++) {
			blue[i] = new Card(n[i], 32*(2+i), 32*8, Card.BLUE, il);
			uw.addCard(blue[i]);
		}
	}
	
	private int[] randomize(CardWorks uw, int[] n){
		for (int i=0; i<n.length; i++) {
			int j = uw.nextInt(n.length);
			int tmp = n[j];
			n[j] = n[i];
			n[i] = tmp;
		}
		return n;
	}

	public Card[] getRedCards(){
		return red;
	}
	
	public Card[] getBlueCards(){
		return blue;
	}
	
	public boolean isOpenedBlue(int n){
		if (blue[n].getStatus() == Card.OPEN) {
			return true;
		} else {
			return false;
		}
	}
	public boolean openBlue(CardWorks uw, int n){
		return openCard(uw, blue[n]);
	}
	
	public boolean openRed(CardWorks uw, int n){
		return openCard(uw, red[n]);
	}
	
	private boolean openCard(CardWorks uw, Card card) {
		if (card.getStatus() == Card.CLOSE) {
			anime.openCard(uw, card);
			return true;
		} else {
			return false;
		}	
	}
	
	public Card[] getOpenRedCards(){
		return getOpenCards(red);
	}
	
	public Card[] getOpenBlueCards(){
		return getOpenCards(blue);
	}
	
	private Card[] getOpenCards(Card[] card){
		List<Card> openCards = new ArrayList<Card>();
		for (int i=0; i<card.length; i++) {
			if (card[i].getStatus() == Card.OPEN) {
				openCards.add(card[i]);
			}
		}
		return openCards.toArray(new Card[0]);
	}
	
	public Card getBlueCard(int n){
		return blue[n];
	}
	
	public Card getRedCard(int n){
		return red[n];
	}
}
