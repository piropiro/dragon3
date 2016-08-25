package dragon3.manage;

import mine.util.Point;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.common.util.MoveUtils;
import dragon3.controller.UnitWorks;
import dragon3.map.StageMap;
import dragon3.panel.PanelManager;
import lombok.Setter;
import mine.event.SleepManager;
import mine.paint.UnitMap;

@Singleton
public class TreasureManagerImpl implements TreasureManager {

	private static final int MAX = 30;

	private Body clearItem;
	private Body[] treasure;
	private Body[] holder;
	private int[] status;


	static final int S_NONE = 0;
	static final int S_ENEMY = 1;
	static final int S_GROUND = 2;
	static final int S_BOX = 3;
	static final int S_CLEAR = 4;
	static final int S_HAVE = 5;

	private List<Body> sources;
	private List<Integer> comments;


	@Setter UnitWorks uw;
	@Inject StageMap map;
	@Inject AnimeManager anime;
	@Inject SleepManager sm;

	/*** Constructer *********************************************/

	@Inject
	public TreasureManagerImpl() {
	}
	
	@Override
	public void clean() {
		clearItem = null;
		treasure = new Body[MAX];
		holder = new Body[MAX];
		status = new int[MAX];
		sources = new ArrayList<>();
		comments = new ArrayList<>();
	}
	
	@Override
	public void setup(List<Body> Charas) {
		clean();
		int n = 0;
		for (Body b : Charas) {
			if (uw.have(b)) {
				b.setHp(0);
				continue;
			}
			
			switch (b.getDeployType()) {
			case CLEAR_ITEM:
				clearItem = b;
				b.setHp(0);
				status[n] = S_CLEAR;
				n++;
				continue;
			case ENEMY_ITEM:
			case SECRET_ITEM:
			case BOX_ITEM:
				treasure[n] = b;
				b.setHp(0);
				n++;
				break;
			default:
				continue;
			}
		}

		for (int i = 0; i < n; i++) {
			Body tre = treasure[i];
			if (tre == null)
				continue;
			if (tre.getGoalX() == 0 && tre.getGoalY() == 0) {
				continue;
			}
			status[i] = S_GROUND;
			for (Body b : Charas) {
				if (b == tre)
					continue;
				if (!b.isAlive())
					continue;
				if (b.getX() != tre.getGoalX())
					continue;
				if (b.getY() != tre.getGoalY())
					continue;
				holder[i] = b;
				status[i] = S_ENEMY;
				continue;
			}
		}
		for (int i = 0; i < n; i++) {
			if (holder[i] != null)
				continue;
			Body tre = treasure[i];
			if (tre == null)
				continue;
			if (tre.getLimitTurn() == 0)
				continue;
			map.getMap().setData(Page.P01, tre.getX(), tre.getY(), MoveUtils.CLOSE_BOX);
			status[i] = S_BOX;
		}
	}
	

	@Override
	public int getLimitTurn(Point p) {
		for (int i = 0; i < treasure.length; i++) {
			Body tre = treasure[i];
			if (tre == null)
				continue;
			if (holder[i] != null)
				continue;
			if (p.x != tre.getX())
				continue;
			if (p.y != tre.getY())
				continue;
			return tre.getLimitTurn();
		}
		return 0;
	}

	@Override
	public int getLimitTurn() {
		if (clearItem == null)
			return 0;
		return clearItem.getLimitTurn();
	}

	@Override
	public String getCount() {
		String count = "";

		for (int i = 0; i < treasure.length; i++) {
			switch (status[i]) {
				case S_NONE :
					break;
				case S_ENEMY :
					if (holder[i].isAlive() && isAlive(treasure[i])) {
						count = count + Texts.kigo[0];
					} else {
						count = count + Texts.kigo[1];
					}
					break;
				case S_GROUND :
					if (isAlive(treasure[i])) {
						count = count + Texts.kigo[2];
					} else {
						count = count + Texts.kigo[3];
					}
					break;
				case S_BOX :
					if (isAlive(treasure[i])) {
						count = count + Texts.kigo[4];
					} else {
						count = count + Texts.kigo[5];
					}
					break;
				case S_CLEAR :
					if (isAlive(clearItem)) {
						count = count + Texts.kigo[6];
					} else {
						count = count + Texts.kigo[7];
					}
					break;
				case S_HAVE :
					count = count + Texts.kigo[8];
					break;
			}
		}
		return count;
	}

