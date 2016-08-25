package dragon3.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import dragon3.attack.calc.HitRate;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.DeployType;
import dragon3.common.constant.GameColor;
import dragon3.data.BodyData;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class Body implements Serializable, Cloneable {

	public BodyData base = new BodyData();
	
	private GameColor color = GameColor.NONE;

	private DeployType deployType;
	
	private int hp, hpMax;
	private int str;
	private int def;
	private int mst;
	private int mdf;
	private int hit;
	private int mis;

	private List<String> wazaList = new ArrayList<>();
	
	private Set<BodyAttribute> attrSet = new LinkedHashSet<>();

	private int x;
	private int y;
	private int scope;
	private int range;
	private int limitTurn;
	private int goalX;
	private int goalY;

	private int level;
	private int exp;

	private int store;
	private int imageNum;
	
	private boolean master;

	public Body() {
	}

	public void setMax() {		
		hpMax = base.getHp() / 10;
		hp = hpMax;
		str = base.getStr() / 10;
		def = base.getDef() / 10;
		mst = base.getMst() / 10;
		mdf = base.getMdf() / 10;
		hit = base.getHit() / 10;
		mis = base.getMis() / 10;
		resetStore();
	}

	public boolean isAlive(){
		return (hp > 0);
	}

	public void resetAttr() {
		attrSet.clear();
		attrSet.addAll(base.getAttrList());
	}
	public void addAttr(BodyAttribute attr){
		attrSet.add(attr);
	}
	public void removeAttr(BodyAttribute attr){
		attrSet.remove(attr);
	}
	public boolean hasAttr(BodyAttribute attr){
		return attrSet.contains(attr);
	}
	
	public void resetWaza() {
		//List<String> wazaList = new ArrayList<>(base.getWazaList());
		//wazaList.removeIf(a -> a.equals("none") );
		List<String> wazaList = new ArrayList<>();
		for (String waza : base.getWazaList()) {
			if (!waza.equals("none")) {
				wazaList.add(waza);
			}
		}

		this.wazaList = wazaList;
	}
	
	public void clearWaza() {
		wazaList.clear();
	}
	
	public void restrict() {
		hpMax = Math.min(999, hpMax);
		str = Math.min(999, str);
		def = Math.min(999, def);
		mst = Math.min(999, mst);
		mdf = Math.min(999, mdf);
		hit = Math.min(999, hit);
		mis = Math.min(999, mis);
	}
	
	public int getMoveStep() {
		int step = base.getMoveStep();
		if (hasAttr(BodyAttribute.MOVE_UP_1))
			step++;
		if (hasAttr(BodyAttribute.MOVE_UP_2))
			step += 2;
		if (hasAttr(BodyAttribute.MOVE_DOWN_1))
			step -= 1;
//		if (hasAttr(BodyAttribute.OIL))
//			step = 1;
		if (hasAttr(BodyAttribute.OIL))
			step /= 2;
		return step;
	}
	
	public void resetStore() {
		store = HitRate.SINGLE_HIT / 2;
	}
}
