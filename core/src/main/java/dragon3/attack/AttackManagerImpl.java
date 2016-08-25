package dragon3.attack;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dragon3.Statics;
import dragon3.anime.AnimeManager;
import dragon3.attack.calc.Damage;
import dragon3.attack.calc.DamageRate;
import dragon3.attack.calc.HitRate;
import dragon3.attack.range.BreathRange;
import dragon3.attack.range.DonutRange;
import dragon3.attack.range.LaserRange;
import dragon3.attack.range.NormalRange;
import dragon3.attack.range.Range;
import dragon3.attack.range.SquareRange;
import dragon3.attack.special.SpecialEffectManager;
import dragon3.attack.target.AllTarget;
import dragon3.attack.target.BreathTarget;
import dragon3.attack.target.LaserTarget;
import dragon3.attack.target.PointTarget;
import dragon3.attack.target.SpreadTarget;
import dragon3.attack.target.Target;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.Page;
import dragon3.common.constant.TargetType;
import dragon3.common.util.Luck;
import dragon3.controller.UnitWorks;
import dragon3.data.AnimeData;
import dragon3.data.WazaData;
import dragon3.manage.RewalkManager;
import dragon3.panel.PanelManager;
import mine.event.SleepManager;
import mine.paint.UnitMap;
import mine.util.Point;

public class AttackManagerImpl implements AttackManager {

	private Statics statics;
	
	private AttackImpl attack;

	private AnimeManager anime;
	private SleepManager sm;
	private PanelManager pm;
	private RewalkManager rewalkManager;
	private UnitWorks uw;
	private UnitMap map;
	private SpecialEffectManager se;

	private List<Body> enemy;

	private Body bestEnemy;
	private int bestDamage;

	private Target target;
	private Range range;


	private WazaData waza;
	
	private boolean hpFlag;

	/*** Constructer *********************************************************/

	public AttackManagerImpl(UnitWorks uw,  UnitMap map, Body ba, String wazaId, boolean hpFlag) {
		this.uw = uw;
		this.map = map;
		this.statics = uw.getStatics();
		this.se = uw.getSe();
		this.anime = uw.getAnimeManager();
		this.sm = uw.getSleepManager();
		this.pm = uw.getPanelManager();
		this.rewalkManager = uw.getRewalkManager();
		this.waza = (WazaData)statics.getWazaData(wazaId);
		this.hpFlag = hpFlag;
		this.attack = new AttackImpl(uw.getAnimeManager(), map, ba, waza);

		enemy = null;

		setTargetType(waza.getTargetType(), ba.getX(), ba.getY());

		range.paint(map, Page.P21, ba.getX(), ba.getY());

		getAttackUnit(uw.getCharaList());
	}

	@Override
	public void show() {
		Body ba = attack.getAttacker();
		map.copyPage(Page.P21, Page.P10);
		map.setData(Page.P30, ba.getX(), ba.getY(), 0);
	}

	/*** Set Range and Target ************************************************/

	private void setTargetType(TargetType targetType, int x, int y) {
		switch (targetType) {
		case SINGLE_1:
			range = new NormalRange(1);
			target = new PointTarget();
			break;
		case SINGLE_2:
			range = new NormalRange(2);
			target = new PointTarget();
			break;
		case SINGLE_3:
			range = new NormalRange(3);
			target = new PointTarget();
			break;
		case ALL_1:
			range = new NormalRange(1);
			target = new AllTarget();
			break;
		case ALL_2:
			range = new NormalRange(2);
			target = new AllTarget();
			break;
		case ALL_3:
			range = new NormalRange(3);
			target = new AllTarget();
			break;
		case ALL_4:
			range = new NormalRange(4);
			target = new AllTarget();
			break;
		case LONG_2:
			range = new DonutRange(2, 1);
			target = new PointTarget();
			break;
		case LONG_3:
			range = new DonutRange(3, 1);
			target = new PointTarget();
			break;
		case RING_2:
			range = new DonutRange(2, 1);
			target = new AllTarget();
			break;
		case RING_3:
			range = new DonutRange(3, 1);
			target = new AllTarget();
			break;
		case LASER_2:
			range = new LaserRange(2, 0);
			target = new LaserTarget(2, 0);
			break;
		case LASER_3:
			range = new LaserRange(3, 0);
			target = new LaserTarget(3, 0);
			break;
		case CROSS_2:
			range = new LaserRange(2, 0);
			target = new AllTarget();
			break;
		case CROSS_3:
			range = new LaserRange(3, 0);
			target = new AllTarget();
			break;
		case BREATH:
			range = new BreathRange(2, 0);
			target = new BreathTarget(2, 0);
			break;
		case HLINE:
			range = new BreathRange(2, 1);
			target = new BreathTarget(2, 1);
			break;
		case SQUARE_1:
			range = new SquareRange(1);
			target = new AllTarget();
			break;
		case SPREAD:
			range = new DonutRange(2, 1);
			target = new SpreadTarget(1);
		default:
			throw new IllegalArgumentException("Unknown TargetType:" + targetType);
		}
		target.setBasePoint(x, y);
	}


