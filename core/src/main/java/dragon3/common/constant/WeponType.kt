/*
 * Created on 2004/06/27
 */
package dragon3.common.constant

import java.util.LinkedHashMap


/**
 * @author k-saito
 */
enum class WeponType private constructor(
        val text: String,
        val image: String) {

    NONE("無", "none.png"),
    SWORD("剣", "waza_sword.png"),
    AX("斧", "waza_ax.png"),
    SPEAR("槍", "waza_spear.png"),
    BOW("弓", "waza_bow.png"),
    KNIFE("小刀", "waza_knife.png"),
    MAGIC("魔法", "waza_magic.png"),
    BODY("体術", "waza_body.png"),
    BREATH("ブレス", "waza_breath.png"),
    HAMMER("ハンマー", "waza_hammer.png");


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
