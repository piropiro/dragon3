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

    val WHITE = 0
    val YELLOW = 1
    val GREEN = 2
    val AQUA = 3
    val BLUE = 4
    val BLACK = 5
    val ICE = 6
    val POISONP = 7
    val OILP = 8
    val FIREP = 9
    val SKYP = 10
    val S_BLUE = 15
    val S_RED = 16
    val C_BLUE = 17
    val C_RED = 18
    val C_BLUEC = 19
    val C_REDC = 20
    val CLOSE_BOX = 21
    val OPEN_BOX = 22
    val BROKEN_BOX = 23
    val OPEN_MAGIC = 24
    val CLOSE_MAGIC = 25

    val T_SKY = 1
    val T_LAND = 2
    val T_SEA = 3
    val T_POOL = 4
    val T_ICE = 5

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
