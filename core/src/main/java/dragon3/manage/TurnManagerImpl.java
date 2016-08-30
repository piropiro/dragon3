package dragon3.manage;

import javax.inject.Inject;
import javax.inject.Singleton;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.MoveType;
import dragon3.common.constant.Page;
import dragon3.common.util.MoveUtils;
import dragon3.controller.UnitWorks;
import dragon3.map.StageMap;
import dragon3.paint.PaintUtils;
import dragon3.view.FrameWorks;
import mine.paint.UnitMap;

@Singleton
public class TurnManagerImpl implements TurnManager {

	private int turn;

	UnitWorks uw;

	@Inject StageMap map;
	@Inject AnimeManager anime;

	/*** Constructer ************************************/

	@Inject
	public TurnManagerImpl() {
	}

	/*** Reset *****************************************/

	public void reset() {
		turn = 0;
	}

	/*** Get Data ***************************************/

	public int getTurn() {
		return turn;
	}



	/*** Change *****************************/

	@Override
	public void turnChange(boolean flag) {
		if (flag) {
			turn++;
		}
		UnitMap map = this.map.getMap();
		map.clear(Page.P10, 0);
		map.clear(Page.P30, 0);
		map.clear(Page.P40, 0);
		for (Body b : uw.getCharaList()) {
			if (!b.isAlive())
				continue;
			
			b.resetStore();
			
			b.removeAttr(BodyAttribute.SORA);
			b.removeAttr(BodyAttribute.RIKU);
			setTikei(b, flag);
			setPoison(b, flag);
			setRegene(b, flag);
			setBersek(b, flag);

			setStatus(b, BodyAttribute.ATTACK_UP, BodyAttribute.ATTACK_UP_LOCK);
			setStatus(b, BodyAttribute.GUARD_UP, BodyAttribute.GUARD_UP_LOCK);
			setStatus(b, BodyAttribute.WET, BodyAttribute.WET_LOCK);
			setStatus(b, BodyAttribute.OIL, BodyAttribute.OIL_LOCK);
			setStatus(b, BodyAttribute.CHARM, BodyAttribute.CHARM_LOCK);
			setStatus(b, BodyAttribute.SLEEP, BodyAttribute.SLEEP_LOCK);

			paintStatus(b);
		}
	}

