package imo.body;

import imo.common.ImageList;
import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.paint.MineImage;


public class Sister extends Body {

	static final int HP_MAX = 3;
	static final int ANIME_WAIT_MAX = 20;
	static final int DAMAGE_WAIT_MAX = 10;

	static final int S_NONE = 0;
	static final int S_DAMAGE = 1;

	private int hp;
	private boolean animeFlag;
	private int animeWait;
	private int status;
	private int statusWait;
	
	private MineImage[][] sisterImage;

	public Sister(double x, double y, ImageList imageList) {
		super(x, y, 32, 32, 0, 0);
		this.sisterImage = imageList.getSister();
		hp = HP_MAX;
		animeFlag = true;
		animeWait = 0;
		status = 0;
		statusWait = 0;
	}

	public boolean isAlive() {
		return hp > 0;
	}

	private void anime() {
		if (0 >= statusWait--)
			status = S_NONE;
		if (0 >= animeWait--) {
			animeFlag = !animeFlag;
			animeWait = ANIME_WAIT_MAX;
		}
	}

	public void damage() {
		if (status == S_DAMAGE)
			return;
		status = S_DAMAGE;
		statusWait = DAMAGE_WAIT_MAX;
		hp--;
	}

	public void move() {
		anime();
	}

	public void display(MineGraphics g) {
		int srcx = 0;
		if (animeFlag)
			srcx += 1;
	
		if (status == S_DAMAGE)
			srcx += 2;

		g.drawImage(sisterImage[0][srcx], (int)x, (int)y);
	}
	
	public void displayMessage(MineGraphics g, int love, Player player, Imo imo){
		String message = "";
		if (imo.isAlive()){
			if (hp < HP_MAX) {
				message = "うわーん";
			} else if (love > 30) {
				message = "お兄ちゃん、だめ…";
			} else if (imo.getHp() == 1 ){
				if (player.getHp() == Player.HP_MAX) {
					message = "やっちゃえ！";
				} else {
					message = "お兄ちゃん、とどめよ！";
				}
			} else if (player.getHp() < 5){
				message = "お兄ちゃん負けないで！";
			} else if (imo.getHp() < 5){
				message = "がんばれー";
			} else if (imo.getHp() < 10){
				message = "いけー";
			}
		} else {
			if (player.getHp() == Player.HP_MAX){
				message = "パーフェクト！";
			} else if (player.getHp() == 1){
				message = "ヤレヤレだぜ…";
			} else {
				message = "おかえりー";
			}
		}

		g.setColor(MineColor.BLACK);
		g.drawString(message, 5, 49);
	}
}
