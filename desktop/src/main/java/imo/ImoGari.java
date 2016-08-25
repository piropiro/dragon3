package imo;

import imo.common.ImageList;
import imo.paint.EndPaint;
import imo.paint.GamePaint;
import imo.paint.StartPaint;

import javax.swing.JComponent;

import mine.awt.ImageLoaderAWT;
import mine.awt.KeyManagerAWT;
import mine.event.KeyManager;
import mine.paint.MineImageLoader;

public class ImoGari implements MainWorks {

	private ImoListener il;
	private ImoCanvas mc;
	private GamePaint gp;
	private String name;
	private int level;
	private ImageList imageList;

	/**
	 * コンストラクタ
	 */
	public ImoGari(ImoListener il, String name, int level) {
		this.il = il;
		this.name = name;
		this.level = level;
		mc = new ImoCanvas();
		MineImageLoader mil = new ImageLoaderAWT();
		imageList = new ImageList(mil);
		KeyManager km = new KeyManagerAWT();
		gp = new GamePaint(this, km, imageList);
		gameReset(name, level);
	}

	/**
	 * Reset
	 */
	public void gameReset(String name_, int level_){
		this.name = name_;
		this.level = level_;
		mc.setPaintListener(new StartPaint(this));
		mc.start();
	}

	/**
	 * Start
	 */
	public void gameStart() {
		mc.stop();
		gp.resetData(name, level);
		mc.setPaintListener(gp);
		mc.start();
	}

	/**
	 * End
	 */
	public void gameEnd(int n) {
		mc.stop();
		imageList.setEndImage(n);
		mc.setPaintListener(new EndPaint(this, imageList));
		mc.start();
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
		mc.repaint();
	}
	
	public JComponent getCanvas(){
		return mc;
	}
}
