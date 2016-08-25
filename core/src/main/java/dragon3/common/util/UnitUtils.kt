package dragon3.common.util

import mine.paint.UnitMap
import mine.util.Point

class UnitUtils(private val map: UnitMap) {

    private var face: Int = 0 // used in moveS

    init {
        face = 0
    }

    /*** Shortest Root  */

    fun moveS(p: Int, x: Int, y: Int): Point? {

        val next = Point(x, y)
        var bd = map.getData(p, x, y)

        when (face) {
            F_UP -> bd = moves(p, x, y - 1, bd, next, F_UP)
            F_DOWN -> bd = moves(p, x, y + 1, bd, next, F_DOWN)
            F_LEFT -> bd = moves(p, x - 1, y, bd, next, F_LEFT)
            F_RIGHT -> bd = moves(p, x + 1, y, bd, next, F_RIGHT)
        }
        bd = moves(p, x, y - 1, bd, next, F_UP)
        bd = moves(p, x, y + 1, bd, next, F_DOWN)
        bd = moves(p, x - 1, y, bd, next, F_LEFT)
        bd = moves(p, x + 1, y, bd, next, F_RIGHT)
        if (next.x != x)
            return next
        if (next.y != y)
            return next
        return null
    }

    private fun moves(p: Int, x: Int, y: Int, bd: Int, s: Point, f: Int): Int {
        val bds = map.getData(p, x, y)
        if (bd > bds) {
            s.x = x
            s.y = y
            face = f
            return bds
        }
        return bd
    }

    companion object {
        private val F_UP = 0
        private val F_DOWN = 1
        private val F_LEFT = 2
        private val F_RIGHT = 3
    }
}
