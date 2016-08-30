package card;

import mine.awt.MineAwtUtils;
import mine.awt.MineCanvasAWT;
import mine.awt.MouseManagerAWT;
import mine.event.MineCanvas;
import mine.event.MouseManager;
import mine.event.SleepManager;
import mine.io.JsonManager;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CardDialog extends JDialog implements CardListener {

	private MineImage[] chara;
	private int level = 1;

	private int[][] status;

	private MouseManager mm;
	private MineCanvasAWT c;

	@Inject MineCanvas mc;
	@Inject SleepManager sleepManager;
	@Inject MineImageLoader imageLoader;
	@Inject JsonManager jsonManager;
	@Inject CardCanvas cc;

	@Inject
	public CardDialog() {
		super();
	}
	
	public void launch() {
		c = new MineCanvasAWT(mc);
		mm = new MouseManagerAWT(this);

		MineAwtUtils.setSize(c, 640, 480);
		
		cc.setVisible(true);
		setTitle("CardBattle");
		
		//SleepManager sleepManager = new SleepManagerAWT();
		c.addKeyListener((KeyListener)sleepManager);
		c.addMouseListener((MouseListener)sleepManager);
		
		//cc = new CardCanvas(cardPanel, mil, sleepManager);		
		cc.setCardListener(this);
		mm.setMouseAllListener(new CardEventListener(cc));
		
		//chara = (MineImage[])MineUtils.linerize(
		//	imageLoader.loadTile("card/image/chara.png", 32, 32), new MineImage[0]);

		MineImage[][] charaArray = imageLoader.loadTile("card/image/chara.png", 32, 32);
		List<MineImage> charaList = new ArrayList<>();
		for (MineImage[] array : charaArray) {
			for (MineImage image : array) {
				charaList.add(image);
			}
		}
		chara = charaList.toArray(new MineImage[0]);

		status = jsonManager.read("card/data/status.json", int[][].class);


		getContentPane().add(c);
		pack();
		MineAwtUtils.setCenter(this);
		setVisible(true);
		cc.setBlueChara(chara[status[0][0]], subarray(status[0], 1, 7));
		setRedChara();
		cc.start();
	}


	public void gameExit(int redWin, int blueWin) {
		cc.dispose();
		level = Math.max(1, Math.min(level - redWin + blueWin, status.length - 1));
		setRedChara();
		cc.start();
	}
	
	private void setRedChara(){
		cc.setRedChara(chara[status[level][0]], subarray(status[level], 1, 7));
	}
	
	private int[] subarray(int[] src, int pos, int len){
		int[] dst = new int[len];
		System.arraycopy(src, pos, dst, 0, len);
		return dst;
	}
}
