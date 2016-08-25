package dragon3.card;

import card.CardCanvas;
import card.CardListener;
import dragon3.common.Body;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.controller.UnitWorks;
import dragon3.image.ImageManager;
import dragon3.panel.PanelManager;
import mine.event.SleepManager;
import mine.paint.MineImage;
import mine.paint.UnitMap;

public class CardManager implements CardListener {

	private CardCanvas cardCanvas;
	private UnitMap map;
	private UnitWorks uw;
	private PanelManager pm;
	private SleepManager sm;
	private ImageManager im;
	private Body ba, bb;
	private boolean endFlag;

	/*** Constructer ***********************************/

	public CardManager(CardCanvas cardCanvas, UnitWorks uw, UnitMap map, PanelManager pm, SleepManager sm, ImageManager im) {
		cardCanvas.setCardListener(this);
		this.cardCanvas = cardCanvas;
		this.uw = uw;
		this.map = map;
		this.pm = pm;
		this.sm = sm;
		this.im = im;
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
		uw.addMember(bb);
		uw.setEnd(ba, false);
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
		uw.setEnd(ba, false);
	}

	/*** End Judge *************************************/

	public boolean isEnd() {
		return endFlag;
	}
}
