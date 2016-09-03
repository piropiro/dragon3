package imo.body;

import imo.common.ImageList;
import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

public class Imo extends Body {

	static final int SHOT_WAIT_MAX = 150;
	static final int ANIME_WAIT_MAX = 7;

	static final int F_UP = 1;
	static final int F_DOWN = 2;
	static final int F_LEFT = 3;
	static final int F_RIGHT = 4;

	static final int SWORD_WAIT_MAX = 60;
	static final int DAMAGE_WAIT_MAX = 5;

	static final int S_MOVE = 0;
	static final int S_SHOT = 1;
	static final int S_DAMAGE = 2;
	static final int S_DEAD = 3;

	static final int V_WALK = 1;
	static final int V_DAMAGE = 6;

	private String name;
	private int hp;
	private int hpMax;
	private int shotWait;
	private int turnWait;
	private int animeWait;
	private boolean animeFlag;
	private int face;
	private int status;
	private int statusWait;
	private Body screen;
	private double oldx, oldy;

	private MineImage[][] imoImage;
	private MineImage hpbarImage;

	public Imo(double x, double y, String name, int hpMax, Body screen,
			ImageList imageList) {
		super(x, y, 32, 32, 0, 0.5);
		this.name = name;
		this.imoImage = imageList.getImo();
		this.hpbarImage = imageList.getHpbar();
		this.hpMax = hpMax;
		this.screen = screen;
		oldx = x;
		oldy = y;
		hp = hpMax;
		shotWait = 300;
		turnWait = 0;
		animeWait = 0;
		animeFlag = false;
		face = 0;
		status = 0;
		statusWait = 0;
	}

	public void shot(Turu turu) {
		if (shotWait > 0)
			return;
		if (status != S_MOVE)
			return;
		turu.make(this, SWORD_WAIT_MAX);
		shotWait = SHOT_WAIT_MAX + rnd(100);
		status = S_SHOT;
		statusWait = SWORD_WAIT_MAX;
	}

	public void move(Player ore) {
		shotWait--;
		if (isAlive() && 0 >= statusWait--)
			status = S_MOVE;
		anime();
		if (status != S_SHOT)
			turn();

		oldx = x;
		oldy = y;

		switch (status) {
			case S_MOVE :
				v = V_WALK;
				moveK(face == 1, face == 2, face == 3, face == 4);
				moveC(screen);
				break;
			case S_SHOT :
				break;
			case S_DAMAGE :
				if (face == F_UP && ore.getFace() == F_DOWN)
					break;
				if (face == F_DOWN && ore.getFace() == F_UP)
					break;
				if (face == F_LEFT && ore.getFace() == F_RIGHT)
					break;
				if (face == F_RIGHT && ore.getFace() == F_LEFT)
					break;
				v = V_DAMAGE;
				super.move();
				break;
			default :
				}
	}

	public void turn() {
		if (0 <= turnWait--)
			return;
		switch (rnd(10)) {
			case 0 :
			case 1 :
			case 2 :
				face = 1;
				break;
			case 3 :
			case 4 :
				face = 2;
				break;
			case 5 :
			case 6 :
			case 7 :
				face = 3;
				break;
			case 8 :
			case 9 :
				face = 4;
				break;
			default :
				}
		turnWait = 10 + rnd(10);
	}

	public void moveS() {
		x = oldx;
		y = oldy;
	}

	private void anime() {
		if (0 >= animeWait--) {
			animeFlag = !animeFlag;
			animeWait = ANIME_WAIT_MAX;
		}
	}

	public void damage(Body teki, int d) {
		if (status == S_DAMAGE)
			return;
		if (status == S_SHOT)
			return;
		status = S_DAMAGE;
		statusWait = DAMAGE_WAIT_MAX;
		hp -= d;
		if (hp <= 0) {
			hp = 0;
			status = S_DEAD;
		}
		angle = getAngle(teki, this);
	}
	public boolean isAlive() {
		return hp > 0;
	}
	
	public int getHp(){
		return hp;
	}
	public void display(MineGraphics g) {

		int srcx = 0;
		int srcy = 0;
	
		switch (face) {
			case F_UP :
				srcx = 6;
				break;
			case F_DOWN :
				srcx = 0;
				break;
			case F_LEFT :
				srcx = 2;
				break;
			case F_RIGHT :
				srcx = 4;
				break;
			default :
				}
		if (animeFlag){
			srcx += 1;
		}

		switch (status) {
			case S_MOVE :
				srcy = 0;
				break;
			case S_DAMAGE :
				srcy = 1;
				break;
			case S_SHOT :
				srcy = 2;
				break;
			default :
				}

		if (!isAlive()){
			srcx = 8;
			srcy = 0;
		}

		g.drawImage(imoImage[srcy][srcx], (int)x, (int)y);
		g.drawImage(imoImage[1][srcx], 0, 0, 32, 32 * (hpMax - hp) / hpMax, (int)x, (int)y);
	}
	public void displayHP(MineGraphics g) {
		g.drawImage(hpbarImage, 191, 18);

		g.setColor(MineColor.LIME);
		g.fillRect(194, 21, hp * 96 / hpMax, 8);
		
		g.setColor(MineColor.BLACK);
		g.drawString(name, 190, 16);
	}

}
