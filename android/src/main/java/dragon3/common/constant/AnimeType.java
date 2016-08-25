package dragon3.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum AnimeType {

	NONE("無"), // 0
	SINGLE("単体"), // 1
	ALL("全体"), // 2
	SINGLE_ARROW("一人矢"), // 3
	LASER_ARROW_2("貫通矢２"), // 4
	LASER_ARROW_3("貫通矢３"), // 4
	SOME_ARROW("複数矢"), // 5
	ROTATE("回転"), // 6
	;
	
	@Getter private String text;
	
	AnimeType(String text) {
		this.text = text;
	}
	
	public static Map<String, String> createMap() {
//		return Arrays.asList(values()).stream()
//				.collect(Collectors.toMap(AnimeType::name,
//						d -> d.text,
//						(u, v) -> v,
//						LinkedHashMap::new));
		Map<String, String> idAndText = new LinkedHashMap<>();
		for (AnimeType a : values()) {
			idAndText.put(a.name(), a.getText());
		}
		return idAndText;
	}
}