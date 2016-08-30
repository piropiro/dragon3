package dragon3.panel;

import dragon3.common.Body;
import dragon3.controller.UnitWorks;
import dragon3.panel.item.HPBar;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.event.SleepManager;
import mine.paint.MineColor;
import mine.paint.MineGraphics;

public class HPanel implements PaintListener {

	public static final int WIDTH = 96;
	public static final int HEIGHT = 14;
	
	private PaintComponent panel;

	private SleepManager sm;
	private Body ba;
	private boolean high;

	private HPBar hpb;

	private UnitWorks uw;

	/*** Constructer ***********************************************/

	public HPanel(PaintComponent panel, SleepManager sm, boolean high) {
		this.sm = sm;
		this.panel = panel;
		this.high = high;
		panel.setVisible(false);
		//panel.setFontSize(14);
		hpb = new HPBar();
		panel.setPaintListener(this);
	}

	/*** Locate ***********************************************/

	public void setLocate(Body ba, Body bb) {
		int cx = ba.getX();
		int cy = ba.getY();
		int mx, my;
		if (ba.getX() >= bb.getX())
			mx = cx * 32 + 32;
		else
			mx = cx * 32 - WIDTH;
		if (cx < 3) {
			mx = cx * 32 + 32;
			if (ba.getY() == bb.getY() && Math.abs(ba.getX() - bb.getX()) < 5)
				mx = Math.max(ba.getX(), bb.getX()) * 32 + 32;
		}
		if (cx > 16) {
			mx = cx * 32 - WIDTH;
			if (ba.getY() == bb.getY() && Math.abs(ba.getX() - bb.getX()) < 5)
				mx = Math.min(ba.getX(), bb.getX()) * 32 - WIDTH;
		}
		if (high) {
			my = cy * 32 + 1;
		} else {
			my = cy * 32 + 17;
		}
		panel.setLocation(mx, my);
	}

	/*** Display ******************************************************/

	public void display(Body ba_, Body bb, int damage, boolean hit) {
		if (ba_ == null) {
			panel.setVisible(false);
			return;
		}
		this.ba = ba_;
		hpb.setup(hit, ba.getHp(), ba.getHpMax());
		hpb.setMin(ba.getHp() - damage, false);

		setLocate(ba, bb);
		panel.setVisible(true);
		uw.repaint();
	}

	/*** Paint *********************************************************/

	@Override
	public void paint(MineGraphics g) {
		if (ba == null)
			return;
		
		g.setFont("serif", 14);
		g.setColor(ba.getColor().getAlphaBg());
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(MineColor.BLACK);
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

		hpb.paint(2, 12, g);
	}

	/*** Damage **********************************************/

	public void damage(int damage) {
		hpb.setMin(ba.getHp() - damage, true);
		panel.update();
		uw.repaint();
	}

	/*** Henka **************************************************/

	public void henka() {
		int st = hpb.getSleepTime();
		while (hpb.henka()) {
			panel.update();
			uw.repaint();
			sm.sleep(st);
		}
		panel.update();
		uw.repaint();
	}
	
	public void setVisible(boolean flag) {
		panel.setVisible(flag);
	}

	public void setUw(UnitWorks uw) {
		this.uw = uw;
	}
}
