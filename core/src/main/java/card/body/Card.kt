package card.body

import card.common.ImageList
import mine.paint.MineGraphics
import mine.paint.MineImage
import mine.util.Bounder

class Card(private var n: Int, var x: Int, var y: Int, private val color: Int, il: ImageList) {

    private var count: Int = 0
    var status = NONE

    private var open_img = openImage
    private val close_img = closeImage

    private val blueImage = il.blue
    private val redImage = il.red
    private val numImage = il.number
    private val winImage = il.win

    /*** Status  */

    fun close() {
        status = CLOSE
    }

    fun opening(count: Int) {
        this.count = count
        status = OPENING
    }

    fun open() {
        status = OPEN
    }

    fun dispose() {
        status = NONE
    }

    fun slashing(count: Int) {
        this.count = count
        status = SLASHING
    }

    fun winning(count: Int) {
        this.count = count
        status = WINNING
    }

    fun win() {
        status = WIN
    }

    var number: Int
        get() = n
        set(n) {
            this.n = n
            open_img = openImage
        }

    fun setLocation(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    private // do nothing
    val openImage: MineImage
        get() {
            val img: MineImage = when (color) {
                BLUE -> blueImage[ImageList.OPEN].copy
                RED -> redImage[ImageList.OPEN].copy
                else -> throw IllegalArgumentException("Invalid color:" + color)
            }
            val g = img.graphics

            when {
                (n < 0) -> {
                }
                (n < 10) -> {
                    g.drawImage(numImage[n], 11, 10)
                }
                (n < 100) -> {
                    g.drawImage(numImage[n / 10], 6, 10)
                    g.drawImage(numImage[n % 10], 15, 10)
                }
                else -> {
                    g.drawImage(numImage[0], 6, 10)
                    g.drawImage(numImage[0], 15, 10)
                }
            }

            return img
        }

    private val closeImage: MineImage
        get() {
            return if (color == BLUE) blueImage[ImageList.CLOSE] else redImage[ImageList.CLOSE]
        }


    fun paint(g: MineGraphics) {
        when (status) {
            NONE -> {
            }
            CLOSE, WIN -> g.drawImage(close_img, x, y)
            OPENING -> openingPaint(g)
            OPEN -> g.drawImage(open_img, x, y)
            SLASHING -> slashingPaint(g)
            WINNING -> winningPaint(g)
        }
    }

    private fun openingPaint(g: MineGraphics) {
        count = Math.min(count, OPENING_MAX)
        if (color == BLUE) {
            g.drawImage(blueImage[count], x, y)
        } else {
            g.drawImage(redImage[count], x, y)
        }
    }

    private fun slashingPaint(g: MineGraphics) {
        count = Math.min(count, SLASHING_MAX)
        g.setAlpha(1.0 * (SLASHING_MAX - count) / SLASHING_MAX)
        if (color == BLUE) {
            halfPaint(g, x + count * 3, y, true)
            halfPaint(g, x + count * 1, y, false)
        } else {
            halfPaint(g, x - count * 3, y, true)
            halfPaint(g, x - count * 1, y, false)
        }
        g.setAlpha(1.0)
    }

    private fun winningPaint(g: MineGraphics) {
        count = Math.min(count, WINNING_MAX)
        g.drawImage(open_img, x, y)
        g.drawImage(winImage[0], x + 2, y + 20 - BOUNDER.getY(count))
        g.drawImage(winImage[1], x + 15, y + 20 - BOUNDER.getY(count + 1))
        g.drawImage(winImage[2], x + 20, y + 20 - BOUNDER.getY(count + 2))
    }

    private fun halfPaint(g: MineGraphics, x_: Int, y_: Int, flag: Boolean) {
        val offset = if (flag) 0 else 16
        g.drawImage(open_img.getSubimage(0, offset, 32, 16), x_, y_ + offset)
    }

    override fun toString(): String {
        return when (color) {
            BLUE -> "B" + n
            else -> "R" + n
        }
    }

    companion object {

        private val BOUNDER = Bounder(20.0, 3.0, 0.7, 20)

        const val NONE = 0
        const val CLOSE = 1
        const val OPENING = 2
        const val OPEN = 3
        const val SLASHING = 4
        const val WINNING = 5
        const val WIN = 6

        const val BLUE = 0
        const val RED = 1

        const val OPENING_MAX = 6
        const val SLASHING_MAX = 20
        const val WINNING_MAX = 20
    }
}
