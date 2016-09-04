package imo.paint

import imo.ImoEventListener
import imo.ImoWorks
import imo.body.Body
import imo.body.Imo
import imo.body.Player
import imo.body.Sister
import imo.body.Sword
import imo.body.Turu
import imo.common.ImageList
import mine.event.KeyManager
import mine.paint.MineGraphics
import mine.thread.Engine

class GameImoEvent(private val mw: ImoWorks, private val km: KeyManager, private val imageList: ImageList) : ImoEventListener {

    private val player: Player
    private val sister: Sister
    private val sword: Sword
    private val imo: Imo
    private val turu: Turu
    private val screen: Body

    private var love: Int = 0
    private var clearFlag: Boolean = false

    init {
        screen = Body(0.0, 0.0, 300, 300, 0.0, 0.0)
        resetData("", 1)
        player = Player(120.0, 120.0, screen, imageList)
        sister = Sister(0.0, 0.0, imageList)

        imo = Imo(280.0, 280.0, 2, screen, imageList)
        turu = Turu(imageList)

        sword = Sword(imageList)
    }

    fun resetData(name: String, level: Int) {
        km.reset()
    }

    override fun paint(g: MineGraphics) {
        sword.display(g)
        player.display(g)
        sister.display(g)
        imo.display(g)
        turu.display(g)
        sister.displayMessage(g, love, player, imo)
        player.displayHP(g)
        imo.displayHP(g)
    }

    private fun hit() {
        if (Body.hit(player, sister)) {
            player.moveS()
            love++
        }
        if (Body.hit(player, imo)) {
            if (imo.isAlive) {
                player.damage(imo, 1)
            } else {
                player.moveS()
            }
            if (turu.isAlive && Body.hit(player, turu)) {
                player.damage(imo, 3)
            }
            if (sword.isAlive && Body.hit(sword, imo)) {
                imo.damage(player, 1)
            }
        }
        if (sword.isAlive && Body.hit(sword, sister)) {
            sister.damage()
        }
        sword.move(player)
    }

    private fun endjudge() {
        if (!player.isAlive) {
            mw.gameEnd(1)
            return
        }
        if (!sister.isAlive) {
            mw.gameEnd(4)
            return
        }
        if (Body.hit(imo, sister)) {
            mw.gameEnd(3)
            return
        }
        if (turu.isAlive && Body.hit(turu, sister)) {
            mw.gameEnd(3)
            return
        }

        if (love >= 200) {
            mw.gameEnd(2)
            return
        }
        var imoAlive = false
        if (imo.isAlive) {
            imoAlive = true
        }

        if (!imoAlive) {
            clearFlag = true
            if (love >= 10) {
                if (player.isMax) {
                    mw.gameEnd(5)
                    return
                } else {
                    mw.gameEnd(0)
                    return
                }
            }
        }
    }

    val exp: Int
        get() {
            if (clearFlag)
                return player.hp
            return 0
        }

    override fun keyReleased(character: Char, keyCode: Int) {
        km.keyReleased(character, keyCode)
    }

    override fun keyPressed(character: Char, keyCode: Int) {
        km.keyPressed(character, keyCode)
    }

    override fun run() {
        Engine.sleep(16)
        mw.repaint()
        if (km.isEscape || km.isKey('x'))
            mw.gameStart()
        endjudge()
        player.move(km.isUp, km.isDown, km.isLeft, km.isRight)
        imo.move(player)
        sister.move()
        if (km.isEnter || km.isKey('z')) {
            player.shot(sword)
            km.setKey('z', false)
        }
        imo.shot(turu)
        turu.move(imo)
        sword.move(player)
        hit()
    }
}
