package mine.awt;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import mine.event.MouseAllListener;
import mine.event.MouseManager;

/**
 * マウスの状態を保持します。
 * @author k-saito
 * @version 1.0
 */
public class MouseManagerAWT implements MouseListener, MouseMotionListener, MouseManager {

	private int x, y;
	private boolean left, right;
	private MouseAllListener listener;
	private Thread thread;

	/**
	 * コンストラクタ
	 */
	public MouseManagerAWT() {
		reset();
	}

	/**
	 * コンストラクタ
	 * 指定されたコンポーネントに自分をリスナー登録します。
	 * @param c - 指定されたコンポーネント
	 */
	public MouseManagerAWT(Component c) {
		super();
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
	}

	/**
	 * リスナーをセットする
	 */
	public void setMouseAllListener(MouseAllListener listener){
		this.listener = listener;
	}
	
	/**
	 * リスナーを削除する
	 */
	public void removeMouseAllListener(){
		listener = null;
	}

	/**
	 * 状態を初期化します。
	 */
	public void reset() {
		x = 0;
		y = 0;
		left = false;
		right = false;
	}

	/**
	 * @return マウススレッドの実行されていればtrue
	 */
	public boolean isAlive() {
		return (thread != null && thread.isAlive());
	}

	/**
	 * @return マウスの現在位置X座標
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return マウスの現在位置Y座標
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return 左ボタンが押されていればtrue
	 */
	public boolean isLeft() {
		return left;
	}

	/**
	 * @return 右ボタンが押されていればtrue
	 */
	public boolean isRight() {
		return right;
	}

	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		mouseEntered();
	}
	public void mouseExited(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		mouseExited();
	}

	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		mouseMoved();
	}

	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if (SwingUtilities.isLeftMouseButton(e)) {
			leftDragged();
		} else if (SwingUtilities.isRightMouseButton(e)) {
			rightDragged();
		}
	}

	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if (SwingUtilities.isLeftMouseButton(e)) {
			left = false;
			leftReleased();
		} else if (SwingUtilities.isRightMouseButton(e)) {
			right = false;
			rightReleased();
		}
	}

	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if (SwingUtilities.isLeftMouseButton(e)) {
			left = true;
			leftPressed();
		} else if (SwingUtilities.isRightMouseButton(e)) {
			right = true;
			rightPressed();
		}
	}

	public void mouseMoved(){
		if (listener != null) {
			listener.mouseMoved(x, y);
		}
	}
	public void mouseEntered(){
		if (listener != null) {
			listener.mouseEntered(x, y);
		}
	}

	public void mouseExited(){
		if (listener != null) {
			listener.mouseExited(x, y);
		}
	}
	public void leftDragged(){
		if (listener != null) {
			listener.leftDragged(x, y);
		}
	}
	public void rightDragged(){
		if (listener != null) {
			listener.rightDragged(x, y);
		}
	}
	public void leftPressed(){
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(() -> listener.leftPressed(x, y) );
			thread.start();
		}
	}
	public void rightPressed(){
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(() -> listener.rightPressed(x, y) );
			thread.start();
		}
	}
	public void leftReleased(){
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(() -> listener.leftReleased(x, y) );
			thread.start();
		}
	}
	public void rightReleased(){
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(() -> listener.rightReleased(x, y) );
			thread.start();
		}
	}	

}
