package imo.paint

import java.awt.event.KeyEvent

import imo.ImoEventListener
import imo.ImoWorks
import imo.common.ImageList
import mine.paint.MineColor
import mine.paint.MineGraphics
import mine.paint.MineImage
import mine.thread.Engine

class EndImoEvent
(private val mw: ImoWorks, private val imageList: ImageList, n : Int) : ImoEventListener {

    private var count: Int = 0
    private val endImage: MineImage

    init {
        endImage = imageList.loadEndImage(n)
    }

    override fun paint(g: MineGraphics) {
        val size = Math.min(300, count * 6)

        g.drawImage(endImage, 0, 0, 300, size, 0, 0)

        if (count / 12 % 2 == 0) {
            g.setColor(MineColor.BLACK)
            g.drawString("press  C  to close", 20, 280)
        }
    }

    override fun keyReleased(character: Char, keyCode: Int) {
    }

    override fun keyPressed(character: Char, keyCode: Int) {
        when (keyCode) {
            KeyEvent.VK_ESCAPE -> mw.gameStart()
        }

        when (character) {
            'c' -> mw.gameExit()
        }
    }

    override fun run() {
        Engine.sleep(30)
        mw.repaint()
        count++
    }
}
