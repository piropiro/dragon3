package card.manage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import card.CardWorks;
import card.anime.AnimeManager;
import card.body.Card;
import card.common.ImageList;

@Singleton
public class CardManager {

	@Inject AnimeManager anime;
	@Inject ImageList il;
	private List<Card> red;
	private List<Card> blue;
	
	@Inject
	public CardManager(){
	}

	public void setRedCards(CardWorks uw, int[] n){
		randomize(uw, n);
		red = new ArrayList<>();
		for (int i=0; i<7; i++) {
			Card card = new Card(n[i], 32*(2+i), 32*4, Card.RED, il);
			red.add(card);
			uw.addCard(card);
		}
	}
	
	public void setBlueCards(CardWorks uw, int[] n){
		randomize(uw, n);
		blue = new ArrayList<>();
		for (int i=0; i<7; i++) {
			Card card = new Card(n[i], 32*(2+i), 32*8, Card.BLUE, il);
			blue.add(card);
			uw.addCard(card);
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

	public List<Card> getRedCards(){
		return red;
	}
	
	public List<Card> getBlueCards(){
		return blue;
	}
	
	public boolean isOpenedBlue(int n){
		if (blue.get(n).getStatus() == Card.OPEN) {
			return true;
		} else {
			return false;
		}
	}
	public boolean openBlue(CardWorks uw, int n){
		return openCard(uw, blue.get(n));
	}
	
	public boolean openRed(CardWorks uw, int n){
		return openCard(uw, red.get(n));
	}
	
	private boolean openCard(CardWorks uw, Card card) {
		if (card.getStatus() == Card.CLOSE) {
			anime.openCard(uw, card);
			return true;
		} else {
			return false;
		}	
	}
	
	public List<Card> getOpenRedCards(){
		return getOpenCards(red);
	}
	
	public List<Card> getOpenBlueCards(){
		return getOpenCards(blue);
	}
	
	private List<Card> getOpenCards(List<Card> cards){
		List<Card> openCards = new ArrayList<>();
		for (Card card : cards) {
			if (card.getStatus() == Card.OPEN) {
				openCards.add(card);
			}
		}
		return openCards;
	}
	
	public Card getBlueCard(int n){
		return blue.get(n);
	}
	
	public Card getRedCard(int n){
		return red.get(n);
	}
}
