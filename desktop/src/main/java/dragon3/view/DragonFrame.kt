package dragon3.view

import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseListener

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.swing.AbstractButton
import javax.swing.JFrame

import dragon3.controller.CommandListener
import mine.awt.BMenuBar
import mine.awt.MineAwtUtils
import mine.awt.MineCanvasAWT
import mine.awt.MouseManagerAWT
import mine.event.MineCanvas
import mine.event.MouseAllListener
import mine.event.MouseManager
import mine.event.SleepManager
import kotlin.concurrent.thread

@Singleton
class DragonFrame
@Inject
constructor(@Named("mainC") mc: MineCanvas, sleepManager: SleepManager) : FrameWorks, ActionListener, KeyListener {


    private val mm: MouseManager

    private val mb: BMenuBar
    private val frame: JFrame
    private val c: MineCanvasAWT
    private val mca: MineCanvas
    private var mal: MouseAllListener? = null

    private var commandListener: CommandListener? = null

    init {
        this.frame = JFrame("RyuTaiji 3")
        frame.isResizable = false
        frame.background = Color(0, 0, 150)

        // Menu
        mb = BMenuBar()
        mb.add("NONE", "none", KeyEvent.VK_N)
        frame.jMenuBar = mb

        c = MineCanvasAWT(mc)
        mm = MouseManagerAWT(c)

        this.mca = mc

        //mapPanel.setVisible(true);

        MineAwtUtils.setSize(c, 640, 480)
        c.background = Color(0, 0, 150)
        c.addKeyListener(this)

        frame.contentPane.add(c)
        frame.pack()
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        c.addKeyListener(sleepManager as KeyListener)
        c.addMouseListener(sleepManager as MouseListener)
    }

    fun launch() {
        frame.isVisible = true
        MineAwtUtils.setCenter(frame)

        thread {
            while (true) {
                Thread.sleep(10)
                c.repaint()
            }
        }
    }

    /*** MenuBar  */

    @Synchronized override fun setMenu(type: MenuSet) {
        mb.reset(this)
        when (type) {
            MenuSet.T_SCORE -> {
                mb.add("BACK", "back", KeyEvent.VK_B)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            MenuSet.T_TITLE -> mb.add("NONE", "none", KeyEvent.VK_N)
            MenuSet.T_CAMP -> {
                mb.add("STAGE", "stage", KeyEvent.VK_A)
                mb.add("SAVE", "save", KeyEvent.VK_S)
                mb.add("LOAD", "campload", KeyEvent.VK_Q)
                mb.addMenu("OPTION    x", 'x')
                mb.addItem("SORT", "sort", KeyEvent.VK_T)
                mb.addItem("WAZA", "waza", KeyEvent.VK_W)
                mb.addItem("WAZA_LIST", "wazalist", KeyEvent.VK_E)
                mb.addItem("REMOVE", "remove", KeyEvent.VK_R)
                mb.addItem("COLLECTION", "collect", KeyEvent.VK_F)
                mb.addItem("IMO_GARI", "imogari", KeyEvent.VK_V)
                mb.addItem("HELP", "help", KeyEvent.VK_H)
                mb.addItem("SCORE", "score", KeyEvent.VK_G)
            }
            MenuSet.T_STAGESELECT -> {
                mb.add("BACK", "camp", KeyEvent.VK_B)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            MenuSet.T_SETMENS -> {
                mb.add("BACK", "stage", KeyEvent.VK_B)
                mb.add("START", "start", KeyEvent.VK_S)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            MenuSet.T_COLLECT -> {
                mb.add("BACK", "back", KeyEvent.VK_B)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            MenuSet.T_PLAYER -> {
                mb.add("TURN END", "turnend", KeyEvent.VK_T)
                mb.add("ESCAPE", "escape", KeyEvent.VK_E)
                mb.add("LOAD", "mapload", KeyEvent.VK_Q)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }

            MenuSet.T_ENEMY -> mb.add("NONE", "none", KeyEvent.VK_N)
            MenuSet.T_CLEAR -> {
                mb.add("CAMP", "camp", KeyEvent.VK_A)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            MenuSet.T_GAMEOVER -> {
                mb.add("LOAD", "mapload", KeyEvent.VK_Q)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
        }
        frame.jMenuBar = mb
        mb.repaint()
        c.requestFocus()
    }


    override fun actionPerformed(e: ActionEvent) {
        frame.requestFocus()
        if (mm.isAlive)
            return
        val b = e.source as AbstractButton
        val command = b.actionCommand

        Thread { commandListener!!.executeMenuCommand(command) }.start()
    }

    override fun keyTyped(e: KeyEvent) {
    }

    override fun keyReleased(e: KeyEvent) {
    }

    override fun keyPressed(e: KeyEvent) {
        println(e.keyChar)
        var n = 0
        when (e.keyCode) {
            KeyEvent.VK_F1 -> n = 1
            KeyEvent.VK_F2 -> n = 2
            KeyEvent.VK_F3 -> n = 3
            KeyEvent.VK_F4 -> n = 4
            KeyEvent.VK_F5 -> n = 5
            KeyEvent.VK_F6 -> n = 6
            KeyEvent.VK_F7 -> n = 7
            KeyEvent.VK_F8 -> n = 8
            KeyEvent.VK_X -> {
                Thread { mal!!.accept() }.start()
                return
            }
            KeyEvent.VK_C -> {
                Thread { mal!!.cancel() }.start()
                return
            }
            else -> return
        }
        commandListener?.executeFKeyCommand(n, e.isShiftDown)
    }

    override fun setMouseListener(mal: MouseAllListener) {
        mm.setMouseAllListener(mal)
        this.mal = mal
    }

    override fun repaint() {
//        c.repaint()
    }

    override fun repaint(x: Int, y: Int, w: Int, h: Int) {
//        c.repaint(x, y, w, h)
//        c.repaint()
    }

    fun setCommandListener(commandListener: CommandListener) {
        this.commandListener = commandListener
    }
}
