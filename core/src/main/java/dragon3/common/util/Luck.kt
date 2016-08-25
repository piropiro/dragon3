package dragon3.common.util

import java.util.Random

import dragon3.common.Body
import dragon3.common.constant.GameColor


object Luck {

    private var random: Random? = null

    private var luckType = 0

    val FairLuck = 0
    val GoodLuck = 1
    val HardLuck = 2
    val NoneLuck = 3

    fun setup(type: Int) {
        luckType = type
        random = Random()
    }

    fun rnd(max: Int): Int {
        return random!!.nextInt(max + 1)
    }

    fun rnd(width: Int, b: Body): Int {
        when (luckType) {
            FairLuck -> return random!!.nextInt(width)
            GoodLuck -> {
                if (GameColor.isPlayer(b.color))
                    return width
                else
                    return -width
                if (GameColor.isPlayer(b.color))
                    return -width
                else
                    return width
                return 0
            }
            HardLuck -> {
                if (GameColor.isPlayer(b.color))
                    return -width
                else
                    return width
                return 0
            }
            NoneLuck -> return 0
            else -> return 0
        }
    }
}
