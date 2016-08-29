package dragon3.manage

import java.util.function.Consumer
import java.util.function.Supplier

import javax.inject.Inject

import dragon3.Statics
import dragon3.camp.Equip
import dragon3.common.Body
import dragon3.common.constant.BodyKind
import dragon3.common.constant.Texts
import dragon3.panel.PanelManager

class LevelManager
/*** Level Up  */
(private val equip: Equip, private val pm: PanelManager) {

    @Inject internal var statics: Statics? = null

    fun levelup(ba: Body) {

        // 装備レベルアップ
        for (item in equip.getEquipOf(ba).values) {
            itemLevelup(item)
        }

        // キャラレベルアップ
        charaLevelup(ba)

        // 技習得
        learnWaza(ba)

        pm.startMessage(ba)
    }

    private fun charaLevelup(ba: Body) {
        while (ba.exp >= MAX_EXP) {
            ba.exp = ba.exp - MAX_EXP
            if (ba.base.name.length <= 1) {
                pm.addMessage(ba.base.name + Texts.ha + Texts.equip1)
            } else {
                pm.addMessage(ba.base.name + Texts.ha)
                pm.addMessage(Texts.equip1)
            }
            statusup(ba)
            ba.level = ba.level + 1
        }
    }

    private fun itemLevelup(item: Body?) {
        if (item == null)
            return

        if (item.exp >= MAX_EXP) {
            item.exp = MAX_EXP

            if (!item.master) {
                item.master = true
                pm.addMessage(item.base.kind.text + Texts.equip1)
            }
        }
    }

    private fun learnWaza(ba: Body) {
        val wazaList = equip.equipWaza(ba)

        for (wazaId in wazaList) {
            if (!ba.wazaList.contains(wazaId)) {
                val waza = statics!!.getWazaData(wazaId)
                if (waza.name.length <= 5) {
                    pm.addMessage(waza.name + Texts.wo + Texts.equip2)
                } else {
                    pm.addMessage(waza.name + Texts.wo)
                    pm.addMessage(Texts.equip2)
                }
            }
        }
        ba.wazaList = wazaList
    }

    private fun statusup(name: String, baseGet: () -> Int, baseSet: (Int) -> Unit, up: Int,
                         currentGet: () -> Int, currentSet: (Int) -> Unit) {

        // ベースステータス上昇
        val before = baseGet()
        baseSet(before + up)

        // 上昇値を計算
        val ns = (before + up) / 10 - before / 10
        if (ns > 0)
            pm.addMessage(name + Texts.ga + " " + ns + Texts.equip3)

        // 現在のステータスに上昇値を加算
        val current = currentGet()
        currentSet(current + ns)
    }

    private fun statusup(ba: Body) {

        val list = equip.getEquipOf(ba)
        val clazz = list[BodyKind.CLASS]

        if (clazz != null) {
            statusup(Texts.hp, { ba.base.hp }, { ba.base.hp = it }, clazz.hp, { ba.hpMax }, { ba.hpMax = it })
            statusup(Texts.kougekiryoku, { ba.base.str }, { ba.base.str = it }, clazz.str, { ba.str }, { ba.str = it })
            statusup(Texts.bougyoryoku,  { ba.base.def }, { ba.base.def = it }, clazz.def, { ba.def }, { ba.def = it })
            statusup(Texts.mahouryoku,   { ba.base.mst }, { ba.base.mst = it }, clazz.mst, { ba.mst }, { ba.mst = it })
            statusup(Texts.teikouryoku,  { ba.base.mdf }, { ba.base.mdf = it }, clazz.mdf, { ba.mdf }, { ba.mdf = it })
            statusup(Texts.meichuritu,   { ba.base.hit }, { ba.base.hit = it }, clazz.hit, { ba.hit }, { ba.hit = it })
            statusup(Texts.kaihiritu,    { ba.base.mis }, { ba.base.mis = it }, clazz.mis, { ba.mis }, { ba.mis = it })
        }
    }

    companion object {

        const val MAX_EXP = 100
    }
}
