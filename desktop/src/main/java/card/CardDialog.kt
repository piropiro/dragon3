package card

import mine.awt.MineAwtUtils
import mine.awt.MineCanvasAWT
import mine.awt.MouseManagerAWT
import mine.event.MineCanvas
import mine.event.MouseManager
import mine.event.SleepManager
import mine.io.JsonManager
import mine.paint.MineImage
import mine.paint.MineImageLoader

import javax.inject.Inject
import javax.inject.Singleton
import javax.swing.*
import java.awt.event.KeyListener
import java.awt.event.MouseListener
import java.util.ArrayList
import kotlin.concurrent.thread

@Singleton
class CardDialog
@Inject
constructor() : JDialog(), CardListener {


    @field: Inject
    lateinit internal var mc: MineCanvas

    @field: Inject
    lateinit internal var sleepManager: SleepManager

    @field: Inject
    lateinit internal var imageLoader: MineImageLoader

    @field: Inject
    lateinit internal var jsonManager: JsonManager

    @field: Inject
    lateinit internal var cc: CardCanvas


    private lateinit var chara: Array<MineImage>
    private var level = 1

    private lateinit var status: Array<IntArray>


    fun launch() {
        val c = MineCanvasAWT(cc)
        val mm = MouseManagerAWT(c)

        MineAwtUtils.setSize(c, 640, 480)

        cc.setVisible(true)
        title = "CardBattle"

        //SleepManager sleepManager = new SleepManagerAWT();
        c.addKeyListener(sleepManager as KeyListener)
        c.addMouseListener(sleepManager as MouseListener)

        //cc = new CardCanvas(cardPanel, mil, sleepManager);		
        cc.setCardListener(this)
        mm.setMouseAllListener(CardEventListener(cc))

        //chara = (MineImage[])MineUtils.linerize(
        //	imageLoader.loadTile("card/image/chara.png", 32, 32), new MineImage[0]);

        val charaArray = imageLoader.loadTile("card/image/chara.png", 32, 32)
        val charaList = ArrayList<MineImage>()
        for (array in charaArray) {
            for (image in array) {
                charaList.add(image)
            }
        }
        chara = charaList.toTypedArray()

        status = jsonManager.read("card/data/status.json", Array<IntArray>::class.java)


        contentPane.add(c)
        pack()
        MineAwtUtils.setCenter(this)
        isVisible = true
        cc.setBlueChara(chara[status[0][0]], status[0].toList().subList(1, 7))
        setRedChara()

        thread {
            cc.start()
        }

        thread {
            while (true) {
                if (mc.isUpdated) {
                    c.repaint()
                }
                Thread.sleep(10)
            }
        }
    }


    override fun gameExit(redWin: Int, blueWin: Int) {
        cc.dispose()
        level = Math.max(1, Math.min(level - redWin + blueWin, status.size - 1))
        setRedChara()
        cc.start()
    }

    private fun setRedChara() {
        cc.setRedChara(chara[status[level][0]], status[level].toList().subList(1, 7))
    }
}
