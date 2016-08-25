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
public enum WeponType {
	
	NONE("無", "none.png"),
	SWORD("剣", "waza_sword.png"),
	AX("斧", "waza_ax.png"),
	SPEAR("槍", "waza_spear.png"),
	BOW("弓", "waza_bow.png"),
	KNIFE("小刀", "waza_knife.png"),
	MAGIC("魔法", "waza_magic.png"),
	BODY("体術", "waza_body.png"),
	BREATH("ブレス", "waza_breath.png"),
	HAMMER("ハンマー", "waza_hammer.png"),
	;
	
	@Getter private String text;
	@Getter private String image;
	
	WeponType(String text, String image) {
		this.text = text;
		this.image = image;
	}
	
	public static Map<String, String> createMap() {
		Map<String, String> idAndText = new LinkedHashMap<>();
		for (WeponType a : values()) {	
			idAndText.put(a.name(), a.getText());
		}
		return idAndText;
	}

}
