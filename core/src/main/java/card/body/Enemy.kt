package card.body

import card.CardWorks
import card.common.ImageList
import card.manage.BattleManager
import card.manage.CardManager


/**
 * @author k-saito
 */
class Enemy(private val canvas: CardWorks, private val battleManager: BattleManager, private val cardManager: CardManager) {

    private var openCardNum: Int = 0
    private var closeCardNum: Int = 7

    var red: List<Card> = listOf()

    fun setupCard(ns: List<Int>, il: ImageList) {
        red = ns.mapIndexed { n, i -> Card(n, 32*(2+i), 32*4, Card.RED, il) }
    }

    fun addCards(uw: CardWorks) {
        red.forEach { uw.addCard(it) }
    }

    fun openCard(uw: CardWorks): Boolean {
        if (closeCardNum == 0) return false

        while (true) {
            val n = canvas.nextInt(7)
            if (cardManager.openRed(uw, n)) {
                openCardNum++
                closeCardNum--
                return true
            }
        }
    }

    /**
     * バトルに出すカードを選択する。
     *
     *

     * 赤と青のどちらかがリーチの場合
     * ①.青の最大カードに勝てる最小カードを選択する。
     * ②.①がない場合は青の最大カードと引き分けるカードを選択する。
     * ③.②がない場合はランダムで選択する。
     *
     *
     * 赤と青のどちらもリーチでない場合
     * ①ランダムで選択する。
     *
     *

     * @return バトルに出すカード
     */
    fun selectCard(): Card {
        val redWin = battleManager.redWin
        val blueWin = battleManager.blueWin
        val red = cardManager.openRedCards
        val blueMax = max(cardManager.openBlueCards)

        return if (redWin == 2 || blueWin == 2) {
            selectMinCard(red, blueMax) ?:
                selectMinCard(red, blueMax -1) ?:
                selectRandomCard(red)
        } else {
            selectRandomCard(red)
        }
    }


    /**
     * カードの数字の最大値を返す。
     *
     *

     * @param card
     * *
     * @return
     */
    private fun max(card: List<Card>): Int {
        return card.maxBy { it.number }?.number ?: -1
    }

    /**
     * ランダムに選択する。
     *
     * @param card
     * @return
     */
    private fun selectRandomCard(card: List<Card>): Card {
        val n = canvas.nextInt(card.size)
        return card[n]
    }

    /**
     * ボーダー以上で最小のカードを選択する。
     *
     * @param red
     * @param border
     * @return
     */
    private fun selectMinCard(red: List<Card>, border: Int): Card? {
        return red.filter { it.number > border }.minBy { it.number }
    }
}
