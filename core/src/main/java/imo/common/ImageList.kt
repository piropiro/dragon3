package imo.common


import mine.paint.MineImage
import mine.paint.MineImageLoader

class ImageList(private val il: MineImageLoader) {

    val imo: Array<Array<MineImage>>
    val jiki: Array<Array<MineImage>>
    val sister: Array<Array<MineImage>>
    val sword: Array<Array<MineImage>>
    val turu: MineImage
    val hpbar: MineImage

    init {
        imo = il.loadTile(IMAGE_PATH + "imo.png", 32, 32)
        jiki = il.loadTile(IMAGE_PATH + "jiki.png", 32, 32)
        sister = il.loadTile(IMAGE_PATH + "sister.png", 32, 32)
        sword = il.loadTile(IMAGE_PATH + "sword.png", 32, 32)
        turu = il.load(IMAGE_PATH + "turu.png")
        hpbar = il.load(IMAGE_PATH + "hpbar.png")
    }

    fun loadEndImage(n: Int): MineImage {
        return il.load(IMAGE_PATH + "end" + n + ".png")
    }

    companion object {

        const val IMAGE_PATH = "imo/image/"
    }
}
