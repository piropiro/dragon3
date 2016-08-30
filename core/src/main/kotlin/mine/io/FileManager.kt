package mine.io

import java.io.BufferedInputStream
import java.io.BufferedOutputStream

/**
 * Created by rara on 2016/08/29.
 */
interface FileManager {

    fun getInputStream(path: String): BufferedInputStream

    fun getOutputStream(path: String): BufferedOutputStream
}
