package dragon3.common

import java.io.Serializable
import java.util.ArrayList
import java.util.LinkedHashSet

import dragon3.attack.calc.HitRate
import dragon3.common.constant.BodyAttribute
import dragon3.common.constant.DeployType
import dragon3.common.constant.GameColor
import dragon3.data.BodyData

@SuppressWarnings("serial")
class Body : Serializable, Cloneable {

    @JvmField var base = BodyData()

    var color = GameColor.NONE

    var deployType: DeployType? = null

    var hp: Int = 0
    var hpMax: Int = 0
    var str: Int = 0
    var def: Int = 0
    var mst: Int = 0
    var mdf: Int = 0
    var hit: Int = 0
    var mis: Int = 0

    var wazaList: MutableList<String> = ArrayList()

    var attrSet = LinkedHashSet<BodyAttribute>()

    var x: Int = 0
    var y: Int = 0
    var scope: Int = 0
    var range: Int = 0
    var limitTurn: Int = 0
    var goalX: Int = 0
    var goalY: Int = 0

    var level: Int = 0
    var exp: Int = 0

    var store: Int = 0
    var imageNum: Int = 0

    var master: Boolean = false

    fun setMax() {
        hpMax = base.hp / 10
        hp = hpMax
        str = base.str / 10
        def = base.def / 10
        mst = base.mst / 10
        mdf = base.mdf / 10
        hit = base.hit / 10
        mis = base.mis / 10
        resetStore()
    }

    val isAlive: Boolean
        get() = hp > 0

    fun resetAttr() {
        attrSet.clear()
        attrSet.addAll(base.attrList)
    }

    fun addAttr(attr: BodyAttribute) {
        attrSet.add(attr)
    }

    fun removeAttr(attr: BodyAttribute) {
        attrSet.remove(attr)
    }

    fun hasAttr(attr: BodyAttribute): Boolean {
        return attrSet.contains(attr)
    }

    fun resetWaza() {
        val wazaList = ArrayList(base.wazaList)
        wazaList.removeIf { a -> a == "none" }
        this.wazaList = wazaList
    }

    fun clearWaza() {
        wazaList.clear()
    }

    fun restrict() {
        hpMax = Math.min(999, hpMax)
        str = Math.min(999, str)
        def = Math.min(999, def)
        mst = Math.min(999, mst)
        mdf = Math.min(999, mdf)
        hit = Math.min(999, hit)
        mis = Math.min(999, mis)
    }

    //		if (hasAttr(BodyAttribute.OIL))
    //			step = 1;
    val moveStep: Int
        get() {
            var step = base.moveStep
            if (hasAttr(BodyAttribute.MOVE_UP_1))
                step++
            if (hasAttr(BodyAttribute.MOVE_UP_2))
                step += 2
            if (hasAttr(BodyAttribute.MOVE_DOWN_1))
                step -= 1
            if (hasAttr(BodyAttribute.OIL))
                step /= 2
            return step
        }

    fun resetStore() {
        store = HitRate.SINGLE_HIT / 2
    }

    fun getBase() :BodyData {
        return base
    }
}
