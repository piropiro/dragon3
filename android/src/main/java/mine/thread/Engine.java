package mine.thread;

/**
 * スレッドを管理するクラス。
 * 指定されたランナーを無限ループで走らせる。
 * @author k-saito
 * @version 1.0
 */
public class Engine implements Runnable {

	private Thread th;
	private Runnable runner;

	/**
	 * コンストラクタ
	 */
	public Engine() {
		th = null;
		runner = null;
	}

	/**
	 * ランナーを変更する。
	 * @param rc - 実行されるランナー
	 */
	public void setRunner(Runnable runner) {
		this.runner = runner;
	}

	/**
	 * ランナーが走っていればtrue
	 */
	public boolean isRunning() {
		return (th != null && th.isAlive());
	}

	/**
	 * ランナーを走らせる。
	 */
	public void start() {
		if (!isRunning()) {
			th = new Thread(this);
			th.start();
		}
	}

	/**
	 * ランナーを止める。
	 */
	public void stop() {
		th = null;
	}

	/**
	 * ランナーをループさせて実行する。
	 */
	public void run() {
		Thread ths = Thread.currentThread();
		while (th == ths) {
			runner.run();
			Thread.yield();
		}
	}

	/**
	 * 指定された時間だけランナーを止める。
	 * @param msec - 止める時間（ミリ秒）
	 */
	public static void sleep(long msec) {
		try {
			Thread.sleep(msec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
