package dragon3.common.constant

import java.util.LinkedHashMap

enum class BodyAttribute private constructor(

        val text: String) {

    NONE("NONE"), // 0
    DRAGON("竜族"), // 5
    UNDEAD("死霊"), // 34
    FIRE_200("火炎倍打"), // 6
    FIRE_50("火炎半減"), // 7
    FIRE_0("火炎無効"), // 8
    ICE_200("氷結倍打"), // 9
    ICE_50("氷結半減"), // 10
    ICE_0("氷結無効"), // 11
    THUNDER_200("電撃倍打"), // 12
    THUNDER_50("電撃半減"), // 13
    THUNDER_0("電撃無効"), // 14
    TALKABLE("会話可能"), // 15
    ANTI_ALL("全部無効"), // 17
    ANTI_CRITICAL("必殺無効"), // 18
    ANTI_DEATH("デス無効"), // 19
    ANTI_SLEEP("睡眠無効"), // 22
    ANTI_POISON("毒無効"), // 24
    ANTI_CHARM("魅了無効"), // 28
    SLEEP("睡眠"), // 21
    POISON("毒"), // 23s
    WET("水ぬれ"), // 25
    OIL("油まみれ"), // 26
    CHARM("魅了"), // 27
    REGENE("自然治癒"), // 29
    ATTACK_UP("攻撃UP"), // 30
    GUARD_UP("防御UP"), // 31
    POWERUP("強化"), // 33
    BERSERK("暴走"), // 58
    SORA("飛行"), // 36
    RIKU("歩行"), // 37
    FLY_ABLE("飛行可能"), // 46
    SWIM_ABLE("水中可能"), // 47
    LITE_WALK("軽歩可能"), // 57
    MOVE_UP_1("移動＋１"), // 48
    MOVE_UP_2("移動＋２"), // 49
    MOVE_DOWN_1("移動－１"), // 56
    SISTER("妹"), // 50
    HERO("主人公"), // 51
    DRAGON_KILLER("竜退治"), // 40
    UNDEAD_KILLER("死霊退治"), // 41
    MAGIC_50("魔法半減"), // 45
    SWORD_50("剣斧半減"), // 43
    CHARM_LOCK("魅了ロック"),
    SLEEP_LOCK("睡眠ロック"),
    WET_LOCK("水ぬれロック"),
    OIL_LOCK("オイルロック"),
    ATTACK_UP_LOCK("攻撃UPロック"),
    GUARD_UP_LOCK("防御UPロック"),
    MASTER("魔物使い");


    companion object {

        fun convert(n: Int): BodyAttribute {
            when (n) {
            //case 1: return CLASS;
            //case 2: return WEPON;
            //case 3: return ARMOR;
            //case 4: return ITEM;
                5 -> return DRAGON
                6 -> return FIRE_200
                7 -> return FIRE_50
                8 -> return FIRE_0
                9 -> return ICE_200
                10 -> return ICE_50
                11 -> return ICE_0
                12 -> return THUNDER_200
                13 -> return THUNDER_50
                14 -> return THUNDER_0
                15 -> return TALKABLE
                16 -> return TALKABLE
                17 -> return ANTI_ALL
                18 -> return ANTI_CRITICAL
                19 -> return ANTI_DEATH
            //case 20: return NKILL;
                21 -> return SLEEP
                22 -> return ANTI_SLEEP
                23 -> return POISON
                24 -> return ANTI_POISON
                25 -> return WET
                26 -> return OIL
                27 -> return CHARM
                28 -> return ANTI_CHARM
                29 -> return REGENE
                30 -> return ATTACK_UP
                31 -> return GUARD_UP
            //case 32: return S_WAIT;
            //case 33: return POWERUP;
                34 -> return UNDEAD
            //case 35: return S_LOCK;
                36 -> return SORA
                37 -> return RIKU
            //case 38: return SUMMON;
            //case 39: return DOLL;
                40 -> return DRAGON_KILLER
                41 -> return UNDEAD_KILLER
            //case 42: return MASTER;
                43 -> return SWORD_50
            //case 44: return BADITEM;
                45 -> return MAGIC_50
                46 -> return FLY_ABLE
                47 -> return SWIM_ABLE
                48 -> return MOVE_UP_1
                49 -> return MOVE_UP_2
                50 -> return SISTER
                51 -> return HERO
            //case 52: return WAZA;
            //case 53: return M_RED;
            //case 54: return M_GREEN;
            //case 55: return M_BLUE;
                56 -> return MOVE_DOWN_1
                57 -> return LITE_WALK
                58 -> return BERSERK

                else -> throw IllegalArgumentException("Types unmatch: " + n)
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
}// 42
//CLASS("職業"), // 1
//WEPON("武器"), // 2
//ARMOR("防具"), // 3
//ITEM("小物"), // 4
//ASK("説得可能"), // 16
//NKILL("即死無効"), // 20
//S_WAIT("ウエイト"), // 32
//POWERUP("強化"), // 33
//S_LOCK("ロック"), // 35
//SUMMON("召喚"), // 38
//DOLL("ドール"), // 39
//MASTER("魔物使い"), // 42
//BADITEM("劣化品"), // 44
//WAZA("技"), // 52
//M_RED("赤玉"), // 53
//M_GREEN("緑玉"), // 54
//M_BLUE("青玉"), // 55
