/*
 * Created on 2004/06/27
 */
package dragon3.common.constant

import java.util.LinkedHashMap

/**
 * @author k-saito
 */
enum class ArmorType private constructor(
        val text: String) {

    NONE("無"),
    LITE("軽量"),
    HEAVY("重量");


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
