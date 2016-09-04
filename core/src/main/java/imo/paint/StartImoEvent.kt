package imo.paint


import imo.ImoEventListener
import imo.ImoWorks
import mine.paint.MineColor
import mine.paint.MineGraphics
import mine.thread.Engine


class StartImoEvent(private val mw: ImoWorks) : ImoEventListener {
    private var count: Int = 0

    init {
        count = 0
    }

    override fun paint(g: MineGraphics) {
        g.setColor(MineColor.BLACK)
        g.drawString("操作方法", 200, 240)
        g.drawString("攻撃 : Z", 200, 260)
        g.drawString("移動 : ↑←↓→", 200, 280)


        if (count / 12 % 2 == 0) {
            g.setColor(MineColor.BLACK)
        } else {
            g.setColor(180, 240, 180)
        }

        g.drawString("press  Z  to start", 100, 140)
    }

    override fun run() {
        Engine.sleep(30)
        mw.repaint()
        count++
    }

    override fun keyReleased(character: Char, keyCode: Int) {
    }

    override fun keyPressed(character: Char, keyCode: Int) {
        when (character) {
            'z' -> mw.gameStart()
        }
    }

}
