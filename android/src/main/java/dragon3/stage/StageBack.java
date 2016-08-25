package dragon3.stage;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum StageBack {
	WHITE("無地", "back_WHITE.png", "obj_BASIC.png"),
	GRASS("草原", "back_GRASS.png", "obj_BASIC.png"),
	SAND("砂地", "back_SAND.png", "obj_BASIC.png"),
	
	;
	
	@Getter private String text;
	@Getter private String backImage;
	@Getter private String objImage;


	private StageBack(String text, String backImage, String objImage) {
		this.text = text;
		this.backImage = backImage;
		this.objImage = objImage;
	}	
	
	
	public static Map<String, String> createMap() {
		Map<String, String> idAndText = new LinkedHashMap<>();
		for (StageBack a : values()) {	
			idAndText.put(a.name(), a.name());
		}
		return idAndText;
	}


}
