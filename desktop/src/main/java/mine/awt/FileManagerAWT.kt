package mine.awt

import mine.io.FileManager
import java.io.*

/**
 * Created by rara on 2016/08/29.
 */
class FileManagerAWT : FileManager {

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
    override fun getInputStream(path: String): BufferedInputStream {

        try {
            var ins: InputStream? = FileManager::class.java.getResourceAsStream("/" + path)
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
    override fun getOutputStream(path: String): BufferedOutputStream {

        try {
            val os = FileOutputStream(path)
            return BufferedOutputStream(os)
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }

    }

}
