package mine

import mine.io.FileManager
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintStream
import java.io.PrintWriter
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.StringTokenizer


/**

 * @author k-saito
 */
object MineUtils {

    /**
     * OSがWindowsかどうかを判定する。
     *
     *

     * @return
     */
    val isWindows: Boolean
        get() {
            val osname = System.getProperty("os.name")
            if (osname.length < 7) {
                return false
            }
            return osname.substring(0, 7) == "Windows"
        }

    /**
     * オブジェクトのゲッターメソッドを実行する。
     *
     *

     * @param obj
     * *
     * @param field
     * *
     * @return
     */
    fun getField(obj: Any, field: String): Any {
        try {
            val methodName = Character.toUpperCase(field[0]) + field.substring(1)
            val method = obj.javaClass.getDeclaredMethod("get" + methodName)
            return method.invoke(obj)
        } catch (e: ReflectiveOperationException) {
            throw RuntimeException(e)
        }

    }

    /**
     * オブジェクトのセッターメソッドを実行する。
     *
     *

     * @param obj
     * *
     * @param field
     * *
     * @param value
     */
    fun setField(obj: Any, field: String, value: Any) {
        try {
            val methodName = Character.toUpperCase(field[0]) + field.substring(1)
            val clazz = obj.javaClass.getDeclaredMethod("get" + methodName).returnType
            val method = obj.javaClass.getDeclaredMethod("set" + methodName, clazz)
            method.invoke(obj, *arrayOf(value))
        } catch (e: ReflectiveOperationException) {
            throw RuntimeException(e)
        }

    }

    /**
     * ファイルから文字列を読み込む。

     * @param file
     * *
     * @return
     */
    fun readStringArray(fm: FileManager, file: String): Array<String> {
        try {
            BufferedReader(InputStreamReader(fm.getInputStream(file))).use { `in` ->
                val list = ArrayList<String>()
                var s: String? = `in`.readLine()
                while (s != null) {
                    list.add(s)
                    s = `in`.readLine()
                }
                return list.toTypedArray()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    /**
     * ファイルに文字列を書き込む。

     * @param file
     * *
     * @param list
     */
    fun writeStringArray(fm: FileManager, file: String, list: Array<String>) {
        PrintWriter(OutputStreamWriter(fm.getOutputStream(file))).use { out ->
            for (str in list) {
                out.println(str)
            }
        }
    }

    /**
     * ファイルからIDとテキストを読み込む。
     *
     *

     * @param file
     * *
     * @return
     */
    fun readIdAndTextMap(fm: FileManager, file: String): Map<String, String> {
        val map = LinkedHashMap<String, String>()

        try {
            BufferedReader(InputStreamReader(fm.getInputStream(file))).use { input ->
                var s: String? = input.readLine()
                while (s != null) {
                    val st = StringTokenizer(s)
                    if (st.countTokens() == 2) {
                        map.put(st.nextToken(), st.nextToken());
                    }
                    s = input.readLine()
                }
                return map
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    /**
     * エラーをファイルに出力する。
     *
     *

     * @param filename
     */
    fun setErr(filename: String) {
        try {
            val fos = FileOutputStream(filename)
            System.setErr(PrintStream(fos))
        } catch (e: Exception) {
            println(e)
        }

    }

    /**
     * ３つの数字の中間値を取得する。
     *
     *

     * @param s
     * *
     * @param m
     * *
     * @param b
     * *
     * @return
     */
    fun mid(s: Int, m: Int, b: Int): Int {
        return Math.max(s, Math.min(m, b))
    }

    /**
     * 反射角を求める。
     *
     *

     * @param anga
     * *
     * @param angb
     * *
     * @return
     */
    fun refAngle(anga: Double, angb: Double): Double {
        var angle = anga - angb
        while (angle < 0) {
            angle += Math.PI * 2
        }
        while (angle >= Math.PI * 2) {
            angle -= Math.PI * 2
        }
        if (angle <= Math.PI / 2) {
            return anga
        }
        if (angle >= Math.PI * 3 / 2) {
            return anga
        }

        angle = angb * 2 - anga - Math.PI
        while (angle < 0) {
            angle += Math.PI * 2
        }
        while (angle >= Math.PI * 2) {
            angle -= Math.PI * 2
        }
        return angle
    }

    /**
     * 二次元配列をAffine変換する。
     *
     *

     * @param src
     * *
     * @param dst
     * *
     * @param af
     */
    fun affine(
            src: Array<IntArray>,
            dst: Array<IntArray>,
            af: IntArray) {

        for (y in src.indices) {
            for (x in 0..src[0].size - 1) {
                val xs = x * af[0] + y * af[1] + af[2]
                val ys = x * af[3] + y * af[4] + af[5]
                if (xs < 0 || xs >= dst[0].size) {
                    continue
                }
                if (ys < 0 || ys >= dst.size) {
                    continue
                }
                dst[ys][xs] = src[y][x]
            }
        }
    }

//    /**
//     * 二次元配列を一次元配列に並べ替える。
//     *
//     *
//
//     * @param src
//     * *
//     * @param dst
//     * *
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    fun <T> linerize(src: Array<Array<T>>, dst: Array<T>): Array<T> {
//        var dst = dst
//
//        var size = 0
//        for (src1 in src) {
//            if (src1 == null) {
//                continue
//            }
//            size += src1.size
//        }
//
//        if (dst.size < size) {
//            dst = java.lang.reflect.Array.newInstance(
//                    dst.javaClass.componentType, size) as Array<T>
//        }
//
//        var pos = 0
//        for (src1 in src) {
//            if (src1 == null) {
//                continue
//            }
//            System.arraycopy(src1, 0, dst, pos, src1.size)
//            pos += src1.size
//        }
//
//        if (dst.size > size) {
//            dst[size] = null
//        }
//
//        return dst
//    }

    /**
     * オブジェクトの配列をリストに挿入する。
     *
     *

     * @param
     * *
     * @param list
     * *
     * @param objs
     * *
     * @return
     */
    @Deprecated("")
    fun <E> addToList(list: MutableList<E>, objs: Array<E>): List<E> {
        list.addAll(Arrays.asList(*objs))
        return list
    }

    /**
     * 配列の比較

     * @param a
     * *
     * @param b
     * *
     * @return
     */
    fun compare(a: Array<IntArray>, b: Array<IntArray>): Boolean {
        for (i in a.indices) {
            for (j in 0..a[i].size - 1) {
                if (a[i][j] != b[i][j]) {
                    return false
                }
            }
        }
        return true
    }
}
