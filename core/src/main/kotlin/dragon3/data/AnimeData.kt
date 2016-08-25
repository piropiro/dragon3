/*
 * 作成日: 2004/03/07
 */
package dragon3.data

import dragon3.common.constant.AnimeType

/**
 * @author k-saito
 */
class AnimeData : Data {

    override var id = "none"
    override var name = "none"
    var type = AnimeType.SINGLE
    var image = "none.png"
    var sleep: Int = 0

    override fun toString(): String {
        return id + " - " + name
    }
}
