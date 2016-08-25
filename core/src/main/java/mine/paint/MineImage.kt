/*
 * Created on 2004/10/10
 */
package mine.paint

/**
 * @author saito
 */
interface MineImage {
    val image: Any

    val width: Int

    val height: Int

    val graphics: MineGraphics

    fun getSubimage(x: Int, y: Int, w: Int, h: Int): MineImage

    val copy: MineImage
}
