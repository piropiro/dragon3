package dragon3.common.constant

import java.util.LinkedHashMap

import lombok.Getter

enum class AttackEffect private constructor(

        val text: String) {

    NONE("NONE"), // 0
    DAMAGE_200("威力２倍"), // 1
    DAMAGE_300("威力３倍"), // 2
    HICHU("必中"), // 3
    TAME("移動不可"), // 4
    COUNTER_ABLE("反撃可能"), // 5
    COMBO("連続攻撃"), // 6
    FIRE("火炎属性"), // 7
    ICE("氷結属性"), // 8
    THUNDER("電撃属性"), // 9
    SORA_200("飛行倍打"), // 10
    DRAGON_200("竜族倍打"), // 11
    COUNTER_ONLY("反撃専用"), // 12
    REGENE("回復効果"), // 13
    CRITICAL("一撃必殺"), // 14
    DEATH("デス"), // 15
    SLEEP("睡眠"), // 16
    POISON("毒"), // 17
    WET("濡れ"), // 18
    CHARM("魅了"), // 19
    ATTACK_UP("攻撃上昇"), // 20
    GUARD_UP("防御上昇"), // 21
    NO_ATTACK("攻撃なし"), // 22
    REFRESH("再行動"), // 23
    HIT_4("命中＋４"), // 24
    MISS_4("命中－４"), // 25
    UPPER("打ち上げ"), // 26
    CHOP("打ち下し"), // 27
    HEAL("回復"), // 28
    RIKU_0("地上のみ"), // 29
    MIZU_0("水中のみ"), // 30
    DAMAGE_150("威力1.5倍"), // 31
    UNDEAD_200("死霊倍打"), // 32
    OIL("油まみれ"), // 33
    RIKU_150("地上1.5倍"), // 34
    MIZU_200("水中倍打"), // 35
    MIZU_100("水中普通"), // 36
    HIT_12("命中＋12");


    companion object {

        fun convert(n: Int): AttackEffect {
            when (n) {
                1 -> return DAMAGE_200
                2 -> return DAMAGE_300
                3 -> return HICHU
                4 -> return TAME
                5 -> return COUNTER_ABLE
                6 -> return COMBO
                7 -> return FIRE
                8 -> return ICE
                9 -> return THUNDER
                10 -> return SORA_200
                11 -> return DRAGON_200
                12 -> return COUNTER_ONLY
                13 -> return REGENE
                14 -> return CRITICAL
                15 -> return DEATH
                16 -> return SLEEP
                17 -> return POISON
                18 -> return WET
                19 -> return CHARM
                20 -> return ATTACK_UP
                21 -> return GUARD_UP
                22 -> return NO_ATTACK
                23 -> return REFRESH
                24 -> return HIT_4
                25 -> return MISS_4
                26 -> return UPPER
                27 -> return CHOP
                28 -> return HEAL
                29 -> return RIKU_0
                30 -> return MIZU_0
                31 -> return DAMAGE_150
                32 -> return UNDEAD_200
                33 -> return OIL
                34 -> return RIKU_150
                35 -> return MIZU_200
                36 -> return MIZU_100
                37 -> return HIT_12
                else -> throw IllegalArgumentException("Kinds unmatch: " + n)
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
}// 37
