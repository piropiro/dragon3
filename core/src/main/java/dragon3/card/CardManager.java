package dragon3.card;

import javax.inject.Inject;
import javax.inject.Singleton;

import card.CardCanvas;
import card.CardListener;
import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.controller.UnitWorks;
import dragon3.image.ImageManager;
import dragon3.map.StageMap;
import dragon3.panel.PanelManager;
import mine.event.SleepManager;
import mine.paint.MineImage;
import mine.paint.UnitMap;
import mine.thread.Lock;
import mine.util.Point;

@Singleton
public class CardManager implements CardListener {

	private CardCanvas cardCanvas;
	@Inject StageMap map;
	UnitWorks uw;
	@Inject PanelManager pm;
	@Inject SleepManager sm;
	@Inject ImageManager im;
	private Body ba, bb;
	private boolean endFlag;

	/*** Constructer ***********************************/

	@Inject
	public CardManager(CardCanvas cardCanvas) {
		cardCanvas.setCardListener(this);
		this.cardCanvas = cardCanvas;
	}

	/*** Setup *************************************/

	public void setup(Body ba, Body bb) {
		this.ba = ba;
		this.bb = bb;
		MineImage blueImage = im.getBodyImageList().getImage(ba.getImageNum());
		MineImage redImage = im.getBodyImageList().getImage(bb.getImageNum());
		int[] blueNum = getNumber(ba);
		int[] redNum = getNumber(bb);
		cardCanvas.setRedChara(redImage, redNum);
		cardCanvas.setBlueChara(blueImage, blueNum);
		endFlag = false;
		pm.displayCardCanvas();
		pm.displayHelp(new Point(0, 0), GameColor.BLUE, Texts.help[Texts.H_CARD]);
	}

	private int[] getNumber(Body b) {

		int[] num = new int[7];
		num[0] = b.getStr() / 3;
		num[1] = b.getDef() / 3;
		num[2] = b.getMst() / 3;
		num[3] = b.getMdf() / 3;
		num[4] = b.getHit() / 3;
		num[5] = b.getMis() / 3;
		num[6] = b.getHp() / 3;
		return num;
	}

	/*** End Judge *************************************************/

	@Override
	public void gameExit(int redWin, int blueWin) {
		if (blueWin > redWin) {
			win();
		} else {
			lose();
		}
		endFlag = true;
	}

	/*** Win *****************************************************/

	public void win() {
		UnitMap map = this.map.getMap();
		pm.closeCardCanvas();
		pm.closeData();
		sm.sleep(500);
		pm.addMessage(Texts.card_success);
		pm.addMessage(bb.base.getName() + Texts.ga);
		pm.addMessage(Texts.card_success2);
		pm.startMessage(bb);
		map.setData(Page.P20, bb.getX(), bb.getY(), 0);
		map.setData(Page.P50, bb.getX(), bb.getY(), 0);
		bb.setHp(0);
		uw.closeCardBattle(ba, bb, true);
	}

	/*** Lose *****************************************************/

	public void lose() {
		pm.closeCardCanvas();
		bb.setHp(bb.getHpMax());
		pm.closeData();
		sm.sleep(500);
		pm.addMessage(Texts.card_fail);
		pm.addMessage(bb.base.getName() + Texts.no);
		pm.addMessage(Texts.card_fail2);
		pm.startMessage(bb);
		uw.closeCardBattle(ba, bb, false);
	}

	/*** End Judge *************************************/

	public boolean isEnd() {
		return endFlag;
	}
	
	public Lock getLock() {
		return cardCanvas.getLock();
	}

	public void setUw(UnitWorks uw) {
		this.uw = uw;
	}

	public CardCanvas getCardCanvas() {
		return cardCanvas;
	}
}
