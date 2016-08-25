/*
 * Created on 2004/10/10
 */
package mine.event;

/**
 * @author saito
 */
public interface KeyManager {
	/*** Reset ******************************/
	public abstract void reset();

	/*** Get Data **********************************/
	public abstract boolean isEnter();

	/**
	 * @return Escapeキーが押されていればtrue
	 */
	public abstract boolean isEscape();

	/**
	 * @return Shiftキーが押されていればtrue
	 */
	public abstract boolean isShift();

	/**
	 * @return スペースキーが押されていればtrue
	 */
	public abstract boolean isSpace();

	/**
	 * @return 方向キーの上が押されていればtrue
	 */
	public abstract boolean isUp();

	/**
	 * @return 方向キーの下が押されていればtrue
	 */
	public abstract boolean isDown();

	/**
	 * @return 方向キーの左が押されていればtrue
	 */
	public abstract boolean isLeft();

	/**
	 * @return 方向キーの右が押されていればtrue
	 */
	public abstract boolean isRight();

	/**
	 * @return 方向キーが押されていなければtrue
	 */
	public abstract boolean isNeutral();

	/**
	 * @param c - 指定されたキー文字
	 * @return 指定された文字が押されていればtrue
	 */
	public abstract boolean isKey(char c);

	/**
	 * キーの状態を設定する。
	 * @param c
	 * @param b
	 */
	public abstract void setKey(char c, boolean b);

	public abstract void keyPressed(char character, int keyCode);

	public abstract void keyReleased(char character, int keyCode);
}