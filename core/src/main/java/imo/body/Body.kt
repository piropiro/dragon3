package imo.body

open class Body(var x: Double, var y: Double, protected var xs: Int, protected var ys: Int, protected var angle: Double, protected var v: Double) {

    open fun move() {
        x += v * Math.cos(angle)
        y += v * Math.sin(angle)
    }

    fun moveK(u: Boolean, d: Boolean, l: Boolean, r: Boolean) {
        if (u)
            y -= v
        if (d)
            y += v
        if (l)
            x -= v
        if (r)
            x += v
    }

    fun moveM(mx: Int, my: Int) {
        if ((x - mx) * (x - mx) + (y - my) * (y - my) < v * v) {
            x = mx.toDouble()
            y = my.toDouble()
        } else {
            val anga = getAngle(this, mx.toDouble(), my.toDouble())
            x += v * Math.cos(anga)
            y += v * Math.sin(anga)
        }
    }

    fun moveC(a: Body) {
        if (x + xs >= a.x + a.xs)
            x = a.x + a.xs - xs.toDouble() - 1.0
        if (y + ys >= a.y + a.ys)
            y = a.y + a.ys - ys.toDouble() - 1.0
        if (x < a.x)
            x = a.x
        if (y < a.y)
            y = a.y
    }

    fun rnd(max: Int): Int {
        return (Math.random() * max).toInt()
    }

    companion object {

        fun hit(a: Body, b: Body): Boolean {
            if (a.xs <= 0)
                return false
            if (a.ys <= 0)
                return false
            if (b.xs <= 0)
                return false
            if (b.ys <= 0)
                return false
            if (a.x + a.xs < b.x)
                return false
            if (a.x >= b.x + b.xs)
                return false
            if (a.y + a.ys < b.y)
                return false
            if (a.y >= b.y + b.ys)
                return false
            return true
        }

        fun hit(a: Body, x: Double, y: Double): Boolean {
            if (x < a.x)
                return false
            if (x >= a.x + a.xs)
                return false
            if (y < a.y)
                return false
            if (y >= a.y + a.ys)
                return false
            return true
        }

        internal fun getAngle(a: Body, b: Body): Double {
            return Math.atan2(
                    b.y + b.ys / 2 - (a.y + a.ys / 2),
                    b.x + b.xs / 2 - (a.x + a.xs / 2))
        }

        internal fun getAngle(a: Body, x: Double, y: Double): Double {
            return Math.atan2(y - (a.y + a.ys / 2), x - (a.x + a.xs / 2))
        }
    }
}
