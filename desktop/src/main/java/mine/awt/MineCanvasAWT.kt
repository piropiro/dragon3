package mine.awt

import java.awt.Graphics
import java.awt.Paint

import javax.swing.JComponent

import mine.awt.GraphicsAWT
import mine.event.PaintListener

/**
 * Created by rara on 2016/08/30.
 */
class MineCanvasAWT(private val pl: PaintListener) : JComponent() {

    override fun paint(g: Graphics) {
        pl.paint(GraphicsAWT(g))
    }
}
