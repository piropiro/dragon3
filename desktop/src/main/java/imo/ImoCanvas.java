package imo;

import imo.paint.PaintListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

import mine.awt.GraphicsAWT;
import mine.awt.MineAwtUtils;
import mine.thread.Engine;

public class ImoCanvas extends JComponent implements KeyListener {

	private static final long serialVersionUID = 8726641543332205618L;

	private PaintListener pl;
	private Engine engine;

	/**
	 * コンストラクタ
	 * @param mw MainWorks
	 */
	ImoCanvas() {
		engine = new Engine();
		addKeyListener(this);
		MineAwtUtils.setSize(this, 300, 300);
	}

	/**
	 * ペイントリスナーをセットする。
	 * @param pl PaintListener
	 */
	public void setPaintListener(PaintListener pl) {
		this.pl = pl;
		engine.setRunner(pl);
	}

	/**
	 * スレッドを開始する。
	 */
	public void start() {
		engine.start();
	}

	/**
	 * スレッドを停止する。
	 */
	public void stop() {
		engine.stop();
	}

	/**
	 * 描画
	 */
	protected void paintComponent(Graphics g){
		Dimension d = getSize();
		g.setColor(new Color(180, 240, 180));
		g.fillRect(0, 0, d.width, d.height);
		pl.paint(new GraphicsAWT(g));
		requestFocus();
	}


	public void keyReleased(KeyEvent e) {
		pl.keyReleased(e.getKeyChar(), e.getKeyCode());
	}
	public void keyPressed(KeyEvent e) {
		pl.keyPressed(e.getKeyChar(), e.getKeyCode());
	}
	public void keyTyped(KeyEvent e){
	}

}
