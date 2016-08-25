package mine.awt;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import mine.game.GameListener;
import mine.game.GameManager;
import mine.paint.MineGraphics;
import mine.thread.Engine;

/**
 * 簡易ゲームを作るためのベースクラス。<p>
 * 
 * @author k-saito
 *
 */
@SuppressWarnings("serial")
public class GameCanvas extends JComponent implements Runnable, KeyListener, MouseListener, GameManager {

	private static final int PAINT_START = 0;
	private static final int PAINT_GAME = 1;
	private static final int PAINT_END = 2;



	private GameListener gl;

	private KeyManagerAWT km; // キーの状態を管理する。
	private Engine engine; // メインループを回す。

	private int paint_type; // 描画タイプ

	private int sleep_time; // メインループのスリープ時間


	/**
	 * 画面サイズとスリープ時間を指定してインスタンスを生成する。<p>
	 * 
	 * @param width 画面の横幅
	 * @param height 画面の縦幅
	 * @param sleep_time メインループのスリープ時間（ミリ秒）
	 */
	public GameCanvas(int width, int height, int sleep_time) {
		super();
		this.sleep_time = sleep_time;
		MineAwtUtils.setSize(this, width, height);

		engine = new Engine();
		engine.setRunner(this);

		km = new KeyManagerAWT();
		addKeyListener(this);
		
		addMouseListener(this);
	}

	/**
	 * GameListenerをセットする。
	 * 
	 * @param gl
	 */
	public void setGameListener(GameListener gl) {
		this.gl = gl;
	}

	/**
	 * タイトル画面を表示する。<p>
	 */
	public void start() {
		paint_type = PAINT_START;
		engine.stop();
		repaint();
	}

	/**
	 * ゲームを開始する。<p>
	 */
	public void play() {
		paint_type = PAINT_GAME;
		engine.start();
	}


	/**
	 * ゲームオーバー画面を表示する。<p>
	 */
	public void end() {
		paint_type = PAINT_END;
		engine.stop();
		repaint();
	}


	/*
	 * メインループ処理<p>
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		repaint();
		Engine.Companion.sleep(sleep_time);
	}


	/* 
	 * ペイントタイプに従って描画メソッドを呼び出す。<p>
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		MineGraphics mg = new GraphicsAWT(g);
		switch (paint_type) {
			case PAINT_START :
				gl.paintStart(mg);
				break;
			case PAINT_END :
				gl.paintEnd(mg);
				break;
			case PAINT_GAME :
				gl.paintGame(mg);
				break;
		}
	}

	/*
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
	}

	/*
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		km.keyReleased(e.getKeyChar(), e.getKeyCode());
	}

	/*
	 * Z,X,Cが押された場合、対応したメソッドを呼び出す。<p>
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		km.keyPressed(e.getKeyChar(), e.getKeyCode());

		switch (e.getKeyChar()) {
			case 'z' :
				switch (paint_type) {
					case PAINT_START:
						gl.init();
						play();
						break;
					case PAINT_GAME:
						gl.z_key();
						break;
					case PAINT_END:
						start();
						break;
				}
				break;
			case 'x' :
				if (paint_type == PAINT_GAME) {
					gl.x_key();
				}
				break;
			case 'c' :
				if (paint_type == PAINT_GAME) {
					gl.c_key();
				}
				break;
		}
	}

	/**
	 * 下キーが押されていればtrueを返す。<p>
	 * 
	 * @return 下キーが押されていればtrue
	 */
	public boolean isDown() {
		return km.isDown();
	}

	/**
	 * 左キーが押されていればtrueを返す。<p>
	 * 
	 * @return 左キーが押されていればtrue
	 */
	public boolean isLeft() {
		return km.isLeft();
	}

	/**
	 * 右キーが押されていればtrueを返す。<p>
	 * 
	 * @return 右キーが押されていればtrue
	 */
	public boolean isRight() {
		return km.isRight();
	}

	/**
	 * 上キーが押されていればtrueを返す。<p>
	 * 
	 * @return 上キーが押されていればtrue
	 */
	public boolean isUp() {
		return km.isUp();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		requestFocus();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {		
	}
}
