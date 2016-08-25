package imo.paint;

import java.util.Random;

import imo.MainWorks;
import imo.body.Body;
import imo.body.Imo;
import imo.body.Jiki;
import imo.body.Sister;
import imo.body.Sword;
import imo.body.Turu;
import imo.common.ImageList;

import mine.event.KeyManager;
import mine.paint.MineGraphics;
import mine.thread.Engine;

public class GamePaint implements PaintListener {

	private Jiki ore;
	private Sister sister;
	private Sword sword;
	private Imo[] imo;
	private Turu[] turu;
	private Body screen;

	private int imoMax = 0;
	private int imoNum = 0;
	private int love;
	private boolean clearFlag;

	private MainWorks mw;
	private KeyManager km;
	private ImageList imageList;

	public GamePaint(MainWorks mw, KeyManager km, ImageList imageList) {
		this.mw = mw;
		this.km = km;
		this.imageList = imageList;
		screen = new Body(0, 0, 300, 300, 0, 0);
	}

	public void resetData(String name, int level) {
		love = 0;
		clearFlag = false;
		imoMax = level + 100;
		ore = new Jiki(120, 120, name, screen, imageList);
		sister = new Sister(0, 0, imageList);
		imo = new Imo[imoMax];
		turu = new Turu[imoMax];
		
		Random rnd = new Random();
		for (int i=0; i<imo.length; i++) {
			String imoname;
			if (i==0) {
				imoname = "いもすけ";
			} else {
				imoname = " " + (char)('ぁ' + rnd.nextInt(83)) + (char)('ぁ' + rnd.nextInt(83)) + "すけ";
			}
			imo[i] = new Imo(280, 280, imoname + "　Lv." + level, 2, screen, imageList);
			turu[i] = new Turu(imageList);
		}
		sword = new Sword(imageList);

		km.reset();
	}

	public void paint(MineGraphics g) {
		sword.display(g);
		ore.display(g);
		sister.display(g);
		for (int i=0; i<imo.length; i++) {
			imo[i].display(g);
			turu[i].display(g);
		}
		sister.displayMessage(g, love, ore, imo[imoNum]);
		ore.displayHP(g);
		imo[imoNum].displayHP(g);
	}

	private void hit() {
		if (Body.hit(ore, sister)) {
			ore.moveS();
			love++;
		}
		for (int i=0; i<imo.length; i++) {
			if (Body.hit(ore, imo[i])) {
				if (imo[i].isAlive()) {
					ore.damage(imo[i], 1);
					imoNum = i;
				} else {
					ore.moveS();
				}
			}
			if (turu[i].isAlive() && Body.hit(ore, turu[i])) {
				ore.damage(imo[i], 3);
				imoNum = i;
			}
			if (sword.isAlive() && Body.hit(sword, imo[i])) {
				imo[i].damage(ore, 1);
				imoNum = i;
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
		for (int i=0; i<imo.length; i++) {
			if (Body.hit(imo[i], sister)) {
				mw.gameEnd(3);
				return;
			}
			if (turu[i].isAlive() && Body.hit(turu[i], sister)) {
				mw.gameEnd(3);
				return;
			}
		}
		if (love >= 200) {
			mw.gameEnd(2);
			return;
		}
		boolean imoAlive = false;
		for (int i=0; i<imo.length; i++) {
			if (imo[i].isAlive()) {
				imoAlive = true;
			}
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
		for (int i=0; i<imo.length; i++) {
			imo[i].move(ore);
		}
		sister.move();
		if (km.isEnter() || km.isKey('z')){
			ore.shot(sword);
			km.setKey('z', false);
		}
		for (int i=0; i<imo.length; i++) {
			imo[i].shot(turu[i]);
			turu[i].move(imo[i]);
		}
		sword.move(ore);
		hit();
	}
}
