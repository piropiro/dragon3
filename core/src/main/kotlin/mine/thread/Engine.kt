package mine.thread

/**
 * スレッドを管理するクラス。
 * 指定されたランナーを無限ループで走らせる。
 * @author k-saito
 * *
 * @version 1.0
 */
class Engine : Runnable {

    private var th: Thread? = null
    private var runner: Runnable? = null

    init {
        th = null
        runner = null
    }

    /**
     * ランナーを変更する。
     * @param runner - 実行されるランナー
     */
    fun setRunner(runner: Runnable) {
        this.runner = runner
    }

    /**
     * ランナーが走っていればtrue
     */
    val isRunning: Boolean
        get() = th != null && th!!.isAlive

    /**
     * ランナーを走らせる。
     */
    fun start() {
        if (!isRunning) {
            th = Thread(this)
            th!!.start()
        }
    }

    /**
     * ランナーを止める。
     */
    fun stop() {
        th = null
    }

    /**
     * ランナーをループさせて実行する。
     */
    override fun run() {
        val ths = Thread.currentThread()
        while (th === ths) {
            runner!!.run()
            Thread.`yield`()
        }
    }

    companion object {

        /**
         * 指定された時間だけランナーを止める。
         * @param msec - 止める時間（ミリ秒）
         */
        fun sleep(msec: Long) {
            try {
                Thread.sleep(msec)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
/**
 * コンストラクタ
 */
