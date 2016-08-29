package dragon3.manage

import java.util.Arrays

import javax.inject.Inject

import dragon3.Statics
import dragon3.common.Body
import dragon3.common.constant.BodyKind
import dragon3.common.constant.GameColor
import dragon3.common.constant.SoulType
import dragon3.common.constant.Texts
import dragon3.data.BodyData
import dragon3.data.load.BodyDataLoader
import dragon3.image.ImageManager
import dragon3.panel.PanelManager

class SoulManager
@Inject
constructor() {

    @field: Inject
    lateinit var statics: Statics

    @field: Inject
    lateinit var bodyDataLoader: BodyDataLoader

    @field: Inject
    lateinit var imageManager: ImageManager

    var count: Int = 0
        private set
    private var soul: Body? = null

    init {
        setup()
    }

    fun setup() {
        count = 0
        soul = null
    }

    fun getSoul(body: Body): Body? {
        count++
        if (count % 6 != 0)
            return null

        if (GameColor.isPlayer(body.color))
            return null

        val soul = bodyDataLoader.loadBodyData(body.base.id, body.level)

        soul.base.kind = BodyKind.SOUL
        soul.color = GameColor.GREEN
        soul.base.image = soul.base.soulType.image
        soul.imageNum = imageManager.bodyImageList.getNum(soul.base.image)
        // 技3のみ継承する
        val wazaList = Arrays.asList(soul.base.wazaList[3])
        soul.base.wazaList = wazaList
        soul.resetWaza()

        when (soul.base.soulType) {
            SoulType.BLUE -> changeStatus(soul.base, { soul.base.mst },  { soul.base.mst = it })
            SoulType.RED -> changeStatus(soul.base,  { soul.base.str },  { soul.base.str = it })
            SoulType.GREEN -> changeStatus(soul.base, { soul.base.hit }, { soul.base.hit = it })
            SoulType.PINK -> changeStatus(soul.base,  { soul.base.mdf }, { soul.base.mdf = it })
            SoulType.YELLOW -> changeStatus(soul.base, { soul.base.def }, { soul.base.def = it })
            SoulType.SKY -> changeStatus(soul.base, { soul.base.mis },  { soul.base.mis =it })
            SoulType.ORANGE -> changeStatus(soul.base, { soul.base.hp },  { soul.base.hp = it })
            else -> Unit
        }
        soul.setMax()

        return soul
    }

    fun changeStatus(base: BodyData, baseGet: () -> Int, baseSet: (Int) -> Unit) {
        val n = baseGet() / 10
        base.hp = 0
        base.str = 0
        base.def = 0
        base.mst = 0
        base.mdf = 0
        base.hit = 0
        base.mis = 0
        baseSet(n)
    }

    fun message(pm: PanelManager) {
        if (soul == null)
            return
        pm.addMessage(soul!!.getBase().name + Texts.ha)
        pm.addMessage(Texts.material1)
        pm.addMessage(soul!!.getBase().soulType.text + "色の魂玉を手に入れた。")
        pm.addMessage(Texts.material5)
        pm.startMessage(soul)
        soul = null
    }

}
