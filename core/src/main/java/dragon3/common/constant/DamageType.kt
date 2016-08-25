/*
 * Created on 2004/06/27
 */
package dragon3.common.constant

import java.util.LinkedHashMap

import lombok.Getter

/**
 * @author k-saito
 */
enum class DamageType private constructor(
        val text: String) {

    NONE("なし"),
    SWORD("物理攻撃"),
    MAGIC("魔法攻撃"),
    SWORD_ALL("無視物理"),
    MAGIC_ALL("無視魔法");


    companion object {

        fun createMap(): Map<String, String> {
            val idAndText = LinkedHashMap<String, String>()
            for (a in values()) {
                idAndText.put(a.name, a.text)
            }
            return idAndText
        }

        fun convert(n: Int): DamageType {
            when (n) {
                1 -> return SWORD
                2 -> return MAGIC
                5 -> return SWORD_ALL
                6 -> return MAGIC_ALL

                else -> throw IllegalArgumentException("DamageType unmatch: " + n)
            }
        }
    }
}
