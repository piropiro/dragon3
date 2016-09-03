package imo.paint;

import imo.ImoEventListener;
import imo.ImoWorks;
import imo.body.Body;
import imo.body.Imo;
import imo.body.Player;
import imo.body.Sister;
import imo.body.Sword;
import imo.body.Turu;
import imo.common.ImageList;
import mine.event.KeyManager;
import mine.paint.MineGraphics;
import mine.thread.Engine;

public class GameImoEvent implements ImoEventListener {

	private Player ore;
	private Sister sister;
	private Sword sword;
	private Imo imo;
	private Turu turu;
	private Body screen;

	private int love;
	private boolean clearFlag;

	private ImoWorks mw;
	private KeyManager km;
	private ImageList imageList;

	public GameImoEvent(ImoWorks mw, KeyManager km, ImageList imageList) {
		this.mw = mw;
		this.km = km;
		this.imageList = imageList;
		screen = new Body(0, 0, 300, 300, 0, 0);
	}

	public void resetData(String name, int level) {
		love = 0;
		clearFlag = false;
		ore = new Player(120, 120, name, screen, imageList);
		sister = new Sister(0, 0, imageList);

		imo = new Imo(280, 280, "いもすけ　Lv." + level, 2, screen, imageList);
		turu = new Turu(imageList);

		sword = new Sword(imageList);

		km.reset();
	}

	public void paint(MineGraphics g) {
		sword.display(g);
		ore.display(g);
		sister.display(g);
		imo.display(g);
		turu.display(g);
		sister.displayMessage(g, love, ore, imo);
		ore.displayHP(g);
		imo.displayHP(g);
	}

	private void hit() {
		if (Body.hit(ore, sister)) {
			ore.moveS();
			love++;
		}
		if (Body.hit(ore, imo)) {
			if (imo.isAlive()) {
				ore.damage(imo, 1);
			} else {
				ore.moveS();
			}
			if (turu.isAlive() && Body.hit(ore, turu)) {
				ore.damage(imo, 3);
			}
			if (sword.isAlive() && Body.hit(sword, imo)) {
				imo.damage(ore, 1);
			}
		}
		if (sword.isAlive() && Body.hit(sword, sister)) {
			sister.damage();
		}
		sword.move(ore);
	}

	private void endjudge() {
		if (!ore.isAlive()) {
			mw.gameEnd(1);
			return;
		}
		if (!sister.isAlive()) {
			mw.gameEnd(4);
			return;
		}
		if (Body.hit(imo, sister)) {
			mw.gameEnd(3);
			return;
		}
		if (turu.isAlive() && Body.hit(turu, sister)) {
			mw.gameEnd(3);
			return;
		}

		if (love >= 200) {
			mw.gameEnd(2);
			return;
		}
		boolean imoAlive = false;
		if (imo.isAlive()) {
			imoAlive = true;
		}

		if (!imoAlive) {
			clearFlag = true;
			if (love >= 10) {
				if (ore.isMax()) {
					mw.gameEnd(5);
					return;
				} else {
					mw.gameEnd(0);
					return;
				}
			}
		}
	}

	public int getEXP() {
		if (clearFlag)
			return ore.getHp();
		return 0;
	}

	public void keyReleased(char character, int keyCode) {
		km.keyReleased(character, keyCode);
	}
	public void keyPressed(char character, int keyCode) {
		km.keyPressed(character, keyCode);
	}

	public void run() {
		Engine.Companion.sleep(16);
		mw.repaint();
		if (km.isEscape() || km.isKey('x'))
			mw.gameStart();
		endjudge();
		ore.move(km.isUp(), km.isDown(), km.isLeft(), km.isRight());
		imo.move(ore);
		sister.move();
		if (km.isEnter() || km.isKey('z')){
			ore.shot(sword);
			km.setKey('z', false);
		}
		imo.shot(turu);
		turu.move(imo);
		sword.move(ore);
		hit();
	}
}
