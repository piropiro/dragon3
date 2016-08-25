package imo;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import imo.common.ImageList;
import imo.paint.EndImoEvent;
import imo.paint.GameImoEvent;
import imo.paint.StartImoEvent;
import mine.event.KeyManager;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.paint.MineGraphics;
import mine.paint.MineImageLoader;
import mine.thread.Engine;

@Singleton
public class ImoCanvas implements PaintListener, ImoWorks {

	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;

	private PaintComponent panel;

	private ImoEventListener pl;
	private Engine engine;

	private ImoListener il;
	private GameImoEvent gp;
	private String name;
	private int level;
	private ImageList imageList;

	@Inject
	ImoCanvas(@Named("imoC") PaintComponent panel, KeyManager km, MineImageLoader mil) {
		this.panel = panel;
		engine = new Engine();
		imageList = new ImageList(mil);
		gp = new GameImoEvent(this, km, imageList);
		gameReset(name, level);

		panel.setPaintListener(this);
	}

	public void setup(String name, int level) {
		this.name = name;
		this.level = level;
	}

	public void setImoListener(ImoListener il) {
		this.il = il;
	}
	/**
	 * ペイントリスナーをセットする。
	 * @param pl PaintListener
	 */
	public void setEventListener(ImoEventListener pl) {
		this.pl = pl;
		engine.setRunner(pl);
	}
	public void setVisible(boolean flag) {
		panel.setVisible(flag);
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
	@Override
	public void paint(MineGraphics g){
		g.setColor(180, 240, 180);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		pl.paint(g);
	}

	/**
	 * Reset
	 */
	public void gameReset(String name_, int level_){
		this.name = name_;
		this.level = level_;
		setEventListener(new StartImoEvent(this));
		start();
	}

	/**
	 * Start
	 */
	public void gameStart() {
		stop();
		gp.resetData(name, level);
		setEventListener(gp);
		start();
	}

	/**
	 * End
	 */
	public void gameEnd(int n) {
		stop();
		imageList.setEndImage(n);
		setEventListener(new EndImoEvent(this, imageList));
		start();
	}

	/**
	 * Game Exit
	 */
	public void gameExit() {
		il.gameExit(gp.getEXP());
	}

	/**
	 * Repaint
	 */
	public void repaint() {
		panel.repaint();
	}

	public void keyPressed(char character, int keyCode) {
		pl.keyPressed(character, keyCode);
	}
	public void keyReleased(char character, int keyCode) {
		pl.keyReleased(character, keyCode);
	}

}
