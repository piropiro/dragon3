package imo.body;

import imo.common.ImageList;
import mine.paint.MineGraphics;
import mine.paint.MineImage;


public class Sword extends Body {

	private boolean life;
	private int animeWait;
	private int face;

	static final int F_UP = 1;
	static final int F_DOWN = 2;
	static final int F_LEFT = 3;
	static final int F_RIGHT = 4;
	
	private MineImage[][] swordImage;

	public Sword(ImageList imageList) {
		super(0, 0, 32, 32, 0, 0);
		this.swordImage = imageList.getSword();
		life = false;
		animeWait = 0;
		face = 2;
	}

	public boolean isAlive() {
		return life;
	}

	public void make(Player ore, int animeWait_) {
		this.face = ore.getFace();
		this.animeWait = animeWait_;
		life = true;
		move(ore);
	}

	public void move(Player ore) {
		this.face = ore.getFace();
		switch (face) {
			case F_UP :
				x = ore.x;
				y = ore.y - 32;
				break;
			case F_DOWN :
				x = ore.x;
				y = ore.y + 32;
				break;
			case F_LEFT :
				x = ore.x - 32;
				y = ore.y;
				break;
			case F_RIGHT :
				x = ore.x + 32;
				y = ore.y;
				break;
		}
	}

	public void display(MineGraphics g) {
		if (0 >= animeWait--)
			life = false;
		if (!life)
			return;

		int srcx = 0;
		switch (face) {
			case F_UP :
				srcx = 3;
				break;
			case F_DOWN :
				srcx = 0;
				break;
			case F_LEFT :
				srcx = 1;
				break;
			case F_RIGHT :
				srcx = 2;
				break;
		}

		g.drawImage(swordImage[0][srcx], (int)x, (int)y);
	}
}
