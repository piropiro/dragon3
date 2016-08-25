package dragon3.save

import java.io.Serializable
import java.util.ArrayList
import java.util.HashMap

import dragon3.common.Body
import dragon3.stage.StageStatus

class SaveData : Serializable, Cloneable {

    var reverse: Boolean = false
    var allClear: Boolean = false
    var score: Int = 0
    var turn: Int = 0
    var dead: Int = 0
    var kill: Int = 0
    var item: Int = 0
    var escape: Int = 0
    var save: Int = 0
    var playTime: Long = 0
    var playerName = "Player"

    var bodyList :List<Body> = ArrayList<Body>()
    var stageStatusMap = HashMap<String, StageStatus>()

    fun copy(): SaveData {
        try {
            return this.clone() as SaveData
        } catch (e: CloneNotSupportedException) {
            throw RuntimeException(e)
        }

    }

    fun countTurn() {
        turn++
    }

    fun countDead() {
        dead++
    }

    fun countKill() {
        kill++
    }

    fun countItem() {
        item++
    }

    fun countEscape() {
        escape++
    }

    fun countSave() {
        save++
    }

    fun addTime(n: Long) {
        playTime += n
    }

    fun getStageStatus(stageId: String): StageStatus {
        if (!stageStatusMap.containsKey(stageId)) {
            val stageStatus = StageStatus()
            stageStatus.stageId = stageId
            stageStatusMap.put(stageId, stageStatus)
        }
        return stageStatusMap[stageId]!!
    }

    fun getStarNum(stageId: String): Int {
        return getStageStatus(stageId).star
    }

    fun countStarNum(stageId: String) {
        val status = getStageStatus(stageId)
        status.star = status.star + 1
    }

    fun setOpened(stageId: String, flag: Boolean) {
        val status = getStageStatus(stageId)
        status.opened = flag
    }

    fun sumStars(): Int {
        var sum = 0
        for (status in stageStatusMap.values) {
            sum += status.star
        }
        return sum
    }

}
