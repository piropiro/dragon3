package dragon3.anime.listener;

import dragon3.anime.AnimeWorks;
import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

public class CloseAnime implements AnimeListener {


	public static final int OUT = 0;
	public static final int IN = 1;
	private static final int MAX = 16;

	private MineImage img;
	private int count;
	private int type;

	/*** Constructer ********************************/

	public CloseAnime(int type, MineImage img) {
		this.type = type;
		this.img = img;
	}

	/*** Display ******************************************/

	public void animation(AnimeWorks ac) {
		for (count = 0; count <= MAX; count++) {
			ac.repaint();
			if (type == OUT) {
				ac.sleep(20);
			} else {
				ac.sleep(20);
			}
		}

	}

	/*** Paint ******************************************/

	public void paint(MineGraphics g) {
		g.setColor(MineColor.WHITE);
		switch (type) {
			case OUT :
				g.drawImage(img, 0, 0);
				g.setAlpha(Math.min(1.0, 1.0 * count / MAX));
				g.fillRect(0, 0, 640, 480);
				break;
			case IN :
				g.setAlpha(Math.max(0, 1.0 - 1.0 * count / MAX));
				g.fillRect(0, 0, 640, 480);
				break;
		}

	}
}
