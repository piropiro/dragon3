/*
 * 作成日: 2004/03/07
 */
package dragon3.data

import dragon3.common.constant.DeployType

/**
 * @author k-saito
 */
class DeployData : Data {

    override var id  = "none"
    override var name = "none"

    var bodyId = "none"
    var deployType = DeployType.NONE
    var level: Int = 0
    var scope: Int = 0
    var range: Int = 0
    var limitTurn: Int = 0
    var x: Int = 0
    var y: Int = 0
    var goalX: Int = 0
    var goalY: Int = 0


    override fun toString(): String {
        return bodyId + " - " + name
    }
}
