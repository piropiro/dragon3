package card.body;

import card.CardWorks;
import card.manage.BattleManager;
import card.manage.CardManager;


/**
 * @author k-saito
 *
 */
public class Enemy {

	private CardWorks canvas;
	private CardManager cardManager;
	private BattleManager battleManager;

	@SuppressWarnings("unused")
	private int openCardNum;
	private int closeCardNum;


	public Enemy(CardWorks canvas, BattleManager battleManager, CardManager cardManager){
		this.canvas = canvas;
		this.battleManager = battleManager;
		this.cardManager = cardManager;

		openCardNum = 0;
		closeCardNum = 7;
	}


	public boolean openCard(CardWorks uw){
		if (closeCardNum == 0) return false;

		 while (true) {
		 	int n = canvas.nextInt(7);
		 	if (cardManager.openRed(uw, n)) {
		 		openCardNum++;
		 		closeCardNum--;
		 		return true;
		 	}
		 }
	}

	/**
	 * バトルに出すカードを選択する。<p>
	 *
	 * 赤と青のどちらかがリーチの場合<br>
	 * ①.青の最大カードに勝てる最小カードを選択する。<br>
	 * ②.①がない場合は青の最大カードと引き分けるカードを選択する。<br>
	 * ③.②がない場合はランダムで選択する。<p>
	 * 赤と青のどちらもリーチでない場合<br>
	 * ①ランダムで選択する。<p>
	 *
	 * @return バトルに出すカード
	 */
	public Card selectCard(){
		int redWin = battleManager.getRedWin();
		int blueWin = battleManager.getBlueWin();
		Card[] red = cardManager.getOpenRedCards();
		int blueMax = max(cardManager.getOpenBlueCards());

		if (redWin == 2 || blueWin == 2) {
			Card card = selectMinCard(red, blueMax);
			if (card == null) {
				card = selectMinCard(red, blueMax-1);
			}
			if (card != null) {
				return card;
			}
		}
		return selectRunndomCard(red);
	}


	/**
	 * カードの数字の最大値を返す。<p>
	 *
	 * @param card
	 * @return
	 */
	private int max(Card[] card){
		int max = -1;
		for (int i=0; i<card.length; i++) {
			if (card[i].getNumber() > max) {
				max = card[i].getNumber();
			}
		}
		return max;
	}

	/**
	 * ランダムに選択する。<p>
	 *
	 * @param card
	 * @return
	 */
	private Card selectRunndomCard(Card[] card) {
		int n = canvas.nextInt(card.length);
		return card[n];
	}

	/**
	 * ボーダー以上で最小のカードを選択する。<p>
	 *
	 * @param red
	 * @param border
	 * @return
	 */
	private Card selectMinCard(Card[] red, int border) {
		Card maxCard = null;
		for (int i=0; i<red.length; i++) {
			if (red[i].getNumber() > border) {
				if ( maxCard == null || red[i].getNumber() < maxCard.getNumber()) {
					maxCard = red[i];
				}
			}
		}
		return maxCard;
	}
}
