package dragon3.attack;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import dragon3.anime.AnimeManager;
import dragon3.attack.calc.Damage;
import dragon3.attack.calc.DamageRate;
import dragon3.attack.calc.HitRate;
import dragon3.attack.special.SpecialEffectManager;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.data.WazaData;
import mine.paint.UnitMap;

public class AttackImpl implements Attack {

	private WazaData waza;

	private UnitMap map;

	private Body ba;
	private Body bb;

	@Inject SpecialEffectManager sem;
	
	private Set<AttackEffect> effectSet;

	private int meichu;

	/*** Constructer *********************************************************/

	public AttackImpl(AnimeManager anime, UnitMap map, Body ba, WazaData waza) {

		this.map = map;
		this.ba = ba;
		this.waza = waza;
		this.effectSet = new HashSet<>(waza.getEffect());

		bb = null;
	}



	/*** Effect ********************************/

	public boolean isEffective(AttackEffect effect) {
		return sem.isEffective(map, ba, bb, effectSet, effect);
	}

	public boolean hasEffect(AttackEffect type) {
		return effectSet.contains(type);
	}

	public Set<AttackEffect> getEffectSet() {
		return effectSet;
	}

	/*** Iconable *************************************************/

	public String getName() {
		return waza.getName();
	}
	public String getLabel() {
		return waza.getLabel();
	}
	public GameColor getLabelColor() {
		return waza.getLabelColor();
	}

	public int getStore() {
		return bb.getStore();
	}

	public int getDamage() {
		return Damage.INSTANCE.calc(waza.getDamageType(), map, ba, bb, effectSet);
	}

	public int getRate() {
		return DamageRate.INSTANCE.calc(map, ba, bb, effectSet);
	}

	public Body getAttacker() {
		return ba;
	}

	public Body getReceiver() {
		return bb;
	}

	
	public void setReceiver(Body bb) {
		this.bb = bb;
	}



	public boolean isHit() {
		if (hasEffect(AttackEffect.HICHU))
			return true;
		if (bb.hasAttr(BodyAttribute.SLEEP))
			return true;
		if (bb.hasAttr(BodyAttribute.RIKU))
			return true;
		int hit = HitRate.INSTANCE.calcPredict(ba, bb, effectSet);
		if (hit + bb.getStore() >= HitRate.SINGLE_HIT)
			return true;
		return false;
	}
	
	/*** Energy *****************************/
	
	public boolean checkEnergy() {
		switch (waza.getEnergyType()) {
		case STR:
			return waza.getEnergyCost() <= ba.getStr();
		case DEF:
			return waza.getEnergyCost() <= ba.getDef();
		case MST:
			return waza.getEnergyCost() <= ba.getMst();
		case MDF:
			return waza.getEnergyCost() <= ba.getMdf();
		case HIT:
			return waza.getEnergyCost() <= ba.getHit();
		case MIS:
			return waza.getEnergyCost() <= ba.getMis();
		default:
			return true;
		}
	}
	
	public void consumeEnergy() {
		switch (waza.getEnergyType()) {
		case STR:
			ba.setStr(ba.getStr() - waza.getEnergyCost());
			break;
		case DEF:
			ba.setDef(ba.getDef() - waza.getEnergyCost());
			break;
		case MST:
			ba.setMst(ba.getMst() - waza.getEnergyCost());
			break;
		case MDF:
			ba.setMdf(ba.getMdf() - waza.getEnergyCost());
			break;
		case HIT:
			ba.setHit(ba.getHit() - waza.getEnergyCost());
			break;
		case MIS:
			ba.setMis(ba.getMis() - waza.getEnergyCost());
			break;
		default:
		}
	}

	@Override
	public int getMeichu() {
		return meichu;
	}

	@Override
	public void setMeichu(int meichu) {
		this.meichu = meichu;
	}
}
