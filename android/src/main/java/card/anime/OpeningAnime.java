package card.anime;

import mine.paint.UnitMap;
import card.UnitWorks;
import card.body.Card;
import card.common.ImageList;
import card.common.Page;

public class OpeningAnime implements Runnable {
	
	private UnitWorks canvas;
	private UnitMap map;
	private AnimeManager anime;
	private Card[] red;
	private Card[] blue;
	private ImageList il;

	public OpeningAnime(UnitWorks canvas, AnimeManager anime, UnitMap map, Card[] red, Card[] blue, ImageList il){
		this.canvas = canvas;
		this.anime = anime;
		this.map = map;
		this.red = red;
		this.blue = blue;
		this.il = il;
	}
	
	public void run(){
		setBackground();

		map.setData(Page.CHARA, 5, 2, 1);
		map.setData(Page.CHARA, 5, 10, 0);
		canvas.repaint();

		setRedCards();
		setBlueCards();
	}
	
	private void setBackground(){
		Card card = new Card(0, 0, 0, Card.BLUE, il);
		card.close();
		canvas.addCard(card);
		
		for (int i=0; i<10; i++) {
			map.setData(Page.BACK, i, 0, 1);
			anime.moveCard(card, (i+1)*32, 0, 4);
		}
		for (int i=0; i<12; i++) {
			map.setData(Page.BACK, 10, i, 1);
			anime.moveCard(card, 10*32, (i+1)*32, 4);
		}
		for (int i=10; i>0; i--) {
			map.setData(Page.BACK, i, 12, 1);
			anime.moveCard(card, (i-1)*32, 12*32, 4);
		}
		for (int i=12; i>0; i--) {
			map.setData(Page.BACK, 0, i, 1);
			anime.moveCard(card, 0, (i-1)*32, 4);
		}
		card.dispose();
		canvas.removeCard(card);
		canvas.repaint();
		
		for (int i=1; i<12; i++) {
			map.fillRect(Page.BACK, 1, i, 9, 1, 0);
			canvas.repaint();
			canvas.sleep(60);
		}
	}

	private void setRedCards(){
		Card card = new Card(0, 9*32, 4*32, Card.RED, il);
		card.close();
		canvas.addCard(card);
		red[6].close();
		for (int i=5; i>=0; i--) {
			anime.moveCard(card, (i+2)*32, 4*32, 8);
			red[i].close();
		}
		card.dispose();
		canvas.removeCard(card);
		canvas.repaint();
	}

	private void setBlueCards(){
		Card card = new Card(0, 2*32, 8*32, Card.BLUE, il);
		card.close();
		canvas.addCard(card);
		blue[0].close();
		for (int i=1; i<7; i++) {
			anime.moveCard(card, (i+2)*32, 8*32, 8);
			blue[i].close();
		}
		card.dispose();
		canvas.removeCard(card);
		canvas.repaint();
	}
}
