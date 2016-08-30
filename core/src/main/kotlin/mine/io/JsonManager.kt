package mine.io

import net.arnx.jsonic.JSON
import javax.inject.Inject

/**
 * Created by rara on 2016/08/29.
 */
class JsonManager @Inject constructor() {

    @field: Inject
    lateinit var fileManager :FileManager

    /**
     * ファイルからオブジェクトを読み込む。
     *
     * @param path - データファイルのパス
     * *
     * @return ファイルから読み込まれたオブジェクト
     * *
     * @throws RuntimeException データの読み込みに失敗した。
     */
    fun <T> read(path: String, clazz: Class<T>): T {
        fileManager.getInputStream(path).use { ins -> return JSON.decode(ins, clazz) }
    }

    /**
     * オブジェクトをファイルに書き出す。
     *
     * @param path - データファイルのパス
     *
     * @param obj - 書き出すオブジェクト
     *
     * @throws RuntimeException データの書き込みに失敗した。
     */
    fun write(path: String, obj: Any) {
        fileManager.getOutputStream(path).use { os -> JSON.encode(obj, os, true) }
    }
}