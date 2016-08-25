package dragon3.manage;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Inject;

import dragon3.Statics;
import dragon3.common.Body;
import dragon3.common.constant.BodyKind;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.data.BodyData;
import dragon3.data.load.BodyDataLoader;
import dragon3.image.ImageManager;
import dragon3.panel.PanelManager;

public class SoulManager {

	@Inject Statics statics;
	@Inject BodyDataLoader bodyDataLoader;
	
//	private UnitWorks uw;
	@Inject ImageManager imageManager;
	
	private int count;
	private Body soul;

	@Inject
	public SoulManager() {
		setup();
	}

	public void setup() {
		count = 0;
		soul = null;
	}
	
	public int getCount() {
		return count;
	}

	public Body getSoul(Body body) {
		count++;
		if (count % 6 != 0)
			return null;

		if (GameColor.Companion.isPlayer(body.getColor()))
			return null;
		
		soul = bodyDataLoader.loadBodyData(body.base.getId(), body.getLevel());
		soul.base.setKind(BodyKind.SOUL);
		soul.setColor(GameColor.GREEN);
		soul.base.setImage(soul.base.getSoulType().getImage());
		soul.setImageNum(imageManager.getBodyImageList().getNum(soul.base.getImage()));
		// 技3のみ継承する
		List<String> wazaList = Arrays.asList(soul.base.getWazaList().get(3));
		soul.base.setWazaList(wazaList);
		soul.resetWaza();
		
		switch (soul.base.getSoulType()) {
		case BLUE:
			changeStatus(soul.base, soul.base::getMst, soul.base::setMst);
			break;
		case RED:
			changeStatus(soul.base, soul.base::getStr, soul.base::setStr);
			break;
		case GREEN:
			changeStatus(soul.base, soul.base::getHit, soul.base::setHit);
			break;
		case PINK:
			changeStatus(soul.base, soul.base::getMdf, soul.base::setMdf);
			break;
		case YELLOW:
			changeStatus(soul.base, soul.base::getDef, soul.base::setDef);
			break;
		case SKY:
			changeStatus(soul.base, soul.base::getMis, soul.base::setMis);
			break;
		case ORANGE:
			changeStatus(soul.base, soul.base::getHp, soul.base::setHp);
			break;
		default:
		}
		soul.setMax();
		
		return soul;
	}
		
	public void changeStatus(BodyData base, Supplier<Integer> baseGet, Consumer<Integer> baseSet) {
		int n = baseGet.get() / 10;
		base.setHp(0);
		base.setStr(0);
		base.setDef(0);
		base.setMst(0);
		base.setMdf(0);
		base.setHit(0);
		base.setMis(0);
		baseSet.accept(n);
	}

	public void message(PanelManager pm) {
		if (soul == null)
			return;
		pm.addMessage(soul.getBase().getName() + Texts.ha);
		pm.addMessage(Texts.material1);
		pm.addMessage(soul.getBase().getSoulType().getText() + "色の魂玉を手に入れた。");
		pm.addMessage(Texts.material5);
		pm.startMessage(soul);
		soul = null;
	}

}
