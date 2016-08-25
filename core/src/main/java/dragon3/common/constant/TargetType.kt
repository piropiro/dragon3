package dragon3.common.constant

import java.util.LinkedHashMap

import lombok.Getter

enum class TargetType private constructor(
        val text: String,
        val rangeType: Int,
        val rangeN1: Int,
        val rangeN2: Int,
        val targetType: Int,
        val targetN1: Int,
        val targetN2: Int) {

    NONE("無", 0, 0, 0, 0, 0, 0),
    SINGLE_1("単体１", 0, 1, 0, 0, 0, 0), // 0
    SINGLE_2("単体２", 0, 2, 0, 0, 0, 0), // 1
    SINGLE_3("単体３", 0, 3, 0, 0, 0, 0), // 2
    ALL_1("全体１", 1, 1, 0, 1, 0, 0), // 3
    ALL_2("全体２", 1, 2, 0, 1, 0, 0), // 4
    ALL_3("全体３", 1, 3, 0, 1, 0, 0), // 5
    ALL_4("全体４", 1, 4, 0, 1, 0, 0), // 6
    LONG_2("遠距離２", 1, 2, 1, 0, 0, 0), // 7
    LONG_3("遠距離３", 1, 3, 1, 0, 0, 0), // 8
    RING_2("リング２", 1, 2, 1, 1, 0, 0), // 9
    RING_3("リング３", 1, 3, 1, 1, 0, 0), // 10
    LASER_2("直線２", 3, 2, 0, 2, 2, 0), // 11
    LASER_3("直線３", 3, 3, 0, 2, 3, 0), // 12
    CROSS_2("十字２", 3, 2, 0, 1, 0, 0), // 13
    CROSS_3("十字３", 3, 3, 0, 1, 0, 0), // 14
    BREATH("ブレス", 4, 2, 0, 4, 2, 0), // 15
    HLINE("ライン", 4, 2, 1, 4, 2, 1), // 16
    SQUARE_1("回転", 2, 1, 0, 1, 0, 0), // 17
    SPREAD("拡散", 1, 2, 1, 3, 1, 0);


    companion object {

        fun convert(n: Int): TargetType {
            when (n) {
                0 -> return SINGLE_1
                1 -> return SINGLE_2
                2 -> return SINGLE_3
                3 -> return ALL_1
                4 -> return ALL_2
                5 -> return ALL_3
                6 -> return ALL_4
                7 -> return LONG_2
                8 -> return LONG_3
                9 -> return RING_2
                10 -> return RING_3
                11 -> return LASER_2
                12 -> return LASER_3
                13 -> return CROSS_2
                14 -> return CROSS_3
                15 -> return BREATH
                16 -> return HLINE
                17 -> return SQUARE_1
                18 -> return SPREAD
                else -> throw IllegalArgumentException("TargetType unmatch: " + n)
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
}// 18
