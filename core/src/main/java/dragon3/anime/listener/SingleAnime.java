package dragon3.anime.listener;

import dragon3.anime.AnimeWorks;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

public class SingleAnime implements AnimeListener {

	private MineImage[] image;

	private int n;
	private int sleep;

	/*** Constructer ***********************/

	public SingleAnime(MineImage[] image, int sleep) {
		this.image = image;
		this.sleep = sleep;
	}

	/*** Display **********************************/

	public void animation(AnimeWorks ac) {
		for (n = 0; n < image.length; n++) {
			ac.repaint();
			ac.sleep(sleep);
		}
	}

	/*** Paint *********************************/

	public void paint(MineGraphics g) {
		g.drawImage(image[n], 0, 0);
	}
}
