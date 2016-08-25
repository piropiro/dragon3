package dragon3.cpu;

import javax.inject.Inject;
import javax.inject.Singleton;

import dragon3.attack.FightManager;
import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.controller.UnitWorks;
import dragon3.manage.RewalkManager;
import dragon3.map.MapWorks;
import dragon3.map.StageMap;
import dragon3.paint.WalkPaint;
import dragon3.panel.PanelManager;
import mine.event.SleepManager;
import mine.paint.UnitMap;
import mine.util.Point;

@Singleton
public class EnemyTurn {

	UnitWorks uw;
	@Inject FightManager fm;
	@Inject MapWorks mw;
	@Inject StageMap map;
	@Inject PanelManager pm;
	@Inject SleepManager sm;
	@Inject RewalkManager rewalkManager;

	
	private Body ba;
	
	

	/*** Constructer ******************************************/

	@Inject
	public EnemyTurn() {
//		this.uw = uw;
//		mw = uw.getMapWorks();
//		map = uw.getUnitMap();
//		pm = uw.getPanelManager();
//		sm = uw.getSleepManager();
//		rewalkManager = uw.getRewalkManager();
//		charaList = uw.getCharaList();
	}

	/*** Main *********************************************/

	public void start(int turn) {
		pm.displayLarge("Enemy Turn " + turn, GameColor.RED, 1000);
		pm.closeHelp();
		pm.closeData();
		sm.sleep(300);
		for (Body b : uw.getCharaList()) {
			ba = b;
			if (!ba.isAlive())
				continue;
			if (ba.hasAttr(BodyAttribute.SLEEP))
				continue;
			if (ba.hasAttr(BodyAttribute.BERSERK)) {
			} else if (GameColor.Companion.isPlayer(ba.getColor())) {
				if (!ba.hasAttr(BodyAttribute.CHARM))
					continue;
			} else {
				if (ba.hasAttr(BodyAttribute.CHARM))
					continue;
			}
			move();
			if (uw.endJudge(ba)) {
				return;
			}
		}
		uw.getTurnManager().playerTurnStart();
	}

	public void move() {
		UnitMap map = this.map.getMap();
		rewalkManager.set(ba);

		boolean actionf = false;

		actionf |= walk();
		actionf |= attack();

		map.clear(Page.P10, 0);
		map.clear(Page.P40, 0);
		if (actionf) {
			mw.repaint();
			sm.sleep(200);
		}
	}

	/*** Attack *****************************************************/

	public boolean attack() {
		UnitMap map = this.map.getMap();
		fm.setup(ba);

		if (fm.enemySelect()) {
			map.setData(Page.P10, ba.getX(), ba.getY(), 4);
			fm.enemy();
			return true;
		}
		return false;
	}

	/*** Move *******************************************************/

	// 0-2 Move Data ( Chara Ari )
	// 0-3 Move Data Result ( Chara Ari )
	// 1-0 Under Frame
	// 1-1 Enemy Search
	// 1-2 Move Data ( Chara Nasi )
	// 1-3 Move Data Result ( Chara Nasi )

	public boolean walk() {
		UnitMap map = this.map.getMap();
		WalkPaint walk = new WalkPaint(uw, ba);
		map.setData(Page.P10, ba.getX(), ba.getY(), 4);
		map.setData(Page.P40, ba.getX(), ba.getY(), 4);
		setSearchData();
		if (!isWalkable())
			return false;
		setWalkData();
		Point f = getWalkPoint();
		if (ba.getX() == f.x && ba.getY() == f.y)
			return false;
		mw.repaint();
		sm.sleep(300);
		map.setData(Page.P10, ba.getX(), ba.getY(), 1);
		map.setData(Page.P40, ba.getX(), ba.getY(), 0);
		walk.enemy(f.x, f.y);
		return true;
	}

	/*** Create Move Data ******************************/

	// Decide Move Priority

