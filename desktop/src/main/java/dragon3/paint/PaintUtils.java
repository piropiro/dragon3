/*
 * 作成日: 2004/03/27
 */
package dragon3.paint;

import java.util.List;

import dragon3.attack.FightManager;
import dragon3.camp.Camp;
import dragon3.common.Body;
import dragon3.common.constant.Page;
import dragon3.controller.UnitWorks;

/**
 * @author k-saito
 */
public class PaintUtils {

	public static void setWalkPaint(UnitWorks uw, Body b) {
		System.out.println("WalkPaint");
		uw.setEventListener(new WalkPaint(uw, b));
	}
	public static void setWaitPaint(UnitWorks uw) {
		System.out.println("WaitPaint");
		uw.setEventListener(new WaitPaint());
		uw.getUnitMap().clear(Page.P40, 0);
		uw.getMapWorks().repaint();
	}
	public static void setBasicPaint(UnitWorks uw) {
		System.out.println("BasicPaint");
		uw.setEventListener(new BasicPaint(uw));
	}
	public static void setScorePaint(UnitWorks uw) {
		System.out.println("ScorePaint");
		uw.setEventListener(new ScorePaint(uw));
	}
	public static void setPutPlayersPaint(UnitWorks uw, List<Body> charaList, List<Body> playerList) {
		System.out.println("PutPlayersPaint");
		uw.setEventListener(new PutPlayersPaint(uw, charaList, playerList));
	}
	public static void setEndPaint(UnitWorks uw, Body b) {
		System.out.println("EndPaint");
		uw.setEventListener(new EndPaint(uw, b));
	}
	public static void setAttackPaint(UnitWorks uw, FightManager fm, Body b) {
		System.out.println("AttackPaint");
		uw.setEventListener(new AttackPaint(uw, fm, b));
	}
	public static void setKakuseiPaint(UnitWorks uw, Body b) {
		System.out.println("KakuseiPaint");
		uw.setEventListener(new KakuseiPaint(uw, b));
	}
	public static void setBerserkPaint(UnitWorks uw, Body b) {
		System.out.println("BerserkPaint");
		uw.setEventListener(new BerserkPaint(uw, b));
	}
	public static void setChangePaint(UnitWorks uw, Body ba, Body bb) {
		System.out.println("ChangePaint");
		uw.setEventListener(new ChangePaint(uw, ba, bb));
	}
	public static void setButtonPaint(UnitWorks uw, int x, int y, EventListener pl, int type) {
		System.out.println("ButtonPaint");
		uw.setEventListener(new ButtonPaint(uw, x, y, pl, type));
	}
	public static void setCampPaint(UnitWorks uw, Camp camp) {
		System.out.println("CampPaint");
		uw.setEventListener(new CampPaint(uw, camp));
	}

}
