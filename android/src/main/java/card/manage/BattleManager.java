package card.manage;

import card.anime.AnimeManager;
import card.body.Card;


public class BattleManager {

	private AnimeManager anime;
	private int redWin;
	private int blueWin;
	private int battleNum;

	public BattleManager(AnimeManager anime){
		this.anime = anime;
	}

	public void initialize(){
		redWin = 0;
		blueWin = 0;
		battleNum = 0;
	}

	public void startBattle(Card red, Card blue){
		battleNum++;
		anime.battle(red, blue);
		if (red.getNumber() > blue.getNumber()) {
			anime.loseCard(blue);
			anime.winCard(red);
			anime.closeCard(red);
			red.win();
			anime.moveCard(red, (4-redWin)*32, 2*32, 40);
			redWin++;
		} else if (red.getNumber() < blue.getNumber()) {
			anime.loseCard(red);
			anime.winCard(blue);
			anime.closeCard(blue);
			blue.win();
			anime.moveCard(blue, (6+blueWin)*32, 10*32, 40);
			blueWin++;
		} else {
			anime.drawCard(red, blue);
		}
	}
	
	public boolean isEnd(){
		if (redWin >= 3 || blueWin >= 3 || battleNum >= 7) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return
	 */
	public int getRedWin() {
		return redWin;
	}
	/**
	 * @return
	 */
	public int getBlueWin() {
		return blueWin;
	}

	/**
	 * 
	 */
	public void retireBlue() {
		redWin = 3;
		blueWin = 0;
	}

}
