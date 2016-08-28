package dragon3.common.constant

import java.util.LinkedHashMap

enum class AnimeType private constructor(

        val text: String) {

    NONE("無"), // 0
    SINGLE("単体"), // 1
    ALL("全体"), // 2
    SINGLE_ARROW("一人矢"), // 3
    LASER_ARROW_2("貫通矢２"), // 4
    LASER_ARROW_3("貫通矢３"), // 4
    SOME_ARROW("複数矢"), // 5
    ROTATE("回転");

    companion object {

        fun createMap(): Map<String, String> {
            val idAndText = LinkedHashMap<String, String>()
            for (a in AnimeType.values()) {
                idAndText.put(a.name, a.text)
            }
            return idAndText
        }
    }
}