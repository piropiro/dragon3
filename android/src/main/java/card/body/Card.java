package card.body;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.util.Bounder;
import card.common.ImageList;

public class Card {

	private int n;
	private int x, y;
	private int color;
	private int count;
	private int status;

	private MineImage open_img;
	private MineImage close_img;

	private static final Bounder BOUNDER;

	public static final int NONE = 0;
	public static final int CLOSE = 1;
	public static final int OPENING = 2;
	public static final int OPEN = 3;
	public static final int SLASHING = 4;
	public static final int WINNING = 5;
	public static final int WIN = 6;

	public static final int BLUE = 0;
	public static final int RED = 1;
	
	public static final int OPENING_MAX = 6;
	public static final int SLASHING_MAX = 20;
	public static final int WINNING_MAX = 20;

	static {
		BOUNDER = new Bounder(20, 3, 0.7, 20);
	}
	
	private MineImage[] blueImage;
	private MineImage[] redImage;
	private MineImage[] numImage;
	private MineImage[] winImage;

	public Card(int n, int x, int y, int color, ImageList il) {
		this.n = n;
		this.x = x;
		this.y = y;
		this.color = color;
		blueImage = il.getBlue();
		redImage = il.getRed();
		numImage = il.getNumber();
		winImage = il.getWin();
		status = NONE;
		open_img = getOpenImage();
		close_img = getCloseImage();
	}

	/*** Status *******************************/

	public void close() {
		status = CLOSE;
	}
	public void opening(int count_){
		this.count = count_;
		status = OPENING;
	}
	public void open() {
		status = OPEN;
	}
	public void dispose() {
		status = NONE;
	}
	public void slashing(int count_){
		this.count = count_;
		status = SLASHING;
	}
	public void winning(int count_){
		this.count = count_;
		status = WINNING;
	}
	public void win(){
		status = WIN;
	}
	public int getStatus() {
		return status;
	}

	public int getNumber(){
		return n;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setNumber(int n){
		this.n = n;
		open_img = getOpenImage();
	}

	private MineImage getOpenImage() {
		MineImage img = null;
		switch (color) {
			case BLUE :
				img = blueImage[ImageList.OPEN].getCopy();
				break;
			case RED :
				img = redImage[ImageList.OPEN].getCopy();
				break;
		}
		MineGraphics g = img.getGraphics();

		if (n < 0) {
			// do nothing
		} else if (n < 10) {
			g.drawImage(numImage[n], 11, 10);
		} else if (n < 100) {
			g.drawImage(numImage[n / 10], 6, 10);
			g.drawImage(numImage[n % 10], 15, 10);
		} else {
			g.drawImage(numImage[0], 6, 10);
			g.drawImage(numImage[0], 15, 10);
		}

		return img;
	}

	private MineImage getCloseImage() {
		if (color == BLUE) {
			return blueImage[ImageList.CLOSE];
		} else {
			return redImage[ImageList.CLOSE];
		}
	}
	/*** Paint *************************************/

	public void paint(MineGraphics g) {
		switch (status) {
			case NONE:
				break;
			case CLOSE:
			case WIN:
				g.drawImage(close_img, x, y);
				break;
			case OPENING:
				openingPaint(g);
				break;
			case OPEN :
				g.drawImage(open_img, x, y);
				break;
			case SLASHING:
				slashingPaint(g);
				break;
			case WINNING:
				winningPaint(g);
		}
	}

	private void openingPaint(MineGraphics g){
		count = Math.min(count, OPENING_MAX);
		if (color == BLUE) {
			g.drawImage(blueImage[count], x, y);
		} else {
			g.drawImage(redImage[count], x, y);
		}
	}
	private void slashingPaint(MineGraphics g){
		count = Math.min(count, SLASHING_MAX);
		g.setAlpha(1.0 * (SLASHING_MAX - count) / SLASHING_MAX);
		if (color == BLUE) {
			halfPaint(g, x + count * 3, y, true);
			halfPaint(g, x + count * 1, y, false);
		} else {
			halfPaint(g, x - count * 3, y, true);
			halfPaint(g, x - count * 1, y, false);
		}
		g.setAlpha(1.0);
	}

	private void winningPaint(MineGraphics g){
		count = Math.min(count, WINNING_MAX);
		g.drawImage(open_img, x, y);
		g.drawImage(winImage[0], x + 2, y + 20 - BOUNDER.getY(count));
		g.drawImage(winImage[1], x + 15, y + 20 - BOUNDER.getY(count+1));
		g.drawImage(winImage[2], x + 20, y + 20 - BOUNDER.getY(count+2));
	}

	private void halfPaint(MineGraphics g, int x_, int y_, boolean flag) {
		if (flag) {
			g.drawImage(open_img.getSubimage(0, 0, 32, 16), x_, y_);
		} else {
			g.drawImage(open_img.getSubimage(0, 16, 32, 16), x_, y_ + 16);
		}
	}
	
	public String toString(){
		if (color == BLUE) {
			return new String("B" + n);
		} else {
			return new String("R" + n);
		}
	}
}
