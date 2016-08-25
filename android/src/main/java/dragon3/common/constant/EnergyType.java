package dragon3.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum EnergyType {

	NONE("消費なし"),
	STR("攻撃消費"),
	DEF("防御消費"),
	MST("魔法消費"),
	MDF("抵抗消費"),
	HIT("命中消費"),
	MIS("回避消費"),
	;
	
	@Getter private String text;
	
	EnergyType(String text) {
		this.text = text;
	}
	
	public static EnergyType convert(int n) {
		switch (n) {
		case 0: return NONE;
		case 1: return STR;
		case 2: return DEF;
		case 3: return MST;
		case 4: return MDF;
		case 5: return HIT;
		case 6: return MIS;

		default:
			throw new IllegalArgumentException("FuelType unmatch: " + n);
		}
	}
	
	public static Map<String, String> createMap() {
		Map<String, String> idAndText = new LinkedHashMap<>();
		for (EnergyType a : values()) {	
			idAndText.put(a.name(), a.getText());
		}
		return idAndText;
	}
}