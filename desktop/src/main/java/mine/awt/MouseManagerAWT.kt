package mine.awt

import java.awt.Component
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener

import javax.swing.SwingUtilities

import mine.event.MouseAllListener
import mine.event.MouseManager

/**
 * マウスの状態を保持します。
 * @author k-saito
 * *
 * @version 1.0
 */
class MouseManagerAWT : MouseListener, MouseMotionListener, MouseManager {

    private var x: Int = 0
    private var y: Int = 0
    private var left: Boolean = false
    private var right: Boolean = false
    private var listener: MouseAllListener? = null
    private var thread: Thread? = null

    /**
     * コンストラクタ
     */
    constructor() {
        reset()
    }

    /**
     * コンストラクタ
     * 指定されたコンポーネントに自分をリスナー登録します。
     * @param c - 指定されたコンポーネント
     */
    constructor(c: Component) : super() {
        c.addMouseListener(this)
        c.addMouseMotionListener(this)
    }

    /**
     * リスナーをセットする
     */
    override fun setMouseAllListener(listener: MouseAllListener) {
        this.listener = listener
    }

    /**
     * リスナーを削除する
     */
    override fun removeMouseAllListener() {
        listener = null
    }

    /**
     * 状態を初期化します。
     */
    override fun reset() {
        x = 0
        y = 0
        left = false
        right = false
    }

    /**
     * @return マウススレッドの実行されていればtrue
     */
    override fun isAlive(): Boolean {
        return thread != null && thread!!.isAlive
    }

    /**
     * @return マウスの現在位置X座標
     */
    override fun getX(): Int {
        return x
    }

    /**
     * @return マウスの現在位置Y座標
     */
    override fun getY(): Int {
        return y
    }

    /**
     * @return 左ボタンが押されていればtrue
     */
    override fun isLeft(): Boolean {
        return left
    }

    /**
     * @return 右ボタンが押されていればtrue
     */
    override fun isRight(): Boolean {
        return right
    }

    override fun mouseClicked(e: MouseEvent) {
    }

    override fun mouseEntered(e: MouseEvent) {
        x = e.x
        y = e.y
        mouseEntered()
    }

    override fun mouseExited(e: MouseEvent) {
        x = e.x
        y = e.y
        mouseExited()
    }

    override fun mouseMoved(e: MouseEvent) {
        x = e.x
        y = e.y
        mouseMoved()
    }

    override fun mouseDragged(e: MouseEvent) {
        x = e.x
        y = e.y
        if (SwingUtilities.isLeftMouseButton(e)) {
            leftDragged()
        } else if (SwingUtilities.isRightMouseButton(e)) {
            rightDragged()
        }
    }

    override fun mouseReleased(e: MouseEvent) {
        x = e.x
        y = e.y
        if (SwingUtilities.isLeftMouseButton(e)) {
            left = false
            leftReleased()
        } else if (SwingUtilities.isRightMouseButton(e)) {
            right = false
            rightReleased()
        }
    }

    override fun mousePressed(e: MouseEvent) {
        x = e.x
        y = e.y
        if (SwingUtilities.isLeftMouseButton(e)) {
            left = true
            leftPressed()
        } else if (SwingUtilities.isRightMouseButton(e)) {
            right = true
            rightPressed()
        }
    }

    override fun mouseMoved() {
        if (listener != null) {
            listener!!.mouseMoved(x, y)
        }
    }

    override fun mouseEntered() {
        if (listener != null) {
            listener!!.mouseEntered(x, y)
        }
    }

    override fun mouseExited() {
        if (listener != null) {
            listener!!.mouseExited(x, y)
        }
    }

    override fun leftDragged() {
        if (listener != null) {
            listener!!.leftDragged(x, y)
        }
    }

    override fun rightDragged() {
        if (listener != null) {
            listener!!.rightDragged(x, y)
        }
    }

    override fun leftPressed() {
        if (thread == null || !thread!!.isAlive) {
            thread = Thread { listener!!.leftPressed(x, y) }
            thread!!.start()
        }
    }

    override fun rightPressed() {
        if (thread == null || !thread!!.isAlive) {
            thread = Thread { listener!!.rightPressed(x, y) }
            thread!!.start()
        }
    }

    override fun leftReleased() {
        if (thread == null || !thread!!.isAlive) {
            thread = Thread { listener!!.leftReleased(x, y) }
            thread!!.start()
        }
    }

    override fun rightReleased() {
        if (thread == null || !thread!!.isAlive) {
            thread = Thread { listener!!.rightReleased(x, y) }
            thread!!.start()
        }
    }

}
