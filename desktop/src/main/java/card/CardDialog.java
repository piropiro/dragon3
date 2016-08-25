package card;

import mine.MineUtils;
import mine.awt.MineAwtUtils;
import mine.awt.MineCanvasAWT;
import mine.event.MineCanvas;
import mine.event.SleepManager;
import mine.io.JsonIO;
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

	private static final long serialVersionUID = -7568381413859212716L;

	private MineImage[] chara;
	private int level = 1;

	private int[][] status;
	
	@Inject MineCanvas mc;
	@Inject SleepManager sleepManager;
	@Inject MineImageLoader imageLoader;
	@Inject CardCanvas cc;

	@Inject
	public CardDialog() {
		super();
	}
	
	public void launch() {
		MineCanvasAWT mca = (MineCanvasAWT) mc;
		
		MineAwtUtils.setSize(mca, 640, 480);	
		
		cc.setVisible(true);
		setTitle("CardBattle");
		
		//SleepManager sleepManager = new SleepManagerAWT();
		mca.addKeyListener((KeyListener)sleepManager);
		mca.addMouseListener((MouseListener)sleepManager);
		
		//cc = new CardCanvas(cardPanel, mil, sleepManager);		
		cc.setCardListener(this);
		mc.setMouseAllListener(new CardEventListener(cc));
		
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

		status = JsonIO.INSTANCE.read("card/data/status.json", int[][].class);


		getContentPane().add((JComponent)mc);
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
