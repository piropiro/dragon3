package dragon3.anime.listener;

import dragon3.anime.AnimeManager;
import dragon3.anime.AnimeWorks;
import dragon3.anime.item.Number;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

public class DropTextAnime implements AnimeListener {

	private Number[] nums;

	private int type;

	private MineImage[] text;


	/*** Constructer ***********************/

	public DropTextAnime(int type, MineImage[] text) {
		this.type = type;
		this.text = text;
		setText(type);
	}

	/*** Text *************************/

	private void setText(int type) {
		switch (type) {
			case AnimeManager.TEXT_MISS :
				nums = new Number[4];
				nums[3] = new Number(9, 4, 3);
				nums[2] = new Number(3, 13, 2);
				nums[1] = new Number(6, 16, 1);
				nums[0] = new Number(6, 22, 0);
				break;
			case AnimeManager.TEXT_POISON :
				nums = new Number[6];
				nums[5] = new Number(6, 1, 5);
				nums[4] = new Number(5, 7, 4);
				nums[3] = new Number(3, 12, 3);
				nums[2] = new Number(5, 15, 2);
				nums[1] = new Number(5, 20, 1);
				nums[0] = new Number(6, 25, 0);
				break;
			case AnimeManager.TEXT_SLEEP :
				nums = new Number[5];
				nums[4] = new Number(6, 2, 4);
				nums[3] = new Number(5, 8, 3);
				nums[2] = new Number(6, 13, 2);
				nums[1] = new Number(6, 19, 1);
				nums[0] = new Number(6, 25, 0);
				break;
			case AnimeManager.TEXT_FINISH :
				nums = new Number[6];
				nums[5] = new Number(6, 1, 5);
				nums[4] = new Number(3, 7, 4);
				nums[3] = new Number(6, 10, 3);
				nums[2] = new Number(3, 16, 2);
				nums[1] = new Number(5, 19, 1);
				nums[0] = new Number(6, 24, 0);
				break;
			case AnimeManager.TEXT_WIN :
				nums = new Number[3];
				nums[2] = new Number(12, 2, 2);
				nums[1] = new Number(4, 15, 1);
				nums[0] = new Number(9, 20, 0);
				break;
			case AnimeManager.TEXT_DEATH :
				nums = new Number[5];
				nums[4] = new Number(7, 1, 4);
				nums[3] = new Number(6, 8, 3);
				nums[2] = new Number(6, 14, 2);
				nums[1] = new Number(5, 20, 1);
				nums[0] = new Number(6, 25, 0);
				break;
			case AnimeManager.TEXT_CLOSE :
				nums = new Number[5];
				nums[4] = new Number(6, 3, 4);
				nums[3] = new Number(5, 9, 3);
				nums[2] = new Number(5, 14, 2);
				nums[1] = new Number(5, 19, 1);
				nums[0] = new Number(5, 24, 0);
				break;
		}
	}

	/*** End ******************************/

	private boolean end() {
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != null && !nums[i].end())
				return false;
		}
		return true;
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {
		while (!end()) {
			ac.repaint();
			ac.sleep(25);
		}
		ac.sleep(100);
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		for (int i = 0; i < nums.length; i++) {
			nums[i].move();
			int sx = nums[i].getX();
			int sy = 0;
			int w = nums[i].getN();
			int h = 12;
			int dx = nums[i].getX();
			int dy = 20 - nums[i].getY();
			g.drawImage(text[type], sx, sy, w, h, dx, dy);
		}
	}
}
