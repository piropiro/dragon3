package dragon3.common.constant

import java.util.LinkedHashMap

import lombok.Getter


enum class GameColor private constructor(
        val text: String,
        val bg: IntArray,
        val alphaBg: IntArray,
        val fg: IntArray) {


    NONE("無", intArrayOf(0, 0, 0, 255), intArrayOf(0, 0, 0, 200), intArrayOf(255, 255, 255, 255)),
    BLUE("青", intArrayOf(0, 0, 255, 255), intArrayOf(0, 0, 150, 200), intArrayOf(255, 255, 255, 255)),
    RED("赤", intArrayOf(255, 0, 0, 255), intArrayOf(150, 0, 0, 200), intArrayOf(255, 255, 255, 255)),
    GREEN("緑", intArrayOf(0, 255, 0, 255), intArrayOf(0, 100, 0, 200), intArrayOf(0, 0, 0, 255)),
    YELLOW("黄", intArrayOf(150, 150, 0, 255), intArrayOf(150, 150, 0, 200), intArrayOf(0, 0, 0, 255)),
    WHITE("白", intArrayOf(255, 255, 255, 255), intArrayOf(255, 255, 25, 200), intArrayOf(0, 0, 0, 255)),
    SKY("空", intArrayOf(50, 100, 255, 255), intArrayOf(50, 100, 255, 200), intArrayOf(255, 255, 255, 255));


    companion object {

        fun isPlayer(color: GameColor): Boolean {
            return color == BLUE
        }

        fun isEnemy(color: GameColor): Boolean {
            return color == RED
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
