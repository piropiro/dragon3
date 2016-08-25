/*
 * 作成日: 2004/03/27
 */
package dragon3.paint;

import java.util.List;

import dragon3.attack.FightManager;
import dragon3.camp.Camp;
import dragon3.card.CardPaint;
import dragon3.common.Body;
import dragon3.common.constant.Page;
import dragon3.controller.UnitWorks;
import dragon3.map.MapWorks;

/**
 * @author k-saito
 */
public class PaintUtils {

	public static void setWalkPaint(UnitWorks uw, Body b) {
		System.out.println("WalkPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new WalkPaint(uw, b));
	}
	public static void setWaitPaint(UnitWorks uw) {
		System.out.println("WaitPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new WaitPaint());
		uw.getUnitMap().clear(Page.P40, 0);
		uw.getMapWorks().repaint();
	}
	public static void setBasicPaint(UnitWorks uw) {
		System.out.println("BasicPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new BasicPaint(uw));
	}
	public static void setScorePaint(UnitWorks uw) {
		System.out.println("ScorePaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new ScorePaint(uw));
	}
	public static void setPutPlayersPaint(UnitWorks uw, List<Body> charaList, List<Body> playerList) {
		System.out.println("PutPlayersPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new PutPlayersPaint(uw, charaList, playerList));
	}
	public static void setEndPaint(UnitWorks uw, Body b) {
		System.out.println("EndPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new EndPaint(uw, b));
	}
	public static void setAttackPaint(UnitWorks uw, FightManager fm, Body b) {
		System.out.println("AttackPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new AttackPaint(uw, fm, b));
	}
	public static void setKakuseiPaint(UnitWorks uw, Body b) {
		System.out.println("KakuseiPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new KakuseiPaint(uw, b));
	}
	public static void setBerserkPaint(UnitWorks uw, Body b) {
		System.out.println("BerserkPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new BerserkPaint(uw, b));
	}
	public static void setChangePaint(UnitWorks uw, Body ba, Body bb) {
		System.out.println("ChangePaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new ChangePaint(uw, ba, bb));
	}
	public static void setButtonPaint(UnitWorks uw, int x, int y, EventListener pl, int type) {
		System.out.println("ButtonPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new ButtonPaint(uw, x, y, pl, type));
	}
	public static void setCardPaint(UnitWorks uw, Body ba, Body bb) {
		System.out.println("CardPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new CardPaint(uw, ba, bb));
	}
	public static void setCampPaint(UnitWorks uw, Camp camp) {
		System.out.println("CampPaint");
		MapWorks mw = uw.getMapWorks();
		mw.setEventListener(new CampPaint(uw, camp));
	}

}
