package imo.body

import imo.common.ImageList
import mine.paint.MineColor
import mine.paint.MineGraphics
import mine.paint.MineImage

class Imo(private var oldx: Double, private var oldy: Double, private val hpMax: Int, private val screen: Body,
          imageList: ImageList) : Body(oldx, oldy, 32, 32, 0.0, 0.5) {
    var hp: Int = 0

    private var name: String = ""

    private var shotWait: Int = 0
    private var turnWait: Int = 0
    private var animeWait: Int = 0
    private var animeFlag: Boolean = false
    private var face: Int = 0
    private var status: Int = 0
    private var statusWait: Int = 0

    private val imoImage: Array<Array<MineImage>>
    private val hpbarImage: MineImage

    init {
        this.imoImage = imageList.imo
        this.hpbarImage = imageList.hpbar
        hp = hpMax
        name = "Imo"
        shotWait = 300
        turnWait = 0
        animeWait = 0
        animeFlag = false
        face = 0
        status = 0
        statusWait = 0
    }

    fun shot(turu: Turu) {
        if (shotWait > 0)
            return
        if (status != S_MOVE)
            return
        turu.make(this, SWORD_WAIT_MAX)
        shotWait = SHOT_WAIT_MAX + rnd(100)
        status = S_SHOT
        statusWait = SWORD_WAIT_MAX
    }

    fun move(ore: Player) {
        shotWait--
        if (isAlive && 0 >= statusWait--)
            status = S_MOVE
        anime()
        if (status != S_SHOT)
            turn()

        oldx = x
        oldy = y

        when (status) {
            S_MOVE -> {
                v = V_WALK.toDouble()
                moveK(face == 1, face == 2, face == 3, face == 4)
                moveC(screen)
            }
            S_SHOT -> {
            }
            S_DAMAGE -> {
                when {
                    face == F_UP && ore.getFace() == F_DOWN -> {
                    }
                    face == F_DOWN && ore.getFace() == F_UP -> {
                    }
                    face == F_LEFT && ore.getFace() == F_RIGHT -> {
                    }
                    face == F_RIGHT && ore.getFace() == F_LEFT -> {
                    }
                    else -> {
                        v = V_DAMAGE.toDouble()
                        super.move()
                    }
                }
            }
        }
    }

    fun turn() {
        if (0 <= turnWait--)
            return
        when (rnd(10)) {
            0, 1, 2 -> face = 1
            3, 4 -> face = 2
            5, 6, 7 -> face = 3
            8, 9 -> face = 4
        }
        turnWait = 10 + rnd(10)
    }

    fun moveS() {
        x = oldx
        y = oldy
    }

    private fun anime() {
        if (0 >= animeWait--) {
            animeFlag = !animeFlag
            animeWait = ANIME_WAIT_MAX
        }
    }

    fun damage(teki: Body, d: Int) {
        if (status == S_DAMAGE)
            return
        if (status == S_SHOT)
            return
        status = S_DAMAGE
        statusWait = DAMAGE_WAIT_MAX
        hp -= d
        if (hp <= 0) {
            hp = 0
            status = S_DEAD
        }
        angle = Body.getAngle(teki, this)
    }

    val isAlive: Boolean
        get() = hp > 0

    fun display(g: MineGraphics) {

        var srcx = 0
        var srcy = 0

        when (face) {
            F_UP -> srcx = 6
            F_DOWN -> srcx = 0
            F_LEFT -> srcx = 2
            F_RIGHT -> srcx = 4
        }
        if (animeFlag) {
            srcx += 1
        }

        when (status) {
            S_MOVE -> srcy = 0
            S_DAMAGE -> srcy = 1
            S_SHOT -> srcy = 2
        }

        if (!isAlive) {
            srcx = 8
            srcy = 0
        }

        g.drawImage(imoImage[srcy][srcx], x.toInt(), y.toInt())
        g.drawImage(imoImage[1][srcx], 0, 0, 32, 32 * (hpMax - hp) / hpMax, x.toInt(), y.toInt())
    }

    fun displayHP(g: MineGraphics) {
        g.drawImage(hpbarImage, 191, 18)

        g.setColor(MineColor.LIME)
        g.fillRect(194, 21, hp * 96 / hpMax, 8)

        g.setColor(MineColor.BLACK)
        g.drawString(name, 190, 16)
    }

    companion object {

        const val SHOT_WAIT_MAX = 150
        const val ANIME_WAIT_MAX = 7

        const val F_UP = 1
        const val F_DOWN = 2
        const val F_LEFT = 3
        const val F_RIGHT = 4

        const val SWORD_WAIT_MAX = 60
        const val DAMAGE_WAIT_MAX = 5

        const val S_MOVE = 0
        const val S_SHOT = 1
        const val S_DAMAGE = 2
        const val S_DEAD = 3

        const val V_WALK = 1
        const val V_DAMAGE = 6
    }

}
