package card.manage;

import java.util.ArrayList;
import java.util.List;

import card.UnitWorks;
import card.anime.AnimeManager;
import card.body.Card;
import card.common.ImageList;


public class CardManager {

	private UnitWorks canvas;
	private AnimeManager anime;
	private Card[] red;
	private Card[] blue;
	private ImageList il;
	
	public CardManager(UnitWorks canvas, AnimeManager anime, ImageList il){
		this.canvas = canvas;
		this.anime = anime;
		this.il = il;
	}

	public void setRedCards(int[] n){
		randomize(n);
		red = new Card[7];
		for (int i=0; i<red.length; i++) {
			red[i] = new Card(n[i], 32*(2+i), 32*4, Card.RED, il);
			canvas.addCard(red[i]);
		}
	}
	
	public void setBlueCards(int[] n){
		randomize(n);
		blue = new Card[7];
		for (int i=0; i<blue.length; i++) {
			blue[i] = new Card(n[i], 32*(2+i), 32*8, Card.BLUE, il);
			canvas.addCard(blue[i]);
		}
	}
	
	private int[] randomize(int[] n){
		for (int i=0; i<n.length; i++) {
			int j = canvas.nextInt(n.length);
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
	public boolean openBlue(int n){
		return openCard(blue[n]);
	}
	
	public boolean openRed(int n){
		return openCard(red[n]);
	}
	
	private boolean openCard(Card card) {
		if (card.getStatus() == Card.CLOSE) {
			anime.openCard(card);
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
