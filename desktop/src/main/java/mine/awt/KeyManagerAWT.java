package mine.awt;

import java.awt.event.KeyEvent;

import mine.event.KeyManager;

/**
 * キーの状態を保持するクラス。
 * @author k-saito
 * @version 1.0
 */
public class KeyManagerAWT implements KeyManager {

	private boolean keys[];

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	private boolean enter;
	private boolean escape;
	private boolean shift;
	private boolean space;
	private boolean meta;

	/**
	 * コンストラクタ
	 */
	public KeyManagerAWT() {
		reset();
	}

	/**
	 * 状態を初期化します。
	 */
	public void reset() {
		keys = new boolean[256];
		up = false;
		down = false;
		left = false;
		right = false;
		enter = false;
		escape = false;
		shift = false;
		space = false;
		meta = false;
	}


	/**
	 * @return Enterキーが押されていればtrue
	 */
	public boolean isEnter() {
		return enter;
	}

	/**
	 * @return Escapeキーが押されていればtrue
	 */
	public boolean isEscape() {
		return escape;
	}

	/**
	 * @return Shiftキーが押されていればtrue
	 */
	public boolean isShift() {
		return shift;
	}

	/**
	 * @return Spaceキーが押されていればtrue
	 */
	public boolean isSpace() {
		return space;
	}

	/**
	 * @return Metaキーが押されていればtrue
	 */
	public boolean isMeta() {
		return meta;
	}

	/**
	 * @return 方向キーの上が押されていればtrue
	 */
	public boolean isUp() {
		return up;
	}

	/**
	 * @return 方向キーの下が押されていればtrue
	 */
	public boolean isDown() {
		return down;
	}

	/**
	 * @return 方向キーの左が押されていればtrue
	 */
	public boolean isLeft() {
		return left;
	}

	/**
	 * @return 方向キーの右が押されていればtrue
	 */
	public boolean isRight() {
		return right;
	}

	/**
	 * @return 方向キーが押されていなければtrue
	 */
	public boolean isNeutral() {
		if (up)
			return false;
		if (down)
			return false;
		if (left)
			return false;
		if (right)
			return false;
		return true;
	}

	/**
	 * @param c - 指定されたキー文字
	 * @return 指定された文字が押されていればtrue
	 */
	public boolean isKey(char c) {
		int i = (int) c;
		if (i < 256) {
			return keys[i];
		}
		return false;
	}

	/**
	 * キーの状態を設定する。
	 * @param c
	 * @param b
	 */
	public void setKey(char c, boolean b){
		int i = (int) c;
		if (i < 256) {
			keys[i] = b;
		}
	}

	public void keyPressed(char character, int keyCode) {
		setKey(character, keyCode, true);

	}
	public void keyReleased(char character, int keyCode) {
		setKey(character, keyCode, false);
	}

	/**
	 * 指定されたキーの状態を変更します。
	 */
	private void setKey(char character, int keyCode, boolean b) {
		setKey(character, b);
		if (keyCode < 256) {
			keys[keyCode] = b;
		}
		switch (keyCode) {
			case KeyEvent.VK_ENTER :
				enter = b;
				break;
			case KeyEvent.VK_ESCAPE :
				escape = b;
				break;
			case KeyEvent.VK_SHIFT :
				shift = b;
				break;
			case KeyEvent.VK_SPACE :
				space = b;
				break;
			case KeyEvent.VK_META :
				meta = b;
				break;
			case KeyEvent.VK_UP :
				up = b;
				break;
			case KeyEvent.VK_DOWN :
				down = b;
				break;
			case KeyEvent.VK_LEFT :
				left = b;
				break;
			case KeyEvent.VK_RIGHT :
				right = b;
				break;
		}
	}
}
