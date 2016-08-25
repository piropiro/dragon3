package dragon3.data

import java.util.ArrayList

import dragon3.common.constant.AttackEffect
import dragon3.common.constant.DamageType
import dragon3.common.constant.EnergyType
import dragon3.common.constant.GameColor
import dragon3.common.constant.TargetType

class WazaData : Data {

    override var id = "none"
    override var name = "none"
    var label = "none"
    var image = "none.png"
    var labelColor = GameColor.NONE

    var damageType = DamageType.NONE
    var targetType = TargetType.SINGLE_1
    var animeId = "none"
    var energyType = EnergyType.NONE
    var energyCost: Int = 0

    var effect = ArrayList<AttackEffect>()

    override fun toString(): String {
        return id + " - " + name
    }
}
