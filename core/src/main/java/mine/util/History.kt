package mine.util

import java.io.Serializable
import java.util.LinkedList

/**
 * 履歴を管理する。
 *
 *

 * @author k-saito
 */
class History<B> : Serializable {

    private val max :Int

    private val list = LinkedList<B>()

    constructor() {
        this.max = 10
    }

    /**
     * 履歴の最大数を指定してインスタンスを生成する。
     *
     *

     * @param max
     */
    constructor(max: Int) {
        this.max = max
    }

    /**
     * 追加
     *
     *

     * @param obj
     */
    fun add(obj: B?) {
        if (obj != null) {
            if (list.contains(obj)) {
                list.remove(obj)
            }

            list.addFirst(obj)

            if (list.size > max) {
                list.removeLast()
            }
        }
    }

    /**
     * 指定されたインデックスの履歴を返す。
     *
     *

     * @param i
     * *
     * @return
     */
    operator fun get(i: Int): B {
        return list[i]
    }

    fun size(): Int {
        return list.size
    }

    fun toArray(b: Array<B>): Array<B> {
        return list.toArray(b);
    }

    /**
     * @param src
     */
    fun reset(src: Array<B>) {
        list.clear()
        for (i in src.indices) {
            if (src[i] != null) {
                list.add(src[i])
            }
        }
    }

    fun clear() {
        list.clear()
    }

}
