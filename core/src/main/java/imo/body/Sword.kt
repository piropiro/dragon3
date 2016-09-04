package imo.body

import imo.common.ImageList
import mine.paint.MineGraphics
import mine.paint.MineImage


class Sword(imageList: ImageList) : Body(0.0, 0.0, 32, 32, 0.0, 0.0) {

    var isAlive: Boolean = false
        private set
    private var animeWait: Int = 0
    private var face: Int = 0

    private val swordImage: Array<Array<MineImage>>

    init {
        this.swordImage = imageList.sword
        isAlive = false
        animeWait = 0
        face = 2
    }

    fun make(ore: Player, animeWait_: Int) {
        this.face = ore.getFace()
        this.animeWait = animeWait_
        isAlive = true
        move(ore)
    }

    fun move(ore: Player) {
        this.face = ore.getFace()
        when (face) {
            F_UP -> {
                x = ore.x
                y = ore.y - 32
            }
            F_DOWN -> {
                x = ore.x
                y = ore.y + 32
            }
            F_LEFT -> {
                x = ore.x - 32
                y = ore.y
            }
            F_RIGHT -> {
                x = ore.x + 32
                y = ore.y
            }
        }
    }

    fun display(g: MineGraphics) {
        if (0 >= animeWait--)
            isAlive = false
        if (!isAlive)
            return

        var srcx = 0
        when (face) {
            F_UP -> srcx = 3
            F_DOWN -> srcx = 0
            F_LEFT -> srcx = 1
            F_RIGHT -> srcx = 2
        }

        g.drawImage(swordImage[0][srcx], x.toInt(), y.toInt())
    }

    companion object {

        internal val F_UP = 1
        internal val F_DOWN = 2
        internal val F_LEFT = 3
        internal val F_RIGHT = 4
    }
}
