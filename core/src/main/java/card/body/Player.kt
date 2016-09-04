package card.body

import card.CardWorks
import card.manage.CardManager


class Player(private val cardManager: CardManager) {

    private var openCardNum: Int = 0
    private var closeCardNum: Int = 0


    init {
        openCardNum = 0
        closeCardNum = 7
    }

    fun openCard(uw: CardWorks, n: Int): Boolean {
        if (closeCardNum == 0) return false
        if (openCardNum >= 3) return false

        if (cardManager.openBlue(uw, n)) {
            closeCardNum--
            openCardNum++
            return true
        } else {
            return false
        }
    }

    fun selectCard(n: Int): Card {
        openCardNum--
        return cardManager.getBlueCard(n)
    }

    fun doubleCard() {
        openCardNum--
    }

    fun hasCard(): Boolean {
        return openCardNum + closeCardNum > 0
    }
}
