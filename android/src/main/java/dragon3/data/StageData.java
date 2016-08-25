/*
 * 作成日: 2004/03/07
 */
package dragon3.data;

import dragon3.stage.StageBack;

/**
 * @author k-saito
 */
@lombok.Data
public class StageData implements Data {

	private String id = "none";
	private String name = "none";
	private int level;
	private StageBack back = StageBack.WHITE;

	@Override
	public String toString(){
		return id + " - " + name;
	}
}
