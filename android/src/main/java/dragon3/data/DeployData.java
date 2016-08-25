/*
 * 作成日: 2004/03/07
 */
package dragon3.data;

import dragon3.common.constant.DeployType;

/**
 * @author k-saito
 */
@lombok.Data
public class DeployData implements Data {

	private String id;
	private String name;

	private String bodyId = "none";
	private DeployType deployType = DeployType.NONE;
	private int level;
	private int scope;
	private int range;
	private int limitTurn;
	private int x;
	private int y;
	private int goalX;
	private int goalY;


	@Override
	public String toString(){
		return bodyId + " - " + name;
	}
}
