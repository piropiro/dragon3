package dragon3.attack;

import mine.util.Point;
import java.util.List;

import mine.event.SleepManager;
import mine.paint.UnitMap;
import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.common.util.Tutorial;
import dragon3.controller.UnitWorks;
import dragon3.common.constant.BodyAttribute;
import dragon3.map.MapWorks;
import dragon3.paint.PaintUtils;
import dragon3.paint.TalkPaint;
import dragon3.panel.PanelManager;

public class FightManager {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private PanelManager pm;
	private SleepManager sm;
	private AnimeManager anime;

	private Body ba;
	private Body bb;
	private List<String> baWazaList;
	private AttackManager attack;
	private AttackManager counter;
	private int n;

	/*** Constructer ***************************************/

	public FightManager(UnitWorks uw, Body ba) {
		super();
		this.uw = uw;
		this.ba = ba;
		mw = uw.getMapWorks();
		map = uw.getUnitMap();
		pm = uw.getPanelManager();
		sm = uw.getSleepManager();
		anime = uw.getAnimeManager();
		baWazaList = ba.getWazaList();
		n = -1;
		setHelp(false);
	}

	private void setHelp(boolean flag) {
		if (!GameColor.isPlayer(ba))
			return;
		if (flag) {
			pm.displayHelp(mw.getWaku(), GameColor.GREEN, Texts.help[Texts.H_FIGHT1]);
		} else {
			pm.displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_FIGHT2]);
		}
	}

	/*** Select *********************************************/

	public void nextSelect() {
		while (++n < baWazaList.size()) {
			if (select(n, false)) {
				PaintUtils.setAttackPaint(uw, this, ba);
				return;
			}
		}
		TalkPaint tp = new TalkPaint(uw, ba);
		if (tp.isEnable()) {
			mw.setEventListener(tp);
			tp.show();
			mw.repaint();
		} else {
			PaintUtils.setEndPaint(uw, ba);
			mw.repaint();
		}
		pm.closeSmall();
	}

	public boolean select(int i, boolean enemyFlag) {
		if (baWazaList.get(i).equals("none"))
			return false;
		if (i > 0 && ba.hasAttr(BodyAttribute.WET))
			return false;
		
		attack = new AttackManagerImpl(uw, map, ba, baWazaList.get(i), true);
		if (attack.isAlive(enemyFlag)) {
			attack.show();
			//pm.selectHp(true);
			pm.displaySmall(attack.getAttack().getLabel(), attack.getAttack().getLabelColor(), ba);
			mw.repaint();
			return true;
		}
		return false;
	}

	public boolean enemySelect() {
		int n = -1;
		int dmax = -1;
		for (int i = 0; i < baWazaList.size(); i++) {
			if (i > 0 && ba.hasAttr(BodyAttribute.WET))
				break;
			AttackManager ab = new AttackManagerImpl(uw, map, ba, baWazaList.get(i), true);
			if (ab.isAlive(true)) {
				if (dmax < ab.getBestDamage()) {
					dmax = ab.getBestDamage();
					n = i;
				}
			}
		}
		if (n != -1) {
			return select(n, true);
		} else {
			return false;
		}
	}

	/*** Counter ***************************************/

	private void setCounter(Body bb) {
		this.bb = bb;
		counter = null;
		if (!attack.getAttack().hasEffect(AttackEffect.COUNTER_ABLE))
			return;

		List<String> bbWazaList = bb.getWazaList();
		for (int i = bbWazaList.size() - 1; i >= 0; i--) {
			if (bbWazaList.get(i).equals("none"))
				continue;
			AttackManager ab = new AttackManagerImpl(uw, map, bb, bbWazaList.get(i), false);
			if (ab.isCounterable(ba, true)) {
				counter = ab;
				counter.selectTarget(ba);
				break;
			}
		}
		for (int i = 0; i < bbWazaList.size(); i++) {
			if (counter != null)
				break;
			if (bbWazaList.get(i).equals("none"))
				continue;
			AttackManager ab = new AttackManagerImpl(uw, map, bb, bbWazaList.get(i), false);
			if (ab.isCounterable(ba, false)) {
				counter = ab;
				counter.selectTarget(ba);
				break;
			}
		}
		if (counter != null) {
			//pm.selectHp(false);
			counter.selectTarget(ba);
			//pm.selectHp(true);
		}
	}

	public boolean searchTargets() {
		return attack.searchTargets();
	}

	/*** Target ***********************************************************/

	public void setTarget(Point p) {
		Point ps = mw.getWaku();
		mw.wakuMove(p.x, p.y);
		if (map.getData(Page.P10, ps.x, ps.y) == 0) {
			if (map.getData(Page.P10, p.x, p.y) == 0) {
				mw.wakuPaint(true);
			} else {
				map.clear(Page.P40, 0);
				mw.wakuPaint(false);
				attack.setTarget(p.x, p.y);
				mw.repaint();
			}
		} else {
			if (map.getData(Page.P10, p.x, p.y) == 0) {
				map.clear(Page.P40, 0);
				mw.wakuPaint(false);
				mw.repaint();
			} else {
				map.clear(Page.P40, 0);
				mw.wakuPaint(false);
				attack.setTarget(p.x, p.y);
				mw.repaint();
			}
		}

		bb = uw.search(p.x, p.y);
		attack.selectTarget(bb);
		if (uw.isTutorial()) {
			Tutorial.setHelp(ba, bb, n, uw);
		} else {
			setHelp(bb != null);
		}

		if (bb != null && bb != ba) {
			if (!attack.isTarget(bb))
				return;
			setCounter(bb);
			if (counter == null) {
				pm.displayAttack(attack.getAttack(), null);
			} else {
				pm.displayAttack(attack.getAttack(), counter.getAttack());
			}
		} else {
			pm.closeData();
			attack.selectTarget(null);
		}
	}


	/*** Control Enemy **************************************************/

	public void enemy() {
		bb = attack.getBestTarget();
		attack.setTarget(bb.getX(), bb.getY());
		auto();
	}

	private void auto() {
		attack.searchTargets();
		attack.selectTarget(bb);
		pm.displaySmall(attack.getAttack().getLabel(), attack.getAttack().getLabelColor(), ba);
		map.clear(Page.P40, 0);
		mw.repaint();
		setCounter(bb);
		if (bb != null) {
			if (counter == null) {
				pm.displayAttack(attack.getAttack(), null);
			} else {
				pm.displayAttack(attack.getAttack(), counter.getAttack());
			}
		}
		sm.sleep(300);
		attack();
		mw.repaint();
	}

	/*** Counter ******************************************************/

	private void counter() {
		if (counter == null)
			return;
		if (bb == null)
			return;
		if (!bb.isAlive())
			return;
		if (bb.hasAttr(BodyAttribute.SLEEP))
			return;
		pm.displaySmall(counter.getAttack().getLabel(), counter.getAttack().getLabelColor(), bb);
		//pm.selectHp(false);
		counter.attack();
		//pm.selectHp(true);
	}

	/*** Attack **************************************************************/

	public void attack() {
		attack.attack();
		counter();
		pm.closeHp();
		anime.dispose();
		pm.closeSmall();
		pm.closeData();
		uw.levelup(ba);
		uw.levelup(bb);
		uw.message();
		map.clear(Page.P10, 0);
		map.clear(Page.P40, 0);
	}
}
