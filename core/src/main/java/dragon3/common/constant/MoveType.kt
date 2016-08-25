package dragon3.common.constant

import java.util.LinkedHashMap

enum class MoveType private constructor(
        val text: String,
        val steps: IntArray) {

    NONE("移動", intArrayOf(1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9)), // 0
    FLY("飛行", intArrayOf(1, 1, 1, 1, 1, 99, 1, 1, 1, 1, 1)), // 1
    HEAVY("重歩", intArrayOf(1, 3, 99, 6, 99, 99, 1, 2, 2, 2, 99)), // 2
    LITE("軽歩", intArrayOf(1, 1, 99, 3, 99, 99, 1, 2, 2, 2, 99)), // 3
    SWIM("水中", intArrayOf(99, 99, 99, 1, 1, 99, 1, 1, 1, 1, 99)), // 4
    TWIN("水陸", intArrayOf(2, 6, 99, 1, 1, 99, 1, 1, 1, 1, 99)), // 5
    HOVER("浮遊", intArrayOf(1, 1, 99, 1, 1, 99, 1, 1, 1, 1, 99)); // 6


    companion object {

        fun convert(n: Int): MoveType {
            when (n) {
                0 -> return NONE
                1 -> return FLY
                2 -> return HEAVY
                3 -> return LITE
                4 -> return SWIM
                5 -> return TWIN
                6 -> return HOVER

                else -> throw IllegalArgumentException("MoveType unmatch: " + n)
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
