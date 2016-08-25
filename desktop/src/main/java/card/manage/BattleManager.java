package card.manage;

import card.CardWorks;
import card.anime.AnimeManager;
import card.body.Card;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BattleManager {

	@Inject AnimeManager anime;
	private int redWin;
	private int blueWin;
	private int battleNum;

	@Inject
	public BattleManager(){
	}

	public void initialize(){
		redWin = 0;
		blueWin = 0;
		battleNum = 0;
	}

	public void startBattle(CardWorks uw, Card red, Card blue){
		battleNum++;
		anime.battle(uw, red, blue);
		if (red.getNumber() > blue.getNumber()) {
			anime.loseCard(uw, blue);
			anime.winCard(uw, red);
			anime.closeCard(uw, red);
			red.win();
			anime.moveCard(uw, red, (4-redWin)*32, 2*32, 40);
			redWin++;
		} else if (red.getNumber() < blue.getNumber()) {
			anime.loseCard(uw, red);
			anime.winCard(uw, blue);
			anime.closeCard(uw, blue);
			blue.win();
			anime.moveCard(uw, blue, (6+blueWin)*32, 10*32, 40);
			blueWin++;
		} else {
			anime.drawCard(uw, red, blue);
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
