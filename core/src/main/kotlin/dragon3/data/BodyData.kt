/*
 * 作成日: 2004/03/06
 */
package dragon3.data

import java.util.ArrayList

import dragon3.common.constant.ArmorType
import dragon3.common.constant.BodyAttribute
import dragon3.common.constant.BodyKind
import dragon3.common.constant.MoveType
import dragon3.common.constant.SoulType
import dragon3.common.constant.WeponType

/**
 * @author k-saito
 */
class BodyData : Data, Cloneable {

    override var id = "none"
    override var name = "none"
    var image = "none.png"
    var kind = BodyKind.CHARA

    var hp: Int = 0
    var str: Int = 0
    var def: Int = 0
    var mst: Int = 0
    var mdf: Int = 0
    var hit: Int = 0
    var mis: Int = 0
    var moveStep: Int = 0
    var moveType = MoveType.NONE
    var soulType = SoulType.NONE

    var weponType = WeponType.NONE
    var armorType = ArmorType.NONE

    var wazaList :List<String> = ArrayList<String>()
    var attrList :List<BodyAttribute> = ArrayList<BodyAttribute>()

    override fun toString(): String {
        return id + " - " + name
    }

    fun copy(): BodyData {
        try {
            return this.clone() as BodyData
        } catch (e: CloneNotSupportedException) {
            throw RuntimeException(e)
        }

    }
}
