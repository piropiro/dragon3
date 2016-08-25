package dragon3.stage

import java.util.LinkedHashMap


enum class StageBack private constructor(
        val text: String,
        val backImage: String,
        val objImage: String) {
    WHITE("無地", "back_WHITE.png", "obj_BASIC.png"),
    GRASS("草原", "back_GRASS.png", "obj_BASIC.png"),
    SAND("砂地", "back_SAND.png", "obj_BASIC.png");


    companion object {


        fun createMap(): Map<String, String> {
            val idAndText = LinkedHashMap<String, String>()
            for (a in values()) {
                idAndText.put(a.name, a.name)
            }
            return idAndText
        }
    }


}
