package dragon3.anime.listener;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import dragon3.anime.AnimeWorks;

public class PictureAnime implements AnimeListener {

	private MineImage img;

	/*** Constructer ********************************/

	public PictureAnime(MineImage img) {
		this.img = img;
	}

	/*** Display ******************************************/

	public void animation(AnimeWorks ac) {
		ac.repaint();
	}

	/*** Paint ******************************************/

	public void paint(MineGraphics g) {
		g.drawImage(img, 0, 0);
	}
}
