package card.common;

import mine.MineException;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

public class ImageList {

	public static final int OPEN = 0;
	public static final int CLOSE = 6;

	private MineImage[] back;
	private MineImage[] blue;
	private MineImage[] red;
	private MineImage[] number;
	private MineImage[] waku;
	private MineImage[] win;
	
	private MineImageLoader imageLoader;

	public static final String IMAGE_PATH = "card/image/";

	public ImageList(MineImageLoader mil) {
		this.imageLoader = mil;
		try {
			MineImage[][] cards = mil.loadTile(IMAGE_PATH + "card.png", 32, 32);
			back = cards[0];
			blue = cards[1];
			red = cards[2];
			number = mil.loadTile(IMAGE_PATH + "num.png", 10, 12)[0];
			waku = mil.loadTile(IMAGE_PATH + "waku.png", 32, 32)[0];

			MineImage wins = mil.load(IMAGE_PATH + "win.png");

			win = new MineImage[3];
			win[0] = wins.getSubimage(2, 0, 13, 12);
			win[1] = wins.getSubimage(15, 0, 5, 12);
			win[2] = wins.getSubimage(20, 0, 9, 12);
		} catch (MineException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * @return Returns the back.
	 */
	public MineImage[] getBack() {
		return back;
	}
	/**
	 * @return Returns the blue.
	 */
	public MineImage[] getBlue() {
		return blue;
	}
	/**
	 * @return Returns the number.
	 */
	public MineImage[] getNumber() {
		return number;
	}
	/**
	 * @return Returns the red.
	 */
	public MineImage[] getRed() {
		return red;
	}
	/**
	 * @return Returns the waku.
	 */
	public MineImage[] getWaku() {
		return waku;
	}
	/**
	 * @return Returns the win.
	 */
	public MineImage[] getWin() {
		return win;
	}
	/**
	 * @return Returns the imageLoader.
	 */
	public MineImageLoader getImageLoader() {
		return imageLoader;
	}
}
