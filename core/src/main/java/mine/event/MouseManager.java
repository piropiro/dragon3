/*
 * Created on 2004/10/11
 */
package mine.event;

/**
 * @author saito
 */
public interface MouseManager {
	/**
	 * リスナーをセットする
	 */
	public void setMouseAllListener(MouseAllListener listener);

	/**
	 * リスナーを削除する
	 */
	public void removeMouseAllListener();

	/**
	 * 状態を初期化します。
	 */
	public void reset();

	/**
	 * @return マウススレッドが実行されていればtrue
	 */
	public boolean isAlive();
	
	/**
	 * @return マウスの現在位置X座標
	 */
	public int getX();

	/**
	 * @return マウスの現在位置Y座標
	 */
	public int getY();

	/**
	 * @return 左ボタンが押されていればtrue
	 */
	public boolean isLeft();

	/**
	 * @return 右ボタンが押されていればtrue
	 */
	public boolean isRight();

	public void mouseMoved();

	public void mouseEntered();

	public void mouseExited();

	public void leftDragged();

	public void rightDragged();

	public void leftPressed();

	public void rightPressed();

	public void leftReleased();

	public void rightReleased();
}