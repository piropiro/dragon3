/*
 * Created on 2004/10/10
 */
package mine.paint


/**
 * @author saito
 */
interface MineGraphics {

    fun setColor(color: MineColor)

    fun setColor(r: Int, g: Int, b: Int)

    fun setColor(r: Int, g: Int, b: Int, a: Int)

    fun setColor(rgba: IntArray)

    fun fillRect(x: Int, y: Int, w: Int, h: Int)

    fun drawRect(x: Int, y: Int, w: Int, h: Int)

    fun drawLine(sx: Int, sy: Int, dx: Int, dy: Int)

    fun drawString(s: String, x: Int, y: Int)

    fun drawImage(image: MineImage, x: Int, y: Int)

    fun drawImage(image: MineImage, sx: Int, sy: Int, w: Int, h: Int, dx: Int, dy: Int)

    fun drawRotateImage(image: MineImage, dx: Int, dy: Int, angle: Double)

    fun drawFitImage(image: MineImage, dw: Int, dh: Int)

    fun setAlpha(alpha: Double)

    fun setAntialias(flag: Boolean)

    fun setFont(font: String, size: Int)

    /**
     * 文字列を中央に描画する。
     *
     *

     * @param s
     * *
     * @param x
     * *
     * @param y
     * *
     * @param xs
     * *
     * @param g
     */
    fun drawString(s: String, x: Int, y: Int, xs: Int)
}
