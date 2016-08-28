package dragon3.common.constant

import java.util.LinkedHashMap

enum class EnergyType private constructor(
        val text: String) {

    NONE("消費なし"),
    STR("攻撃消費"),
    DEF("防御消費"),
    MST("魔法消費"),
    MDF("抵抗消費"),
    HIT("命中消費"),
    MIS("回避消費");


    companion object {

        fun convert(n: Int): EnergyType {
            when (n) {
                0 -> return NONE
                1 -> return STR
                2 -> return DEF
                3 -> return MST
                4 -> return MDF
                5 -> return HIT
                6 -> return MIS

                else -> throw IllegalArgumentException("FuelType unmatch: " + n)
            }
        }

        fun createMap(): Map<String, String> {
            val idAndText = LinkedHashMap<String, String>()
            for (a in values()) {
                idAndText.put(a.name, a.text)
            }
            return idAndText
        }
    }
}