package imo.body;

import imo.common.ImageList;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

public class Turu extends Body {

	private boolean life;
	private int animeWait;
	private double time2, time3, time4;

	private MineImage turuImage;

	public Turu(ImageList imageList) {
		super(0, 0, 32, 32, 0, 0);
		this.turuImage = imageList.getTuru();
		life = false;
		animeWait = 0;
	}

	public boolean isAlive() {
		return life;
	}

	public void make(Imo imo, int animeWait_) {
		this.animeWait = animeWait_;
		life = true;

		time2 = animeWait * 20 / 200;
		time3 = animeWait * 10 / 200;
		time4 = animeWait * 30 / 200;

		move(imo);
	}

	public void move(Imo imo) {
		if (0 >= animeWait--)
			life = false;
		double t = animeWait;

		if (t < time4) {
			int ts = (int) (t * 32 / time4);
			x = imo.x - ts;
			y = imo.y - ts;
			xs = 32 + ts * 2;
			ys = 32 + ts * 2;
			return;
		}
		t -= time4;

		if (t < time3) {
			x = imo.x - 32;
			y = imo.y - 32;
			xs = 96;
			ys = 96;
			return;
		}
		t -= time3;

		if (t < time2) {
			int ts = (int) ((time2 - t) * 32 / time2);
			x = imo.x - ts;
			y = imo.y - ts;
			xs = 32 + ts * 2;
			ys = 32 + ts * 2;
			return;
		}

		x = imo.x;
		y = imo.y;
		xs = 32;
		ys = 32;
	}

	public void display(MineGraphics g) {
		if (!isAlive())
			return;

		g.drawImage(turuImage, 48 - xs/2, 48 - ys/2, xs, ys, (int)x, (int)y);
	}
}
