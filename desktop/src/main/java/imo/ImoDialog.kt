package imo

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

import javax.inject.Inject
import javax.swing.JDialog

import mine.awt.MineCanvasAWT
import mine.awt.MineAwtUtils
import mine.event.MineCanvas
import mine.event.SleepManager
import mine.paint.MineImageLoader
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class ImoDialog
@Inject
constructor() : JDialog(), KeyListener, ImoListener {

    @field: Inject
    lateinit internal var mc: MineCanvas

    @field: Inject
    lateinit internal var sleepManager: SleepManager

    @field: Inject
    lateinit internal var imageLoader: MineImageLoader

    @field: Inject
    lateinit internal var ic: ImoCanvas

    var life: Boolean = false
    var exp: Int = 0
    var level = 1

    fun launch() {

        val c = MineCanvasAWT(mc)
        MineAwtUtils.setSize(c, 300, 300)

        ic.setup("僕", 1)

        ic.setVisible(true)
        title = "ImoBattle"

        c.addKeyListener(this)

        ic.setImoListener(this)

        contentPane.add(c)
        pack()
        MineAwtUtils.setCenter(this)
        isVisible = true
        c.requestFocus()

        thread {
            while (true) {
                if (mc.isUpdated) {
                    c.repaint()
                }
                Thread.sleep(10)
            }
        }
    }


    override fun gameExit(exp_: Int) {
        this.life = true
        this.exp = exp_
        if (exp > 0) level++
        ic.gameReset("俺", level)
    }

    override fun keyReleased(e: KeyEvent) {
        ic.keyReleased(e.keyChar, e.keyCode)
    }

    override fun keyPressed(e: KeyEvent) {
        ic.keyPressed(e.keyChar, e.keyCode)
    }

    override fun keyTyped(e: KeyEvent) {
    }

}
