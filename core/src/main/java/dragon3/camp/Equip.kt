package dragon3.camp

import dragon3.common.Body
import dragon3.common.constant.BodyKind
import dragon3.common.constant.GameColor
import java.util.*

class Equip
(private val equipList: MutableList<Body>) {

    /*** Search  */

    fun search(x: Int, y: Int): Body? {
        return equipList.firstOrNull { it.x == x && it.y == y }
    }

    fun searchItem(x: Int, y: Int): Body? {
        return equipList.firstOrNull { it.color == GameColor.GREEN && it.x == x && it.y == y }
    }

    fun addBody(b: Body) {
        equipList.add(b)
    }

    fun removeBody(b: Body) {
        equipList.remove(b)
    }

    val equips: List<Body>
        get() = equipList

    /*** EXP  */

    fun getExp(ba: Body, bb: Body) {

        val exp = (BASE_EXP * Math.pow(1.5, (bb.level - ba.level).toDouble())).toInt()
        ba.exp = ba.exp + exp
        bb.exp = 0

        // Item Exp
        for (item in getEquipOf(ba).values) {
            item.exp = item.exp + exp
        }
    }

    fun getEquipOf(ba: Body): Map<BodyKind, Body> {
        val list = HashMap<BodyKind, Body>()

        for (offset in 1..4) {
            val item = searchItem(ba.goalX + offset, ba.goalY)
            if (item != null) {
                list.put(item.getBase().kind, item)
            }
        }
        return list
    }


    /*** Player  */

    // Campの配置位置でソート
    val players: List<Body>
        get() {

            val playerList = ArrayList<Body>()

            for (b in equipList) {
                if (!GameColor.isPlayer(b.color))
                    continue
                b.setMax()
                equip(b)
                playerList.add(b)
            }
            Collections.sort(playerList, { b1, b2 -> b1.goalY - b2.goalY })

            return playerList
        }

    fun getChangeChara(ba: Body): Body? {
        var bb: Body? = null
        for (b in equipList) {
            if (!GameColor.isPlayer(b.color))
                continue
            if (b.goalX == ba.goalX + 7 && b.goalY == ba.goalY) {
                bb = b
                break
            }
        }
        if (bb == null)
            return null
        bb.setMax()
        equip(bb)
        return bb
    }

    /*** Equip  */

    fun equip(ba: Body) {
        ba.setMax()
        ba.resetAttr()

        val list = getEquipOf(ba)
        equip(ba, list[BodyKind.WEPON])
        equip(ba, list[BodyKind.ARMOR])
        equip(ba, list[BodyKind.ITEM])
        equip(ba, list[BodyKind.SOUL])

        val wazaList = equipWaza(ba)

        ba.wazaList = wazaList
        ba.restrict()
    }


    private fun equip(ba: Body, bb: Body?) {
        if (bb == null)
            return
        ba.str = ba.str + bb.str
        ba.def = ba.def + bb.def
        ba.mst = ba.mst + bb.mst
        ba.mdf = ba.mdf + bb.mdf
        ba.hit = ba.hit + bb.hit
        ba.mis = ba.mis + bb.mis
    }

    /***   */

    // 0 - Normal Attack
    // 1 - Wepon Attack
    // 2 - Armor Attack
    // 3 - Class Attack 1
    // 4 - Class Attack 2
    // 5 - Item Attack 1

    fun equipWaza(ba: Body): List<String> {
        val wazaList = ArrayList<String>()

        val list = getEquipOf(ba)


        // 技0 = 武器0 > 職業0 > キャラ0
        if (checkAddWaza(ba, list[BodyKind.WEPON], 0, false, false, wazaList)) {
        } else if (checkAddWaza(ba, list[BodyKind.CLASS], 0, false, false, wazaList)) {
        } else if (checkAddWaza(ba, ba, 0, false, false, wazaList)) {
        }

        // 武器1
        checkAddWaza(ba, list[BodyKind.WEPON], 1, true, true, wazaList)
        // 防具0
        checkAddWaza(ba, list[BodyKind.ARMOR], 0, true, true, wazaList)
        // 小物0
        checkAddWaza(ba, list[BodyKind.ITEM], 0, true, true, wazaList)
        // 魂玉0
        checkAddWaza(ba, list[BodyKind.SOUL], 0, true, true, wazaList)

        // キャラ1〜
        for (i in 1..ba.base.wazaList.size - 1) {
            checkAddWaza(ba, ba, 1, false, false, wazaList)
        }

        return wazaList
    }

    private fun checkAddWaza(body: Body, item: Body?, i: Int, levelLimit: Boolean, masterLimit: Boolean, wazaList: MutableList<String>): Boolean {
        if (item == null)
            return false

        val waza = item.base.wazaList[i]

        if (waza == "none")
            return false

        if (wazaList.contains(waza))
            return false

        if (levelLimit && body.level < item.level)
            return false

        if (masterLimit && !item.master) {
            return false
        }

        wazaList.add(waza)
        return true
    }


    fun have(ba: Body): Boolean {
        for (b in equipList) {
            if (ba.base.id != b.base.id) {
                continue
            } else if (isDust(b)) {
                continue
            } else {
                return true
            }
        }
        return false
    }


    private fun isDust(b: Body): Boolean {
        if (b.y != 14)
            return false
        if (b.x < 14)
            return false
        return true
    }

    companion object {

        val BASE_EXP = 20
    }

}
