/*
 * Created on 2004/06/27
 */
package dragon3.common.constant

import java.util.LinkedHashMap

import lombok.Getter

/**
 * @author k-saito
 */
enum class SoulType private constructor(
        val text: String,
        val image: String) {

    NONE("無", "none.png"),
    RED("赤", "soul_red.png"),
    BLUE("青", "soul_blue.png"),
    YELLOW("黄", "soul_yellow.png"),
    GREEN("緑", "soul_green.png"),
    PINK("桃", "soul_pink.png"),
    ORANGE("橙", "soul_orange.png"),
    SKY("空", "soul_sky.png");


    companion object {

        fun createMap(): Map<String, String> {
            val idAndText = LinkedHashMap<String, String>()
            for (a in values()) {
                idAndText.put(a.name, a.text)
            }
            return idAndText
        }
    }

}
