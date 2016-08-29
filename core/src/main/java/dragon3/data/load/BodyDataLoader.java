package dragon3.data.load;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dragon3.Statics;
import dragon3.common.Body;
import dragon3.data.BodyData;
import dragon3.data.DeployData;

@Singleton
public class BodyDataLoader {
	
	@Inject Statics statics;
	
	@Inject
	public BodyDataLoader() {
	}

	public List<Body> loadBodyDataList(String stageId, int addLevel) {
		
		List<Body> enemyList = new ArrayList<>();
		
		List<DeployData> deployList = statics.getDeployData(stageId);
		for (DeployData deploy : deployList) {
			
			Body body = loadBodyData(deploy.getBodyId(), deploy.getLevel() + addLevel);
		
			body.setDeployType(deploy.getDeployType());
			body.setColor(deploy.getDeployType().getColor());
			body.setScope(deploy.getScope());
			body.setRange(deploy.getRange());
			body.setLimitTurn(deploy.getLimitTurn());
			body.setX(deploy.getX());
			body.setY(deploy.getY());
			body.setGoalX(deploy.getGoalX());
			body.setGoalY(deploy.getGoalY());
			
			System.out.println(body);
			
			enemyList.add(body);
		}

		return enemyList;
	}
	
	/**
	 * BodyDataはLv.10時点のステータスを表している。
	 * レベルアップ時の上昇率を1.1倍で計算する。
	 * 
	 * @param body
	 * @param level
	 */
	private void calcLevel(Body body, int level) {
		System.out.print("level=" + level);
		System.out.print(" str=" + body.base.getStr());
		
		double rate = Math.pow(1.1, level - 10);
		body.base.setHp((int)(body.base.getHp()   * 10 * rate + 5));
		body.base.setStr((int)(body.base.getStr() * 10 * rate + 5));
		body.base.setDef((int)(body.base.getDef() * 10 * rate + 5));
		body.base.setMst((int)(body.base.getMst() * 10 * rate + 5));
		body.base.setMdf((int)(body.base.getMdf() * 10 * rate + 5));
		body.base.setHit((int)(body.base.getHit() * 10 * rate + 5));
		body.base.setMis((int)(body.base.getMis() * 10 * rate + 5));
		
		System.out.print(" baseStr=" + body.base.getStr());
		
		body.setLevel(level);
		body.setMax();
		
		System.out.println(" str=" + body.getStr());
	}

	@NotNull
	public Body loadBodyData(String bodyId, int level) {
		Body body = new Body();
		
		BodyData bodyData = statics.getBodyData(bodyId);
		body.base = bodyData.copy();
		calcLevel(body, level);			
		body.resetAttr();			
		body.resetWaza();
		
		return body;
	}
}
