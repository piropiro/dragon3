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
import mine.event.MineCanvas
import mine.event.MouseAllListener
import mine.event.SleepManager

@Singleton
class DragonFrame
@Inject
constructor(@Named("mainC") mc: MineCanvas, sleepManager: SleepManager) : FrameWorks, ActionListener, KeyListener {

    private val mb: BMenuBar
    private val frame: JFrame
    private val mca: MineCanvasAWT
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

        this.mca = mc as MineCanvasAWT

        //mapPanel.setVisible(true);

        MineAwtUtils.setSize(mca, 640, 480)
        mca.background = Color(0, 0, 150)
        mca.addKeyListener(this)

        frame.contentPane.add(mca)
        frame.pack()
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        mca.addKeyListener(sleepManager as KeyListener)
        mca.addMouseListener(sleepManager as MouseListener)
    }

    fun launch() {
        frame.isVisible = true
        MineAwtUtils.setCenter(frame)

    }

    /*** MenuBar  */

    @Synchronized override fun setMenu(type: Int) {
        mb.reset(this)
        when (type) {
            FrameWorks.T_SCORE -> {
                mb.add("BACK", "back", KeyEvent.VK_B)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            FrameWorks.T_TITLE -> mb.add("NONE", "none", KeyEvent.VK_N)
            FrameWorks.T_CAMP -> {
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
            FrameWorks.T_STAGESELECT -> {
                mb.add("BACK", "camp", KeyEvent.VK_B)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            FrameWorks.T_SETMENS -> {
                mb.add("BACK", "stage", KeyEvent.VK_B)
                mb.add("START", "start", KeyEvent.VK_S)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            FrameWorks.T_COLLECT -> {
                mb.add("BACK", "back", KeyEvent.VK_B)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            FrameWorks.T_PLAYER -> {
                mb.add("TURN END", "turnend", KeyEvent.VK_T)
                mb.add("ESCAPE", "escape", KeyEvent.VK_E)
                mb.add("LOAD", "mapload", KeyEvent.VK_Q)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }

            FrameWorks.T_ENEMY -> mb.add("NONE", "none", KeyEvent.VK_N)
            FrameWorks.T_CLEAR -> {
                mb.add("CAMP", "camp", KeyEvent.VK_A)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
            FrameWorks.T_GAMEOVER -> {
                mb.add("LOAD", "mapload", KeyEvent.VK_Q)
                mb.add("HELP", "help", KeyEvent.VK_H)
            }
        }
        frame.jMenuBar = mb
        mb.repaint()
        mca.requestFocus()
    }


    override fun actionPerformed(e: ActionEvent) {
        frame.requestFocus()
        if (mca.isRunning)
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
        this.mal = mal
        mca.setMouseAllListener(mal)
    }

    fun repaint() {
        mca.repaint()
    }

    fun setCommandListener(commandListener: CommandListener) {
        this.commandListener = commandListener
    }
}
