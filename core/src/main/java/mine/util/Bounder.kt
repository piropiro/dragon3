package mine.util


class Bounder(y0: Double, g: Double, e: Double, max: Int) {

    private val y: DoubleArray

    init {

        y = DoubleArray(max)
        y[0] = y0

        calc(g, e)
    }

    fun calc(g: Double, e: Double) {

        var bound_time = -Math.sqrt(2 * y[0] / g)
        var bound_v = -g * bound_time

        var i = 1
        while (i < y.size) {
            val t = i - bound_time
            y[i] = bound_v * t - g * t * t / 2
            if (y[i] < 0) {
                y[i] = 0.0
                bound_time += 2 * bound_v / g
                bound_v *= e

                if (bound_v > 0.001) {
                    i--
                    i++
                    continue
                }
            }
            i++
        }
    }

    fun getY(s: Int): Int {
        if (s >= y.size) {
            return 0
        } else {
            return y[s].toInt()
        }
    }

}
