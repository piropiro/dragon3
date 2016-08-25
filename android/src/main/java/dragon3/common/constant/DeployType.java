/*
 * Created on 2004/06/27
 */
package dragon3.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

/**
 * @author k-saito
 */
public enum DeployType {

	NONE("無", GameColor.NONE),
	ENEMY_CHARA("敵キャラ", GameColor.RED),
	PLAYER_CHARA("味方キャラ", GameColor.BLUE),
	NEUTRAL_CHARA("中立キャラ", GameColor.GREEN),
	SUMMON("召喚キャラ", GameColor.RED),
	BOX_ITEM("宝箱アイテム", GameColor.GREEN),
	CLEAR_ITEM("クリアアイテム", GameColor.GREEN),
	ENEMY_ITEM("敵持ちアイテム", GameColor.GREEN),
	SECRET_ITEM("隠しアイテム", GameColor.GREEN),
	
	;
	
	@Getter private String text;
	@Getter private GameColor color;
	
	DeployType(String text, GameColor color) {
		this.text = text;
		this.color = color;
	}
	
	public static Map<String, String> createMap() {
		Map<String, String> idAndText = new LinkedHashMap<>();
		for (DeployType a : values()) {	
			idAndText.put(a.name(), a.getText());
		}
		return idAndText;
	}

}
