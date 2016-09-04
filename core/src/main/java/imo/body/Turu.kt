package imo.body

import imo.common.ImageList
import mine.paint.MineGraphics
import mine.paint.MineImage

class Turu(imageList: ImageList) : Body(0.0, 0.0, 32, 32, 0.0, 0.0) {

    var isAlive: Boolean = false
        private set
    private var animeWait: Int = 0
    private var time2: Double = 0.toDouble()
    private var time3: Double = 0.toDouble()
    private var time4: Double = 0.toDouble()

    private val turuImage: MineImage

    init {
        this.turuImage = imageList.turu
        isAlive = false
        animeWait = 0
    }

    fun make(imo: Imo, animeWait_: Int) {
        this.animeWait = animeWait_
        isAlive = true

        time2 = (animeWait * 20 / 200).toDouble()
        time3 = (animeWait * 10 / 200).toDouble()
        time4 = (animeWait * 30 / 200).toDouble()

        move(imo)
    }

    fun move(imo: Imo) {
        if (0 >= animeWait--)
            isAlive = false
        var t = animeWait.toDouble()

        if (t < time4) {
            val ts = (t * 32 / time4).toInt()
            x = imo.x - ts
            y = imo.y - ts
            xs = 32 + ts * 2
            ys = 32 + ts * 2
            return
        }
        t -= time4

        if (t < time3) {
            x = imo.x - 32
            y = imo.y - 32
            xs = 96
            ys = 96
            return
        }
        t -= time3

        if (t < time2) {
            val ts = ((time2 - t) * 32 / time2).toInt()
            x = imo.x - ts
            y = imo.y - ts
            xs = 32 + ts * 2
            ys = 32 + ts * 2
            return
        }

        x = imo.x
        y = imo.y
        xs = 32
        ys = 32
    }

    fun display(g: MineGraphics) {
        if (!isAlive)
            return

        g.drawImage(turuImage, 48 - xs / 2, 48 - ys / 2, xs, ys, x.toInt(), y.toInt())
    }
}
