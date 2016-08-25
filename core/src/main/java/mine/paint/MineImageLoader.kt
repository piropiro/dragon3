/*
 * Created on 2004/10/10
 */
package mine.paint


/**
 * @author saito
 */
interface MineImageLoader {
    fun load(path: String): MineImage

    fun loadTile(path: String, width: Int, height: Int): Array<Array<MineImage>>

    fun getBuffer(width: Int, height: Int): MineImage

    /**
     * イメージを指定されたサイズに拡大縮小します。
     * @param img - ソースイメージ
     * *
     * @param width - 指定された幅
     * *
     * @param height - 指定された高さ
     * *
     * @return 指定されたサイズに拡大縮小されたイメージ
     */
    fun resize(img: MineImage, width: Int, height: Int): MineImage

    /**
     * イメージを指定された比率で拡大縮小します。
     * @param img - ソースイメージ
     * *
     * @param rate - サイズ比率
     * *
     * @return 指定された比率で拡大縮小されたイメージ
     */
    fun resize(img: MineImage, rate: Double): MineImage
}