	public void setSearchData() {
		UnitMap map = this.map.getMap();
		map.clear(Page.P11, 0);

		map.paintStep(Page.P02, Page.P03, ba.getX(), ba.getY(), 100);
		// Step Paint ( Chara Ari )
		map.paintStep(Page.P12, Page.P13, ba.getX(), ba.getY(), 100);
		// Step Paint ( Chara Nasi )

		// Go to Crystal
		Point p;
		if (ba.getColor().equals(GameColor.BLUE)) {
			p = uw.getCrystal(GameColor.RED);
		} else if (ba.getColor().equals(GameColor.RED)) {
			p = uw.getCrystal(GameColor.BLUE);
		} else {
			p = null;
		}

		if (!GameColor.Companion.isPlayer(ba.getColor()) && p != null) {
			if (map.getData(Page.P10, p.x, p.y) != 0) {
				map.setData(Page.P11, p.x, p.y, 3);
				return;
			}
		}

		// Go to Goal
		if (!GameColor.Companion.isPlayer(ba.getColor()) && (ba.getGoalX() != 0 || ba.getGoalY() != 0)) {
			map.fillDia(Page.P11, ba.getGoalX(), ba.getGoalY(), 1, 2);
			map.setData(Page.P11, ba.getGoalX(), ba.getGoalY(), 3);
			return;
		}

		// Priority 2
		for (Body b : uw.getCharaList()) {
			if (!b.isAlive())
				continue;
			if (b == ba)
				continue;
			if (ba.hasAttr(BodyAttribute.CHARM)) {
				if (b.getColor() != ba.getColor())
					continue;
			} else {
				if (b.getColor() == ba.getColor())
					continue;
			}
			map.fillDia(Page.P11, b.getX(), b.getY(), ba.getScope() - 1, 2);
		}

		// Priority 3
		for (Body b : uw.getCharaList()) {
			if (!b.isAlive())
				continue;
			if (b == ba)
				continue;
			if (ba.hasAttr(BodyAttribute.CHARM)) {
				if (b.getColor() != ba.getColor())
					continue;
			} else {
				if (b.getColor() == ba.getColor())
					continue;
			}
			map.drawDia(Page.P11, b.getX(), b.getY(), ba.getScope(), 3);
		}

		// Priority 1
		for (Body b : uw.getCharaList()) {
			if (!b.isAlive())
				continue;
			if (b == ba)
				continue;
			if (ba.hasAttr(BodyAttribute.CHARM)) {
				if (b.getColor() == ba.getColor())
					continue;
			} else {
				if (b.getColor() != ba.getColor())
					continue;
			}
			if (map.getData(Page.P11, b.getX(), b.getY()) != 0) {
				map.setData(Page.P11, b.getX(), b.getY(), 1);
			}
		}
	}

	/*** Search Enemy ******************************************/

	public boolean isWalkable() {
		UnitMap map = this.map.getMap();
		int maaih = 255; // Minimum Step

		int width = map.getMapWidth();
		int height = map.getMapHeight();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (map.getData(Page.P11, x, y) != 0) {
					maaih = Math.min(maaih, map.getData(Page.P13, x, y));
				}
			}
		}
		if (GameColor.Companion.isPlayer(ba.getColor()))
			return true;
		if (ba.getRange() == 0)
			return true;
		if (maaih + ba.getScope() <= ba.getRange() + 1)
			return true;
		if (uw.getTurnManager().getTurn() >= ba.getLimitTurn())
			return true;
		return false;
	}

	/*** Step Paint ******************************************/

	public void setWalkData() {
		UnitMap map = this.map.getMap();

		int x1 = ba.getX(); // Goal X1 ( Chara Ari )
		int y1 = ba.getY(); // Goal Y1
		int h1 = 255; // Step 1
		int x2 = ba.getX(); // Goal X2 ( Chara Nasi )
		int y2 = ba.getY(); // Goal Y2
		int h2 = 255; // Step 2
		int x3 = ba.getX(); // Goal X3
		int y3 = ba.getY(); // Goal Y3
		int h3 = 255; // Step 3

		int h;

		int width = map.getMapWidth();
		int height = map.getMapHeight();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int d = map.getData(Page.P11, x, y);
				if (d > 1) {
					h = map.getData(Page.P03, x, y);
					if (h < h1) {
						h1 = h;
						x1 = x;
						y1 = y;
					}
					h = map.getData(Page.P13, x, y);
					if (h < h2) {
						h2 = h;
						x2 = x;
						y2 = y;
					}
				}
				if (d == 3) {
					h = map.getData(Page.P03, x, y);
					if (h < h3) {
						h3 = h;
						x3 = x;
						y3 = y;
					}
				}
			}
		}
		int ido = ba.getMoveStep();

		if (h3 <= ido + 1) {
			map.paintStep(Page.P02, Page.P03, x3, y3, 100);
		} else if (h1 < h2 + ido + 1) {
			map.paintStep(Page.P02, Page.P03, x1, y1, 100);
		} else if (h2 < 100) {
			map.paintStep(Page.P12, Page.P13, x2, y2, 100);
			map.copyPage(Page.P13, Page.P03);
		}
	}

	/*** Decide Goal *******************************************/

	public Point getWalkPoint() {
		UnitMap map = this.map.getMap();
		int xf = ba.getX(); // Goal X
		int yf = ba.getY(); // Goal Y
		int hf = 255; // Step

		int h;

		int width = map.getMapWidth();
		int height = map.getMapHeight();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (map.getData(Page.P10, x, y) != 0) {
					h = map.getData(Page.P03, x, y);
					if (h < hf) {
						hf = h;
						xf = x;
						yf = y;
					}
				}
			}
		}
		return new Point(xf, yf);
	}

	public void setUw(UnitWorks uw) {
		this.uw = uw;
	}
}
