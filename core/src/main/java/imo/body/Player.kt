package imo.body


import imo.common.ImageList
import mine.paint.MineColor
import mine.paint.MineGraphics
import mine.paint.MineImage


class Player(private var oldx: Double, private var oldy: Double, private val screen: Body, imageList: ImageList) : Body(oldx, oldy, 32, 32, 0.0, 1.0) {

    var hp: Int = 0

    private var name: String = ""
    private var shotWait: Int = 0
    private var animeFlag: Boolean = false
    private var animeWait: Int = 0
    private var face: Int = 0
    private var faces: Int = 0
    private var status: Int = 0
    private var statusWait: Int = 0

    private val jikiImage: Array<Array<MineImage>>
    private val hpbarImage: MineImage

    init {
        this.jikiImage = imageList.jiki
        this.hpbarImage = imageList.hpbar
        hp = HP_MAX
        shotWait = 0
        animeFlag = true
        animeWait = 0
        face = 2
        faces = 2
        status = 0
        statusWait = 0
    }

    val isAlive: Boolean
        get() = hp > 0

    val isMax: Boolean
        get() = hp == HP_MAX

    fun getFace(): Int {
        return faces
    }

    fun move(u: Boolean, d: Boolean, l: Boolean, r: Boolean) {
        shotWait--
        if (0 >= statusWait--)
            status = S_MOVE
        anime()

        oldx = x
        oldy = y

        when (status) {
            S_MOVE, S_SHOT -> {
                v = V_WALK
                moveK(faces == 1, faces == 2, faces == 3, faces == 4)
                face = 0
                if (u)
                    face = F_UP
                if (d)
                    face = F_DOWN
                if (l)
                    face = F_LEFT
                if (r)
                    face = F_RIGHT
                if (face != 0)
                    faces = face
                v = V_DASH
                moveK(face == 1, face == 2, face == 3, face == 4)
                moveC(screen)
            }
            S_DAMAGE -> {
                v = V_DAMAGE
                super.move()
            }
        }
    }

    fun moveS() {
        x = oldx
        y = oldy
    }

    private fun anime() {
        if (0 >= animeWait--) {
            animeFlag = !animeFlag
            if (face != 0)
                animeWait = ANIME_WAIT_MAX
            if (face == 0)
                animeWait = ANIME_WAIT_MAX * 2
        }
    }

    fun shot(sword: Sword) {
        if (shotWait > 0)
            return
        if (status != S_MOVE)
            return
        sword.make(this, SWORD_WAIT_MAX)
        shotWait = SHOT_WAIT_MAX
        status = S_SHOT
        statusWait = SWORD_WAIT_MAX
    }

    fun damage(teki: Body, d: Int) {
        if (status == S_DAMAGE)
            return
        status = S_DAMAGE
        statusWait = DAMAGE_WAIT_MAX
        hp -= d
        if (hp < 0)
            hp = 0
        angle = Body.getAngle(teki, this)
    }

    fun display(g: MineGraphics) {
        var srcx = 0
        var srcy = 0
        when (faces) {
            F_UP -> srcx = 6
            F_DOWN -> srcx = 0
            F_LEFT -> srcx = 2
            F_RIGHT -> srcx = 4
        }
        if (animeFlag) {
            srcx += 1
        }

        when (status) {
            S_MOVE -> srcy += 0
            S_DAMAGE -> srcy += 1
            S_SHOT -> srcy += 2
        }
        g.drawImage(jikiImage[srcy][srcx], x.toInt(), y.toInt())
    }

    fun displayHP(g: MineGraphics) {
        g.drawImage(hpbarImage, 73, 18)

        g.setColor(255, 0, 255)
        g.fillRect(76, 21, hp * 96 / HP_MAX, 8)

        g.setColor(MineColor.BLACK)
        g.drawString(name, 72, 16)
    }

    companion object {

        const val HP_MAX = 10
        const val SHOT_WAIT_MAX = 15
        const val ANIME_WAIT_MAX = 7
        const val F_UP = 1
        const val F_DOWN = 2
        const val F_LEFT = 3
        const val F_RIGHT = 4
        const val SWORD_WAIT_MAX = 5
        const val DAMAGE_WAIT_MAX = 3
        const val S_MOVE = 0
        const val S_SHOT = 1
        const val S_DAMAGE = 2
        const val V_WALK = 1.5
        const val V_DASH = 3.0
        const val V_DAMAGE = 5.0
    }
}