	private void paintStatus(Body b) {
		UnitMap map = this.map.getMap();
		if (b.hasAttr(BodyAttribute.SLEEP)) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_SLEEP);
		}
		if (b.hasAttr(BodyAttribute.POISON)) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_POISON);
		}
		if (b.hasAttr(BodyAttribute.WET)) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_WET);
		}
		if (b.hasAttr(BodyAttribute.OIL)) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_OIL);
		}
		if (b.hasAttr(BodyAttribute.ATTACK_UP)) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_ATTACK_UP);
		}
		if (b.hasAttr(BodyAttribute.GUARD_UP)) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_GUARD_UP);
		}
		if (b.hasAttr(BodyAttribute.SORA)) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_SORA);
		}
		if (b.hasAttr(BodyAttribute.RIKU)) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_RIKU);
		}
		if (b.hasAttr(BodyAttribute.BERSERK)) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_BERSERK);
		}
	}

	private void setStatus(Body b, BodyAttribute type, BodyAttribute lock) {
		if (b.hasAttr(lock)) {
			b.removeAttr(lock);
		} else {
			b.removeAttr(type);
		}
	}

	/*** Tikei ****************************/

	private void setTikei(Body b, boolean flag) {
		UnitMap map = this.map.getMap();
		if (MoveUtils.INSTANCE.getTikei(map, b) == MoveUtils.T_SKY)
			return;

		switch (map.getData(Page.P01, b.getX(), b.getY())) {
			case MoveUtils.C_BLUE :
				blueCrystal(b, flag);
				break;
			case MoveUtils.AQUA :
			case MoveUtils.BLUE :
				waterPanel(b, flag);
				break;
			case MoveUtils.POISONP :
				poisonPanel(b, flag);
				break;
			case MoveUtils.OILP :
				oilPanel(b, flag);
				break;
			case MoveUtils.FIREP :
				firePanel(b, flag);
				break;

		}
	}

	private void blueCrystal(Body b, boolean flag) {
		if (GameColor.Companion.isPlayer(b.getColor()) != flag)
			return;
		if (b.getHp() == b.getHpMax() && !b.hasAttr(BodyAttribute.POISON))
			return;
		b.removeAttr(BodyAttribute.POISON);
		anime.systemAnime(AnimeManager.ID_REFRESH, b.getX(), b.getY());
		b.setHp(b.getHp() + Math.max(2, b.getHpMax() / 4));
		b.setHp(Math.min(b.getHp(), b.getHpMax()));
	}

	/**
	 * 水パネル
	 *
	 * @param b
	 * @param flag
	 */
	private void waterPanel(Body b, boolean flag) {
		if (b.hasAttr(BodyAttribute.ANTI_ALL))
			return;
		if (b.hasAttr(BodyAttribute.SWIM_ABLE))
			return;
		if (b.base.getMoveType().equals(MoveType.SWIM))
			return;
		if (b.base.getMoveType().equals(MoveType.TWIN))
			return;
		b.addAttr(BodyAttribute.WET);
		b.addAttr(BodyAttribute.WET_LOCK);
		return;
	}


	/**
	 * 毒パネル
	 *
	 * @param b
	 * @param flag
	 */
	private void poisonPanel(Body b, boolean flag) {
		if (b.hasAttr(BodyAttribute.ANTI_ALL))
			return;
		if (b.hasAttr(BodyAttribute.ANTI_POISON))
			return;

		b.addAttr(BodyAttribute.POISON);
	}

	/**
	 * オイルパネル
	 *
	 * @param b
	 * @param flag
	 */
	private void oilPanel(Body b, boolean flag) {
		if (b.hasAttr(BodyAttribute.ANTI_ALL))
			return;
		b.addAttr(BodyAttribute.OIL);
		b.addAttr(BodyAttribute.OIL_LOCK);
	}

	/**
	 * 溶岩パネル
	 *
	 * @param b
	 * @param flag
	 */
	private void firePanel(Body b, boolean flag) {
		if (GameColor.Companion.isPlayer(b.getColor()) != flag)
			return;
		if (b.hasAttr(BodyAttribute.FIRE_0))
			return;

		int rate = 4;
		if (b.hasAttr(BodyAttribute.FIRE_200))
			rate *= 2;
		if (b.hasAttr(BodyAttribute.OIL))
			rate *= 2;
		if (b.hasAttr(BodyAttribute.FIRE_50))
			rate /= 2;
		anime.systemAnime(AnimeManager.ID_FIRE, b.getX(), b.getY());
		b.setHp(b.getHp() - Math.max(2, b.getHpMax() * rate / 16));
		b.setHp(Math.max(1, b.getHp()));
	}

	/*** Poison ******************************/

	private void setPoison(Body b, boolean flag) {
		UnitMap map = this.map.getMap();
		if (!b.hasAttr(BodyAttribute.POISON))
			return;
		if (GameColor.Companion.isPlayer(b.getColor()) != flag || b.getHp() == 1) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_POISON);
		} else {
			anime.statusAnime(AnimeManager.STATUS_POISON, b.getX(), b.getY());
			b.setHp(b.getHp() - Math.max(1, b.getHpMax() / 8));
			b.setHp(Math.max(1, b.getHp()));
		}
		if (b.getHp() == 1)
			b.removeAttr(BodyAttribute.POISON);
	}

	/*** Bersek ******************************/

	private void setBersek(Body b, boolean flag) {
		UnitMap map = this.map.getMap();
		if (!b.hasAttr(BodyAttribute.BERSERK))
			return;
		if (GameColor.Companion.isPlayer(b.getColor()) != flag) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_BERSERK);
		} else {
			b.setStr(Math.max(0, b.getStr() - 1));
			b.setDef(Math.max(0, b.getDef() - 1));
			b.setMst(Math.max(0, b.getMst() - 1));
			b.setMdf(Math.max(0, b.getMdf() - 1));
			b.setHit(Math.max(0, b.getHit() - 1));
			b.setMis(Math.max(0, b.getMis() - 1));
			anime.statusAnime(AnimeManager.STATUS_BERSERK, b.getX(), b.getY());
		}
	}

	/*** Heal ********************************/

	private void setRegene(Body b, boolean flag) {
		UnitMap map = this.map.getMap();
		if (!b.hasAttr(BodyAttribute.REGENE))
			return;
		if (GameColor.Companion.isPlayer(b.getColor()) != flag || b.getHp() == b.getHpMax()) {
			map.setData(Page.P50, b.getX(), b.getY(), AnimeManager.STATUS_REGENE);
		} else if (b.getMst() > 0) {
			anime.statusAnime(AnimeManager.STATUS_REGENE, b.getX(), b.getY());
			b.setMst(Math.max(0, b.getMst() - 2));
			b.setHp(Math.min(b.getHpMax(), b.getHp() + b.getHpMax() / 2));
		} else {
			b.removeAttr(BodyAttribute.REGENE);
		}
	}

	@Override
	public void setUw(UnitWorks uw) {
		this.uw = uw;
	}
}
