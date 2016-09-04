package imo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.inject.Inject;
import javax.swing.JDialog;

import mine.awt.MineCanvasAWT;
import mine.awt.MineAwtUtils;
import mine.event.MineCanvas;
import mine.event.SleepManager;
import mine.paint.MineImageLoader;

public class ImoDialog extends JDialog implements KeyListener, ImoListener {

	@Inject MineCanvas mc;
	@Inject SleepManager sleepManager;
	@Inject MineImageLoader imageLoader;
	@Inject ImoCanvas ic;

	private boolean life;
	private int exp;
	private int level = 1;

	private MineCanvasAWT c;

	@Inject
	public ImoDialog() {
		super();
	}

	public void launch() {

		c = new MineCanvasAWT(mc);
		MineAwtUtils.setSize(c, 300, 300);

		ic.setup("僕", 1);

		ic.setVisible(true);
		setTitle("ImoBattle");

		c.addKeyListener(this);

		ic.setImoListener(this);

		getContentPane().add(c);
		pack();
		MineAwtUtils.setCenter(this);
		setVisible(true);
		c.requestFocus();
	}



	public void gameExit(int exp_) {
		this.life = true;
		this.exp = exp_;
		if ( exp > 0) level++;
		ic.gameReset("俺", level);
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLife() {
		return life;
	}

	public void setLife(boolean life) {
		this.life = life;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		ic.keyReleased(e.getKeyChar(), e.getKeyCode());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		ic.keyPressed(e.getKeyChar(), e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e){
	}

}
