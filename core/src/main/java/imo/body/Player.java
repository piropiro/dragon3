package imo.body;


import imo.common.ImageList;
import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.paint.MineImage;


public class Player extends Body {

	static final int HP_MAX = 10;
	static final int SHOT_WAIT_MAX = 15;
	static final int ANIME_WAIT_MAX = 7;
	static final int F_UP = 1;
	static final int F_DOWN = 2;
	static final int F_LEFT = 3;
	static final int F_RIGHT = 4;
	static final int SWORD_WAIT_MAX = 5;
	static final int DAMAGE_WAIT_MAX = 3;
	static final int S_MOVE = 0;
	static final int S_SHOT = 1;
	static final int S_DAMAGE = 2;
	static final double V_WALK = 1.5;
	static final double V_DASH = 3;
	static final double V_DAMAGE = 5;

	private int hp;
	private String name;
	private int shotWait;
	private boolean animeFlag;
	private int animeWait;
	private int face;
	private int faces;
	private int status;
	private int statusWait;
	private Body screen;
	private double oldx, oldy;

	private MineImage[][] jikiImage;
	private MineImage hpbarImage;

	public Player(double x, double y, String name, Body screen, ImageList imageList) {
		super(x, y, 32, 32, 0, 1);
		this.jikiImage = imageList.getJiki();
		this.hpbarImage = imageList.getHpbar();
		this.name = name;
		oldx = x;
		oldy = y;
		hp = HP_MAX;
		shotWait = 0;
		animeFlag = true;
		animeWait = 0;
		face = 2;
		faces = 2;
		status = 0;
		statusWait = 0;
		this.screen = screen;
	}

	public boolean isAlive() {
		return hp > 0;
	}

	public boolean isMax() {
		return hp == HP_MAX;
	}

	public int getHp() {
		return hp;
	}
	public int getFace() {
		return faces;
	}

	public void move(boolean u, boolean d, boolean l, boolean r) {
		shotWait--;
		if (0 >= statusWait--)
			status = S_MOVE;
		anime();

		oldx = x;
		oldy = y;

		switch (status) {
			case S_MOVE :
			case S_SHOT :
				v = V_WALK;
				moveK(faces == 1, faces == 2, faces == 3, faces == 4);
				face = 0;
				if (u)
					face = F_UP;
				if (d)
					face = F_DOWN;
				if (l)
					face = F_LEFT;
				if (r)
					face = F_RIGHT;
				if (face != 0)
					faces = face;
				v = V_DASH;
				moveK(face == 1, face == 2, face == 3, face == 4);
				moveC(screen);
				break;
			case S_DAMAGE :
				v = V_DAMAGE;
				super.move();
				break;
			default :
				}
	}

	public void moveS() {
		x = oldx;
		y = oldy;
	}

	private void anime() {
		if (0 >= animeWait--) {
			animeFlag = !animeFlag;
			if (face != 0)
				animeWait = ANIME_WAIT_MAX;
			if (face == 0)
				animeWait = ANIME_WAIT_MAX * 2;
		}
	}

	public void shot(Sword sword) {
		if (shotWait > 0)
			return;
		if (status != S_MOVE)
			return;
		sword.make(this, SWORD_WAIT_MAX);
		shotWait = SHOT_WAIT_MAX;
		status = S_SHOT;
		statusWait = SWORD_WAIT_MAX;
	}

	public void damage(Body teki, int d) {
		if (status == S_DAMAGE)
			return;
		status = S_DAMAGE;
		statusWait = DAMAGE_WAIT_MAX;
		hp -= d;
		if (hp < 0)
			hp = 0;
		angle = getAngle(teki, this);
	}

	public void display(MineGraphics g) {
		int srcx = 0;
		int srcy = 0;
		switch (faces) {
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
				srcy += 0;
				break;
			case S_DAMAGE :
				srcy += 1;
				break;
			case S_SHOT :
				srcy += 2;
				break;
			default :
				}
		g.drawImage(jikiImage[srcy][srcx], (int)x, (int)y);
	}

	public void displayHP(MineGraphics g) {
		g.drawImage(hpbarImage, 73, 18);

		g.setColor(255, 0, 255);
		g.fillRect(76, 21, hp * 96 / HP_MAX, 8);

		g.setColor(MineColor.BLACK);
		g.drawString(name, 72, 16);
	}
}
