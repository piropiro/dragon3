package dragon3.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dragon3.attack.Attack;
import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.image.ImageManager;
import dragon3.panel.item.HPBar;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.event.SleepManager;
import mine.paint.MineColor;
import mine.paint.MineGraphics;

@Singleton
public class MessagePanel implements PaintListener {

	public static final int WIDTH = 160;
	public static final int HEIGHT = 128;

	private PaintComponent panel;
	
	private List<String> list;
	private Body ba;
	private int n;
	private int line;

	static final int MAX = 3;

	protected HPBar hpb;
	@Inject SleepManager sm;
	@Inject ImageManager im;
	
	protected int width;
	protected int height;

	@Inject
	public MessagePanel(@Named("messageC") PaintComponent panel) {
		this.width = WIDTH;
		this.height = HEIGHT;
		//MineAwtUtils.setSize(this, width, height);
		//panel.setFontSize(14);

		hpb = new HPBar();
		this.panel = panel;
		
		list = new ArrayList<>();
		//panel.setFontSize(14);
		panel.setPaintListener(this);
	}

	public void setHPBar(Body b, Attack attack) {
		if (attack != null) {
			int damage = attack.getDamage() * attack.getRate() / 100;
			hpb.setup(attack.isHit(), b.getHp(), b.getHpMax());
			hpb.setMin(b.getHp() - damage, false);
		} else {
			hpb.setup(false, b.getHp(), b.getHpMax());
		}
	}

	/*** Main **********************************************/

	private void drawMain(Body ba, MineGraphics g) {
		g.drawImage(im.getWhiteBack(), 10, 10);
		g.drawImage(im.getBodyImageList().getImage(ba.getImageNum()), 10, 10);
		g.drawString(ba.base.getName(), 50, 22);
		g.drawString("Lv." + ba.getLevel(), 52, 41);
	}
	
	private void drawHp(Body ba, MineGraphics g) {
		drawLine(Texts.hp, 0, 0, g);
		hpb.paint(52, 60, g);
	}
	

	/*** Line ***************************************/

	public void drawText(String lines, MineGraphics g) {
		StringTokenizer st = new StringTokenizer(lines, "&");
		g.drawString(st.nextToken(), 50, 32);
		for (int i = 0; i <= 3; i++) {
			if (!st.hasMoreTokens())
				break;
			drawLine(st.nextToken(), 0, i, g);
		}
	}

	public void drawLine(String name, int st, int x, int y, MineGraphics g) {
		g.drawString(name, 10 + 70 * x, 60 + 19 * y);
		g.drawString("" + st, 52 + 70 * x, 60 + 19 * y);
	}
	public void drawLine(String name, int x, int y, MineGraphics g) {
		g.drawString(name, 10 + 70 * x, 60 + 19 * y);
	}

	/*** Clear *********************************************/

	public boolean clear(GameColor color, MineGraphics g) {
		g.setColor(color.getAlphaBg());

		g.fillRect(0, 0, width, height);
		g.setColor(MineColor.WHITE);
		g.drawRect(2, 2, width - 5, height - 5);
		return true;
	}
	
	/*** Locate ***********************************************/

	public void setLocate() {
		int mx = 300 - WIDTH / 2;
		int my = 240 - HEIGHT / 2;
		panel.setLocation(mx, my);
	}

	/*** Text **************************************/

	public void addMessage(String message) {
		list.add(message);
	}

	/*** Display ******************************************************************/

	public void display(Body ba_) {
		this.ba = ba_;
		if (list.size() == 0)
			return;
		setHPBar(ba, null);
		setLocate();
		panel.setVisible(true);

		for (int i = 0; i < list.size(); i++) {
			n = i;
			String text = (String) list.get(n);
			for (line = 0; line <= text.length(); line++) {
				panel.repaint();
				sm.sleep(80);
			}
			sm.sleep(200);
		}
		sm.sleep(700);
		list.clear();
		panel.setVisible(false);
	}

	/*** Paint **************************************************************/

	@Override
	public void paint(MineGraphics g) {
		g.setFont("Dialog", 14);
		clear(ba.getColor(), g);
		drawMain(ba, g);
		drawHp(ba, g);

		int s = Math.max(0, n - MAX + 1);

		for (int i = s; i <= n; i++) {
			if (i >= list.size())
				break;
			String text = (String) list.get(i);
			if (i == n) {
				drawLine(
					text.substring(0, Math.min(line, text.length())),
					0,
					i - s + 1,
					g);
			} else {
				drawLine(text, 0, i - s + 1, g);
			}
		}
	}

	
}
