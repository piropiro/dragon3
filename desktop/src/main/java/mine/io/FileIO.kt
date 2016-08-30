/*
 * 作成日: 2003/09/28
 */
package mine.io

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


/**
 * @author k-saito
 */
object FileIO {

    /**
     * 指定されたファイルの入力ストリームを取得する。
     *
     *

     * @param path ファイルパス
     * *
     * @return 指定されたファイルの入力ストリーム
     * *
     * @throws RuntimeException ファイルが見つからない。
     */
    fun getInputStream(path: String): BufferedInputStream {

        try {
            var ins: InputStream? = FileIO::class.java.getResourceAsStream("/" + path)
            if (ins == null) {
                ins = FileInputStream(path)
            }
            return BufferedInputStream(ins)
        } catch (e: FileNotFoundException) {
            println("File is not Found. [" + File(path).absolutePath + "]")
            throw RuntimeException(e)
        }

    }

    /**
     * 指定されたファイルの出力ストリームを取得する。
     *
     *

     * @param path ファイルパス
     * *
     * @return 指定されたファイルの出力ストリーム
     * *
     * @throws RuntimeException ファイルが見つからない。
     */
    fun getOutputStream(path: String): BufferedOutputStream {

        try {
            val os = FileOutputStream(path)
            return BufferedOutputStream(os)
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }

    }
}
