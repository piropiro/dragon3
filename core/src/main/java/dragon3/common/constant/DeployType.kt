/*
 * Created on 2004/06/27
 */
package dragon3.common.constant

import java.util.LinkedHashMap

import lombok.Getter

/**
 * @author k-saito
 */
enum class DeployType private constructor(
        val text: String,
        val color: GameColor) {

    NONE("無", GameColor.NONE),
    ENEMY_CHARA("敵キャラ", GameColor.RED),
    PLAYER_CHARA("味方キャラ", GameColor.BLUE),
    NEUTRAL_CHARA("中立キャラ", GameColor.GREEN),
    SUMMON("召喚キャラ", GameColor.RED),
    BOX_ITEM("宝箱アイテム", GameColor.GREEN),
    CLEAR_ITEM("クリアアイテム", GameColor.GREEN),
    ENEMY_ITEM("敵持ちアイテム", GameColor.GREEN),
    SECRET_ITEM("隠しアイテム", GameColor.GREEN);


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