	// 2-1 Counter Range
	@Override
	public boolean isCounterable(Body bb, boolean flag) {
		Body ba = attack.getAttacker();
		if (ba.hasAttr(BodyAttribute.SLEEP))
			return false;
		if (flag && !attack.hasEffect(AttackEffect.COUNTER_ONLY))
			return false;
		if (!flag && !attack.hasEffect(AttackEffect.COUNTER_ABLE))
			return false;

		if (map.getData(Page.P21, bb.getX(), bb.getY()) == 0)
			return false;
		return true;
	}

	// 2-1 Attack Range
	// 1-1 Search Enemy
	@Override
	public boolean isAlive(boolean enemyFlag) {
		Body ba = attack.getAttacker();
		
		if (attack.hasEffect(AttackEffect.COUNTER_ONLY))
			return false;
		if (attack.hasEffect(AttackEffect.TAME) && rewalkManager.isWalked(ba))
			return false;
		if (!attack.checkEnergy())
			return false;
		
		if (!enemyFlag) {
			int width = map.getMapWidth();
			int height = map.getMapHeight();
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					if (map.getData(Page.P21, x, y) != 0)
						if (map.getData(Page.P11, x, y) != 0)
							return true;
		} else {
			if (bestEnemy != null)
				return true;
		}
		return false;
	}


	// 4-0 Over Frame
	@Override
	public void setTarget(int x, int y) {
		Body ba = attack.getAttacker();
		target.paint(map, Page.P40, x, y);
		map.setData(Page.P40, ba.getX(), ba.getY(), 0);
		map.copyPage(Page.P40, Page.P41);
	}

	@Override
	public void selectTarget(Body bb) {
		attack.setReceiver(bb);
		Body ba = attack.getAttacker();
		
		if (bb == null) {
			pm.closeHp();
		} else {
			if (enemy == null) {
				enemy = new Vector<Body>();
				enemy.add(bb);
			}
			
			
			attack.setMeichu(HitRate.INSTANCE.calcPredict(ba, bb, attack.getEffectSet()));
			pm.displayHp(
				hpFlag,
				bb,
				ba,
				Damage.INSTANCE.calc(waza.getDamageType(), map, ba, bb, attack.getEffectSet())
					* DamageRate.INSTANCE.calc(map, ba, bb, attack.getEffectSet())
					/ 100,
				attack.isHit());
		}
	}

	/*** Deside Enemy *************************************************/

	// 4-0 Over Frame
	@Override
	public boolean searchTargets() {
		Body bb = attack.getReceiver();
		
		if (attack.hasEffect(AttackEffect.COUNTER_ABLE) && bb == null)
			return false;

		enemy = new ArrayList<Body>();
		for (Body b: uw.getCharaList()) {
			if (!isTarget(b))
				continue;
			if (map.getData(Page.P40, b.getX(), b.getY()) != 0) {
				enemy.add(b);
			}
		}
		return (enemy.size() != 0);
	}

