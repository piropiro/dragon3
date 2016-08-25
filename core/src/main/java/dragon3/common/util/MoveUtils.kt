/*
 * 作成日: 2004/03/25
 */
package dragon3.common.util

import dragon3.common.Body
import dragon3.common.constant.BodyAttribute
import dragon3.common.constant.MoveType
import dragon3.common.constant.Page
import mine.paint.UnitMap

/**
 * @author k-saito
 */
object MoveUtils {

    const val WHITE = 0
    const val YELLOW = 1
    const val GREEN = 2
    const val AQUA = 3
    const val BLUE = 4
    const val BLACK = 5
    const val ICE = 6
    const val POISONP = 7
    const val OILP = 8
    const val FIREP = 9
    const val SKYP = 10
    const val S_BLUE = 15
    const val S_RED = 16
    const val C_BLUE = 17
    const val C_RED = 18
    const val C_BLUEC = 19
    const val C_REDC = 20
    const val CLOSE_BOX = 21
    const val OPEN_BOX = 22
    const val BROKEN_BOX = 23
    const val OPEN_MAGIC = 24
    const val CLOSE_MAGIC = 25

    const val T_SKY = 1
    const val T_LAND = 2
    const val T_SEA = 3
    const val T_POOL = 4
    const val T_ICE = 5

    /**
     * 必要歩数のリストを返す。
     *
     *

     * @param b
     * *
     * @return
     */
    fun getStepList(b: Body): IntArray {

        var moveType = b.base.moveType

        if (b.hasAttr(BodyAttribute.SORA))
            moveType = MoveType.FLY

        if (b.hasAttr(BodyAttribute.RIKU))
            moveType = MoveType.HEAVY

        if (b.hasAttr(BodyAttribute.FLY_ABLE))
            moveType = MoveType.FLY

        val stepList = moveType.steps.clone()

        if (b.hasAttr(BodyAttribute.LITE_WALK)) {
            stepList[WHITE] = 1
            stepList[YELLOW] = 1
        }
        if (b.hasAttr(BodyAttribute.SWIM_ABLE)) {
            stepList[AQUA] = 1
            stepList[BLUE] = 1
        }

        return stepList
    }


    /**
     * キャラの地形（陸、空、海、沼、氷）を返す。
     *
     *

     * @param map
     * *
     * @param b
     * *
     * @return
     */
    fun getTikei(map: UnitMap, b: Body): Int {
        if (b.hasAttr(BodyAttribute.SORA))
            return T_SKY
        if (!b.hasAttr(BodyAttribute.RIKU) && !b.hasAttr(BodyAttribute.SLEEP)) {
            if (b.base.moveType == MoveType.FLY)
                return T_SKY
            if (b.base.moveType == MoveType.HOVER)
                return T_SKY
        }
        when (map.getData(Page.P01, b.x, b.y)) {
            ICE -> return T_ICE
            AQUA, BLUE -> return T_SEA
            POISONP, OILP, FIREP -> return T_POOL
            else -> return T_LAND
        }
    }
}
