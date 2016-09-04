package imo.body

import imo.common.ImageList
import mine.paint.MineColor
import mine.paint.MineGraphics
import mine.paint.MineImage


class Sister(x: Double, y: Double, imageList: ImageList) : Body(x, y, 32, 32, 0.0, 0.0) {

    private var hp: Int = 0
    private var animeFlag: Boolean = false
    private var animeWait: Int = 0
    private var status: Int = 0
    private var statusWait: Int = 0

    private val sisterImage: Array<Array<MineImage>>

    init {
        this.sisterImage = imageList.sister
        hp = HP_MAX
        animeFlag = true
        animeWait = 0
        status = 0
        statusWait = 0
    }

    val isAlive: Boolean
        get() = hp > 0

    private fun anime() {
        if (0 >= statusWait--)
            status = S_NONE
        if (0 >= animeWait--) {
            animeFlag = !animeFlag
            animeWait = ANIME_WAIT_MAX
        }
    }

    fun damage() {
        if (status == S_DAMAGE)
            return
        status = S_DAMAGE
        statusWait = DAMAGE_WAIT_MAX
        hp--
    }

    override fun move() {
        anime()
    }

    fun display(g: MineGraphics) {
        var srcx = 0
        if (animeFlag)
            srcx += 1

        if (status == S_DAMAGE)
            srcx += 2

        g.drawImage(sisterImage[0][srcx], x.toInt(), y.toInt())
    }

    fun displayMessage(g: MineGraphics, love: Int, player: Player, imo: Imo) {
        var message = ""
        if (imo.isAlive) {
            if (hp < HP_MAX) {
                message = "うわーん"
            } else if (love > 30) {
                message = "お兄ちゃん、だめ…"
            } else if (imo.hp == 1) {
                if (player.hp == Player.HP_MAX) {
                    message = "やっちゃえ！"
                } else {
                    message = "お兄ちゃん、とどめよ！"
                }
            } else if (player.hp < 5) {
                message = "お兄ちゃん負けないで！"
            } else if (imo.hp < 5) {
                message = "がんばれー"
            } else if (imo.hp < 10) {
                message = "いけー"
            }
        } else {
            if (player.hp == Player.HP_MAX) {
                message = "パーフェクト！"
            } else if (player.hp == 1) {
                message = "ヤレヤレだぜ…"
            } else {
                message = "おかえりー"
            }
        }

        g.setColor(MineColor.BLACK)
        g.drawString(message, 5, 49)
    }

    companion object {

        internal val HP_MAX = 3
        internal val ANIME_WAIT_MAX = 20
        internal val DAMAGE_WAIT_MAX = 10

        internal val S_NONE = 0
        internal val S_DAMAGE = 1
    }
}
