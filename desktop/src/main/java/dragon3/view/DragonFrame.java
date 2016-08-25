package dragon3.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.AbstractButton;
import javax.swing.JFrame;

import dragon3.controller.CommandListener;
import lombok.Setter;
import mine.awt.BMenuBar;
import mine.awt.MineAwtUtils;
import mine.awt.MineCanvasAWT;
import mine.event.MineCanvas;
import mine.event.MouseAllListener;
import mine.event.SleepManager;

@Singleton
public class DragonFrame implements FrameWorks, ActionListener, KeyListener {
	
	private volatile BMenuBar mb;
	private JFrame frame;
	private MineCanvas mc;
	private MouseAllListener mal;
	
	private CommandListener commandListener;
	
	/*** Constructer *****************************************************/

	@Inject
	public DragonFrame(@Named("mainC") MineCanvas mc, SleepManager sleepManager) {
		this.frame = new JFrame("RyuTaiji 3");
		frame.setResizable(false);
		frame.setBackground(new Color(0, 0, 150));
		
		// Menu
		mb = new BMenuBar();
		mb.add("NONE", "none", KeyEvent.VK_N);
		frame.setJMenuBar(mb);
		
		this.mc = mc;
		MineCanvasAWT mca = (MineCanvasAWT) mc;

		//mapPanel.setVisible(true);
		
		MineAwtUtils.setSize(mca, 640, 480);	
		mca.setBackground(new Color(0, 0, 150));
		mca.addKeyListener(this);
		
		frame.getContentPane().add(mca);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mca.addKeyListener((KeyListener)sleepManager);
		mca.addMouseListener((MouseListener)sleepManager);
	}
	
	public void launch() {
		frame.setVisible(true);
		MineAwtUtils.setCenter(frame);

	}

	/*** MenuBar ****************************************/

	public synchronized void setMenu(int type) {
		mb.reset(this);
		switch (type) {
			case T_SCORE :
				mb.add("BACK", "back", KeyEvent.VK_B);
				mb.add("HELP", "help", KeyEvent.VK_H);
				break;
			case T_TITLE :
				mb.add("NONE", "none", KeyEvent.VK_N);
				break;
			case T_CAMP :
				mb.add("STAGE", "stage", KeyEvent.VK_A);
				mb.add("SAVE", "save", KeyEvent.VK_S);
				mb.add("LOAD", "campload", KeyEvent.VK_Q);
				mb.addMenu("OPTION    x", 'x');
				mb.addItem("SORT", "sort", KeyEvent.VK_T);
				mb.addItem("WAZA", "waza", KeyEvent.VK_W);
				mb.addItem("WAZA_LIST", "wazalist", KeyEvent.VK_E);
				mb.addItem("REMOVE", "remove", KeyEvent.VK_R);
				mb.addItem("COLLECTION", "collect", KeyEvent.VK_F);
				mb.addItem("IMO_GARI", "imogari", KeyEvent.VK_V);
				mb.addItem("HELP", "help", KeyEvent.VK_H);
				mb.addItem("SCORE", "score", KeyEvent.VK_G);
				break;
			case T_STAGESELECT:
				mb.add("BACK", "camp", KeyEvent.VK_B);
				mb.add("HELP", "help", KeyEvent.VK_H);
				break;
			case T_SETMENS :
				mb.add("BACK", "stage", KeyEvent.VK_B);
				mb.add("START", "start", KeyEvent.VK_S);
				mb.add("HELP", "help", KeyEvent.VK_H);
				break;
			case T_COLLECT :
				mb.add("BACK", "back", KeyEvent.VK_B);
				mb.add("HELP", "help", KeyEvent.VK_H);
				break;
			case T_PLAYER :
				mb.add("TURN END", "turnend", KeyEvent.VK_T);
				mb.add("ESCAPE", "escape", KeyEvent.VK_E);
				mb.add("LOAD", "mapload", KeyEvent.VK_Q);
				mb.add("HELP", "help", KeyEvent.VK_H);
				break;

			case T_ENEMY :
				mb.add("NONE", "none", KeyEvent.VK_N);
				break;
			case T_CLEAR :
				mb.add("CAMP", "camp", KeyEvent.VK_A);
				mb.add("HELP", "help", KeyEvent.VK_H);
				break;
			case T_GAMEOVER :
				mb.add("LOAD", "mapload", KeyEvent.VK_Q);
				mb.add("HELP", "help", KeyEvent.VK_H);
				break;
		}
		frame.setJMenuBar(mb);
		mb.repaint();
		mc.requestFocus();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.requestFocus();
		if (mc.isRunning())
			return;
		AbstractButton b = (AbstractButton) e.getSource();
		String command = b.getActionCommand();
		
		new Thread(() -> commandListener.executeMenuCommand(command)).start();
	}
	
	public void keyTyped(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyChar());
		int n = 0;
		switch (e.getKeyCode()) {
			case KeyEvent.VK_F1 :
				n = 1;
				break;
			case KeyEvent.VK_F2 :
				n = 2;
				break;
			case KeyEvent.VK_F3 :
				n = 3;
				break;
			case KeyEvent.VK_F4 :
				n = 4;
				break;
			case KeyEvent.VK_F5 :
				n = 5;
				break;
			case KeyEvent.VK_F6 :
				n = 6;
				break;
			case KeyEvent.VK_F7 :
				n = 7;
				break;
			case KeyEvent.VK_F8 :
				n = 8;
				break;
			case KeyEvent.VK_X:
				new Thread(() -> mal.accept()).start();
				return;
			case KeyEvent.VK_C:
				new Thread(() -> mal.cancel()).start();
				return;
			default :
				return;
		}
		commandListener.executeFKeyCommand(n, e.isShiftDown());
	}

	@Override
	public void setMouseListener(MouseAllListener mal) {
		this.mal = mal;
		mc.setMouseAllListener(mal);
	}
	
	public void repaint() {
		mc.repaint();
	}

	public void setCommandListener(CommandListener commandListener) {
		this.commandListener = commandListener;
	}
}
