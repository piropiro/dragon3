/*
 * Created on 2004/10/10
 */
package mine.game;

/**
 * @author saito
 */
public interface GameManager {
	/**
	 * 下キーが押されていればtrueを返す。<p>
	 * 
	 * @return 下キーが押されていればtrue
	 */
	public boolean isDown();

	/**
	 * 左キーが押されていればtrueを返す。<p>
	 * 
	 * @return 左キーが押されていればtrue
	 */
	public boolean isLeft();

	/**
	 * 右キーが押されていればtrueを返す。<p>
	 * 
	 * @return 右キーが押されていればtrue
	 */
	public boolean isRight();

	/**
	 * 上キーが押されていればtrueを返す。<p>
	 * 
	 * @return 上キーが押されていればtrue
	 */
	public boolean isUp();

	/**
	 * GameListenerをセットする。
	 * 
	 * @param gl
	 */
	public void setGameListener(GameListener gl);

	/**
	 * タイトル画面を表示する。<p>
	 */
	public void start();

	/**
	 * ゲームを開始する。<p>
	 */
	public void play(); 

	/**
	 * ゲームオーバー画面を表示する。<p>
	 */
	public void end();
}
