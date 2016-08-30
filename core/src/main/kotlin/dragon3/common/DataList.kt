package dragon3.common


import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap

import dragon3.data.Data
import mine.io.JsonManager

/**
 * @author k-saito
 */
class DataList<T : Data>
@SuppressWarnings("unchecked")
constructor(jsonManager: JsonManager, baseDir: String, fileList: List<String>, clazz: Class<Array<T>>) {

    private val list: MutableList<T>

    private val map: MutableMap<String, T>

    init {

        list = ArrayList<T>()
        map = HashMap<String, T>()

        for (file in fileList) {
            val filePath = baseDir + file
            for (t in jsonManager.read(filePath, clazz)) {
                list.add(t)
                map.put(t.id, t)
            }

        }
    }

    fun getList(): List<T> {
        return list
    }

    fun getData(id: String): T {
        if (map.containsKey(id)) {
            return map[id]!!
        } else {
            throw IllegalArgumentException("illegal data id:" + id)
        }
    }

    val idAndName: Map<String, String>
        get() {
            val idAndName = LinkedHashMap<String, String>()

            for (i in list.indices) {
                idAndName.put(list[i].id, list[i].name)
            }
            return idAndName
        }
}
