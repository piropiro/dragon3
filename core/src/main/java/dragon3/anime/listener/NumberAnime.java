package dragon3.anime.listener;

import dragon3.anime.AnimeWorks;
import dragon3.anime.item.Number;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

public class NumberAnime implements AnimeListener {

	private Number[] nums;
	
	private MineImage[] num;

	private int damage;

	/*** Constructer ***********************/

	public NumberAnime(int damage, MineImage[] num) {
		this.damage = Math.min(Math.abs(damage), 999);
		this.num = num;
		nums = new Number[3];
	}

	/*** End Judge ******************************/

	private boolean end() {
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != null && !nums[i].end())
				return false;
		}
		return true;
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {
		if (damage > 99) {
			nums[2] = new Number(damage / 100, 1, 4);
			nums[1] = new Number(damage % 100 / 10, 11, 2);
			nums[0] = new Number(damage % 10, 21, 0);
		} else if (damage > 9) {
			nums[2] = null;
			nums[1] = new Number(damage % 100 / 10, 6, 2);
			nums[0] = new Number(damage % 10, 16, 0);
		} else {
			nums[2] = null;
			nums[1] = null;
			nums[0] = new Number(damage % 10, 12, 0);
		}
		while (!end()) {
			ac.repaint();
			ac.sleep(25);
		}
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != null) {
				nums[i].move();
				nums[i].display(g, num);
			}
		}
	}
}
