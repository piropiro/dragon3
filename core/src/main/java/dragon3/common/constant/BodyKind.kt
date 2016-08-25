package dragon3.common.constant

import java.util.LinkedHashMap


enum class BodyKind private constructor(

        val text: String) {

    NONE("無"),
    CHARA("キャラ"),
    CLASS("職業"), // 1
    SOUL("魂玉"),
    WEPON("武器"), // 2
    ARMOR("防具"), // 3
    ITEM("小物"), // 4
    DOLL("ドール"), // 39
    WAZA("技");


    companion object {

        fun createMap(): Map<String, String> {
            val idAndText = LinkedHashMap<String, String>()
            for (a in values()) {
                idAndText.put(a.name, a.text)
            }
            return idAndText
        }
    }
}// 52