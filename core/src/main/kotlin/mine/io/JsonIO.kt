package mine.io

import net.arnx.jsonic.JSON

import org.apache.commons.io.IOUtils

import java.io.IOException


/**
 * BeansJsonファイルを読み書きするクラス。

 * @author k-saito
 * *
 * @version 1.0
 */
object JsonIO {

    /**
     * ファイルからオブジェクトを読み込む。

     * @param path - データファイルのパス
     * *
     * @return ファイルから読み込まれたオブジェクト
     * *
     * @throws RuntimeException データの読み込みに失敗した。
     */
    fun <T> read(path: String, clazz: Class<T>): T {
        FileIO.getInputStream(path).use { ins -> return JSON.decode(ins, clazz) }
    }

    /**
     * オブジェクトをファイルに書き出す。

     * @param path - データファイルのパス
     * *
     * @param obj - 書き出すオブジェクト
     * *
     * @throws RuntimeException データの書き込みに失敗した。
     */
    fun write(path: String, obj: Any) {
        try {
            FileIO.getOutputStream(path).use { os ->
                val json = JSON.encode(obj, true)

                IOUtils.write(json, os, "UTF-8")
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }
}