	@Override
	public boolean isTarget(Body b) {
		Body ba = attack.getAttacker();
		
		if (b == ba)
			return false;
		if (!b.isAlive())
			return false;

		if (attack.hasEffect(AttackEffect.NO_ATTACK)) {
			if (attack.hasEffect(AttackEffect.HEAL) && b.getColor() != ba.getColor())
				return false;
			if (!attack.hasEffect(AttackEffect.HEAL) && b.getColor() == ba.getColor())
				return false;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.CRITICAL))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.DEATH))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.SLEEP))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.CHARM))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.POISON))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.WET))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.ATTACK_UP))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.GUARD_UP))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.UPPER))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.CHOP))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.REFRESH))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.OIL))
				return true;
			if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.REGENE))
				return true;

			return false;
		}

		if (b.hasAttr(BodyAttribute.CHARM))
			return true;
		int damage = Damage.INSTANCE.calc(waza.getDamageType(), map, ba, b, attack.getEffectSet());
		if (damage == 0) {
			if (attack.hasEffect(AttackEffect.HEAL))
				return (b.getColor() == ba.getColor());
			else
				return (b.getColor() != ba.getColor());
		}
		if (damage < 0) {
			if (b.getHp() >= b.getHpMax())
				return false;
		}
		boolean flag = true;
		if (b.getColor().equals(ba.getColor()))
			flag = !flag;
		if (ba.hasAttr(BodyAttribute.CHARM))
			flag = !flag;
		if (damage < 0)
			flag = !flag;
		return flag;
	}




	@Override
	public Body getBestTarget() {
		return bestEnemy;
	}

	@Override
	public int getBestDamage() {
		return bestDamage;
	}

	@Override
	public Body getAttacker() {
		return attack.getAttacker();
	}

	@Override
	public Body getReceiver() {
		return attack.getReceiver();
	}

	/*** Anime *********************************************/

	private void singleAnime() {
		Body ba = attack.getAttacker();
		Body bb = attack.getReceiver();
		AnimeData data = anime.getData(waza.getAnimeId());
		switch (data.getType()) {
		case SINGLE:
			anime.singleAnime(data, bb.getX(), bb.getY());
			break;
		case SINGLE_ARROW:
			anime.singleArrowAnime(data, ba.getX(), ba.getY(), bb.getX(), bb.getY());
			break;
		default:
		}
	}

	private void allAnime() {
		Body ba = attack.getAttacker();
		AnimeData data = anime.getData(waza.getAnimeId());
		
		switch (data.getType()) {
		case ALL:
			anime.allAnime(data);
			break;
		case ROTATE:
			anime.rotateAnime(data, ba.getX(), ba.getY(), target.getX(), target.getY());
			break;
		case SOME_ARROW:
			anime.someArrowAnime(data, ba.getX(), ba.getY());
			break;
		case LASER_ARROW_2:
			laserAnime(data, 2);
			break;
		case LASER_ARROW_3:
			laserAnime(data, 3);
			break;
		default:
		}
	}

	private void laserAnime(AnimeData data, int length) {
		Body ba = attack.getAttacker();
		Point goal = new Point(target.getX(), target.getY());
		switch (target.getFace(target.getX(), target.getY())) {
			case Target.WEST :
				goal.x = Math.max(0, ba.getX() - length);
				break;
			case Target.EAST :
				goal.x = Math.min(19, ba.getX() + length);
				break;
			case Target.NORTH :
				goal.y = Math.max(0, ba.getY() - length);
				break;
			case Target.SOUTH :
				goal.y = Math.min(14, ba.getY() + length);
				break;
		}
		anime.singleArrowAnime(data, ba.getX(), ba.getY(), goal.x, goal.y);
	}

	/* (non-Javadoc)
	 * @see dragon3.impl.AttackManager#getAttackUnit(java.util.List)
	 */

	// 2-1 Under Frame
	// 1-1 Search Enemy
	@Override
	public void getAttackUnit(List<Body> charaList) {
		Body ba = attack.getAttacker();
		map.clear(Page.P11, 0);
		bestDamage = -1;
		for (Body b: charaList) {
			if (!isTarget(b))
				continue;
			target.setSearchField(map, Page.P11, b.getX(), b.getY());
			if (map.getData(Page.P21, b.getX(), b.getY()) != 0) {
				map.setData(Page.P21, b.getX(), b.getY(), 3);
				int d =
					Damage.INSTANCE.calc(waza.getDamageType(), map, ba, b, attack.getEffectSet()) * DamageRate.INSTANCE.calc(map, ba, b, attack.getEffectSet());

				if (!b.hasAttr(BodyAttribute.CHARM_LOCK) && se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.CHARM))
					d += b.getHp() / 2;

				if (!b.hasAttr(BodyAttribute.SLEEP_LOCK) && se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.SLEEP))
					d += b.getHp() / 2;

				if (se.isEffective(map, ba, b, attack.getEffectSet(), AttackEffect.DEATH))
					d += b.getHp();
				if (b.getColor() == ba.getColor())
					d = -d;
				if (ba.hasAttr(BodyAttribute.CHARM))
					d = -d;
				if (bestDamage < d) {
					bestDamage = d;
					bestEnemy = b;
				}
			}
		}
	}


	/* (non-Javadoc)
	 * @see dragon3.impl.AttackManager#attack()
	 */
	@Override
	public void attack() {
		Body ba = attack.getAttacker();
		Body bb = attack.getReceiver();
		
		allAnime();
		if (bb != null) {
			enemy.remove(bb);
			enemy.add(0, bb);
		} else {
			bb = (Body) enemy.get(0);
			attack.setReceiver(bb);
			pm.displayAttack(attack, null);
		}

		int i = 0;
		for (Body b : enemy) {
			bb = b;
			attack.setReceiver(bb);
			attack.setMeichu(HitRate.INSTANCE.calcReal(ba, bb, attack.getEffectSet()));
			pm.displayHp(
				hpFlag,
				bb,
				ba,
				Damage.INSTANCE.calc(waza.getDamageType(), map, ba, bb, attack.getEffectSet())
					* DamageRate.INSTANCE.calc(map, ba, bb, attack.getEffectSet())
					/ 100,
				attack.isHit());
			if (i > 0)
				pm.displayAttack(attack, null);

			if (map.getData(Page.P30, bb.getX(), bb.getY()) == 2) {
				map.setData(Page.P30, bb.getX(), bb.getY(), 0);
			}

			if (attackMiss()) {
				continue;
			}

			if (se.isEffective(map, ba, bb, attack.getEffectSet(), AttackEffect.CRITICAL) && Luck.INSTANCE.rnd(1, ba) != 1) {
				pm.repaintData();
				pm.damageHp(hpFlag, bb, bb.getHpMax());
				se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.CRITICAL);
				pm.repaintData();
				sm.sleep(200);
				pm.animeHp(hpFlag);
				bb.setHp(0);
				uw.dead(ba, bb);
				anime.dispose();
				continue;
			}

			if (se.isEffective(map, ba, bb, attack.getEffectSet(), AttackEffect.DEATH)) {
				pm.repaintData();
				pm.damageHp(hpFlag, bb, bb.getHpMax());
				se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.DEATH);
				pm.repaintData();
				sm.sleep(200);
				pm.animeHp(hpFlag);
				bb.setHp(0);
				uw.dead(ba, bb);
				anime.dispose();
				continue;
			}

			if (attackHit()) {
				continue;
			}

			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.SLEEP);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.CHARM);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.POISON);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.REGENE);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.WET);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.OIL);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.ATTACK_UP);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.GUARD_UP);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.UPPER);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.CHOP);
			se.execute(map, ba, bb, anime, attack.getEffectSet(), AttackEffect.REFRESH);
			
			sm.sleep(300);
		}
		attack.consumeEnergy();
	}

	/*** Hit *********************************/

	private boolean attackHit() {
		Body ba = attack.getAttacker();
		Body bb = attack.getReceiver();
		if (attack.hasEffect(AttackEffect.NO_ATTACK)) {
			bb.setStore(bb.getStore() + attack.getMeichu());
			bb.setStore(bb.getStore() % HitRate.SINGLE_HIT);
			attack.setMeichu(0);
			pm.repaintData();
			singleAnime();
			return false;
		}

		int damages = 0;
		while (attack.getMeichu() > 0) {
			bb.setStore(bb.getStore() + 1);
			attack.setMeichu(attack.getMeichu() - 1);
			if (bb.getStore() >= HitRate.SINGLE_HIT) {
				pm.repaintData();
				singleAnime();
				int damage =
					Damage.INSTANCE.calc(waza.getDamageType(), map, ba, bb, attack.getEffectSet())
						* DamageRate.INSTANCE.calc(map, ba, bb, attack.getEffectSet())
						/ 100;
				
				damages += damage;
//				if (damage >= 0)
//					damages += Math.max(1, damage + Luck.rnd(1, ba));
//				else
//					damages += Math.min(-1, damage - Luck.rnd(1, ba));
				pm.damageHp(hpFlag, bb, damages);
				anime.numberAnime(damage, bb.getX(), bb.getY());
				bb.setStore(bb.getStore() - HitRate.SINGLE_HIT);
			}
		}
		pm.repaintData();
		sm.sleep(200);
		pm.animeHp(hpFlag);
		bb.setHp(Math.max(0, Math.min(bb.getHp() - damages, bb.getHpMax())));
		if (bb.getHp() <= 0) {
			uw.dead(ba, bb);
			anime.dispose();
			sm.sleep(300);
			return true;
		}
		return false;
	}

	/*** Miss *********************************/

	private boolean attackMiss() {
		Body bb = attack.getReceiver();
		if (attack.getMeichu() + bb.getStore() >= HitRate.SINGLE_HIT)
			return false;

		bb.setStore(bb.getStore() + attack.getMeichu());
		attack.setMeichu(0);
		pm.repaintData();
		singleAnime();
		pm.damageHp(hpFlag, bb, 0);
		anime.dropText(AnimeManager.TEXT_MISS, bb.getX(), bb.getY());
		sm.sleep(500);
		return true;
	}

	@Override
	public Attack getAttack() {
		return attack;
	}
	
}