	@Override
	public List<Body> getSources() {
		return sources;
	}

	@Override
	public void add(Body ba) {
		sources.add(ba);
		uw.getSaveManager().getSaveData().countItem();
	}

	@Override
	public void limitOver() {
		UnitMap map = this.map.getMap();
		for (int i = 0; i < treasure.length; i++) {
			Body tre = treasure[i];
			if (tre == null)
				continue;
			if (tre.getLimitTurn() != uw.getTurnManager().getTurn())
				continue;
			if (map.getData(Page.P01, tre.getX(), tre.getY()) != MoveUtils.CLOSE_BOX)
				continue;
			map.setData(Page.P01, tre.getX(), tre.getY(), MoveUtils.BROKEN_BOX);
			anime.statusAnime(AnimeManager.STATUS_HAMMER, tre.getX(), tre.getY());
			sm.sleep(300);
			map.setData(Page.P50, tre.getX(), tre.getY(), 0);
		}
	}

	@Override
	public void addMember(PanelManager pm, Body ba) {
		add(ba);
		getTreasure(ba, true);
		message(pm);
	}

	// flag true  Nakama
	//      false Kill
	@Override
	public void getTreasure(Body ba, boolean flag) {
		for (int i = 0; i < holder.length; i++) {
			if (ba == holder[i]) {
				Body tre = treasure[i];
				if (!isAlive(tre))
					continue;
				if (tre.getLimitTurn() == 0 && !flag)
					continue;
				add(tre);
				comments.add(new Integer(i));
				status[i] = S_HAVE;
			}
		}
	}

	@Override
	public void searchTreasure(PanelManager pm, Body ba) {
		UnitMap map = this.map.getMap();
		for (int i = 0; i < treasure.length; i++) {
			Body tre = treasure[i];
			if (!isAlive(tre))
				continue;
			if (holder[i] != null)
				continue;
			if (tre.getX() != ba.getX())
				continue;
			if (tre.getY() != ba.getY())
				continue;
			treasure[i] = null;
			status[i] = S_HAVE;
			message(pm, ba, tre);
			add(tre);
			if (map.getData(Page.P01, ba.getX(), ba.getY()) == MoveUtils.CLOSE_BOX)
				map.setData(Page.P01, ba.getX(), ba.getY(), MoveUtils.OPEN_BOX);
		}
	}

	@Override
	public void getClearItem(PanelManager pm) {
		if (!isAlive(clearItem))
			return;
		message(pm, null, clearItem);
		add(clearItem);
	}

	/*** Alive *************************************/

	private boolean isAlive(Body b) {
		if (b == null)
			return false;
		if (b.getLimitTurn() == 0)
			return true;
		if (b.getLimitTurn() < uw.getTurnManager().getTurn())
			return false;
		return true;
	}

	@Override
	public void message(PanelManager pm) {
		for (Integer n : comments) {
			Body ba = holder[n.intValue()];
			Body tre = treasure[n.intValue()];
			pm.addMessage(ba.base.getName() + Texts.ha);
			pm.addMessage(Texts.treasure1);
			pm.addMessage(tre.base.getName() + Texts.wo);
			pm.addMessage(Texts.treasure2);
			pm.closeData();
			pm.startMessage(tre);
		}
		comments.clear();
	}

	@Override
	public void message(PanelManager pm, Body ba, Body tre) {
		if (ba != null) {
			if (ba.base.getName().length() <= 2) {
				pm.addMessage(ba.base.getName() + Texts.ha + Texts.treasure3);
			} else {
				pm.addMessage(ba.base.getName() + Texts.ha);
				pm.addMessage(Texts.treasure3);
			}
		} else {
			pm.addMessage(Texts.treasure3);
		}
		pm.addMessage(tre.base.getName() + Texts.wo);
		pm.addMessage(Texts.treasure2);
		pm.closeData();
		pm.startMessage(tre);
	}
}
