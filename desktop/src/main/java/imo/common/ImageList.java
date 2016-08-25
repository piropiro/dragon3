package imo.common;


import mine.MineException;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

public class ImageList {

	public static final String IMAGE_PATH = "imo/image/";

	private MineImage[][] imo;
	private MineImage[][] jiki;
	private MineImage[][] sister;
	private MineImage[][] sword;
	private MineImage turu;
	private MineImage hpbar;

	private MineImage endi;
	
	private MineImageLoader il;

	public ImageList(MineImageLoader il){
		this.il = il;
		imo = il.loadTile(IMAGE_PATH + "imo.png", 32, 32);
		jiki = il.loadTile(IMAGE_PATH + "jiki.png", 32, 32);
		sister = il.loadTile(IMAGE_PATH + "sister.png", 32, 32);
		sword = il.loadTile(IMAGE_PATH + "sword.png", 32, 32);
		turu = il.load(IMAGE_PATH + "turu.png");
		hpbar = il.load(IMAGE_PATH + "hpbar.png");
	}

	public void setEndImage(int n) {
		endi = il.load(IMAGE_PATH + "end" + n + ".png");
	}

	public MineImage getEndImage() {
		return endi;
	}
	/**
	 * @return Returns the hpbar.
	 */
	public MineImage getHpbar() {
		return hpbar;
	}
	/**
	 * @return Returns the imo.
	 */
	public MineImage[][] getImo() {
		return (MineImage[][])imo.clone();
	}
	/**
	 * @return Returns the jiki.
	 */
	public MineImage[][] getJiki() {
		return (MineImage[][])jiki.clone();
	}
	/**
	 * @return Returns the sister.
	 */
	public MineImage[][] getSister() {
		return (MineImage[][])sister.clone();
	}
	/**
	 * @return Returns the sword.
	 */
	public MineImage[][] getSword() {
		return (MineImage[][])sword.clone();
	}
	/**
	 * @return Returns the turu.
	 */
	public MineImage getTuru() {
		return turu;
	}
}
