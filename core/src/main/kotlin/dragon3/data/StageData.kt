/*
 * 作成日: 2004/03/07
 */
package dragon3.data

import dragon3.stage.StageBack

/**
 * @author k-saito
 */
class StageData : Data {

    override var id = "none"
    override var name = "none"
    var level: Int = 0
    var back = StageBack.WHITE

    override fun toString(): String {
        return id + " - " + name
    }
}
