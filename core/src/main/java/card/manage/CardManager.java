package card.manage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import card.CardWorks;
import card.anime.AnimeManager;
import card.body.Card;
import card.body.Enemy;
import card.common.ImageList;

@Singleton
public class CardManager {

	@Inject AnimeManager anime;
	@Inject ImageList il;

	private Enemy enemy;
	private List<Card> blue;
	
	@Inject
	public CardManager(){
	}

	public void setRedCards(CardWorks uw, List<Integer> n){
		randomize(uw, n);
		enemy.setupCard(n, il);
		enemy.addCards(uw);
	}
	
	public void setBlueCards(CardWorks uw, List<Integer> n){
		randomize(uw, n);
		blue = new ArrayList<>();
		for (int i=0; i<7; i++) {
			Card card = new Card(n.get(i), 32*(2+i), 32*8, Card.BLUE, il);
			blue.add(card);
			uw.addCard(card);
		}
	}
	
	private List<Integer> randomize(CardWorks uw, List<Integer> n){
		List<Integer> result = new ArrayList<>();
		while (!n.isEmpty()) {
			result.add(n.remove(uw.nextInt(n.size())));
		}
		return result;
	}

	public List<Card> getRedCards(){
		return enemy.getRed();
	}
	
	public List<Card> getBlueCards(){
		return blue;
	}
	
	public boolean isOpenedBlue(int n){
		return (blue.get(n).getStatus() == Card.OPEN);
	}
	public boolean openBlue(CardWorks uw, int n){
		return openCard(uw, blue.get(n));
	}
	
	public boolean openRed(CardWorks uw, int n){
		return openCard(uw, enemy.getRed().get(n));
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
		return getOpenCards(enemy.getRed());
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
	
}
