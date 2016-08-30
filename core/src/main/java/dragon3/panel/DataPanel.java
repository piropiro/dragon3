package dragon3.panel;

import java.util.StringTokenizer;

import dragon3.Statics;
import dragon3.attack.Attack;
import dragon3.camp.Equip;
import dragon3.common.Body;
import dragon3.common.constant.BodyKind;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.common.util.MoveUtils;
import dragon3.controller.UnitWorks;
import dragon3.data.WazaData;
import dragon3.image.ImageManager;
import dragon3.manage.LevelManager;
import dragon3.panel.item.EXPBar;
import dragon3.panel.item.HPBar;
import dragon3.panel.paint.AnalyzePaint;
import dragon3.panel.paint.AttackPaint;
import dragon3.panel.paint.CampDataPaint;
import dragon3.panel.paint.CounterPaint;
import dragon3.panel.paint.DataPaint;
import dragon3.panel.paint.DataPanelPainter;
import dragon3.panel.paint.ItemPaint;
import dragon3.panel.paint.PlacePaint;
import dragon3.panel.paint.Score1Paint;
import dragon3.panel.paint.Score2Paint;
import dragon3.panel.paint.StatusPaint;
import dragon3.panel.paint.SummonPaint;
import dragon3.panel.paint.TypeListPaint;
import dragon3.panel.paint.WazaListPaint;
import dragon3.panel.paint.WazaPaint;
import dragon3.save.SaveData;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.event.SleepManager;
import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.util.Point;

public class DataPanel implements PanelWorks, PaintListener {

	public static final int WIDTH = 160;
	public static final int HEIGHT = 128;

	private Statics statics;


	private PaintComponent panel;

	private DataPanelPainter pp;

	private GameColor bgcolor = GameColor.BLUE;

	protected HPBar hpb;
	protected EXPBar expb;
	private SleepManager sm;
	private ImageManager im;

	protected boolean left;	
	protected int width;
	protected int height;

	public DataPanel(PaintComponent panel, Statics statics, SleepManager sm, ImageManager im, boolean left) {
		this.statics = statics;
		this.sm = sm;
		this.im = im;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.left = left;
		this.panel = panel;

		hpb = new HPBar();
		expb = new EXPBar();
		panel.setPaintListener(this);
	}

	public void setEXPBar(Body b) {
		expb.setup(b.getExp(), LevelManager.MAX_EXP);
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

	@Override
	public void drawMain(Body ba, MineGraphics g) {
		g.drawImage(im.getWhiteBack(), 10, 10);
		g.drawImage(im.getBodyImageList().getImage(ba.getImageNum()), 10, 10);
		g.drawString(ba.base.getName(), 50, 22);
		g.drawString("Lv." + ba.getLevel(), 52, 41);
	}
	
	@Override
	public void drawHp(Body ba, MineGraphics g) {
		drawLine(Texts.hp, 0, 0, g);
		hpb.paint(52, 60, g);
	}
	
	@Override
	public void drawExp(Body ba, MineGraphics g) {
		drawLine("EXP", 0, 0, g);
		expb.paint(52, 60, g);
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


	/*** Score *******************************************/

	public void displayScore1(SaveData sd) {
		MineImage cBlueImage = im.getStageObj()[MoveUtils.C_BLUE];
		pp = new Score1Paint(sd, cBlueImage);
		display();
	}

	public void displayScore2(Equip equip, SaveData sd) {
		pp = new Score2Paint(equip, sd);
		display();
	}

	/*** Status *******************************************/

	public void displayCamp(Point pa, int tikei, GameColor bgcolor_) {
		pp = new CampDataPaint(tikei, im.getWhiteBack(), im.getWaku(), bgcolor_, pa);
		display();
	}

	public void displayPlace(Point pa, int tikei) {
		pp = new PlacePaint(tikei, im.getStageObj(), pa);
		display();
	}

	public void displayItem(Point pa, int turn, int limit, int tikei) {
		pp = new ItemPaint(turn, limit, tikei, im.getStageObj(), pa);
		display();
	}
	
	public void displaySummon(Point pa, int turn, int limit, int tikei) {
		pp = new SummonPaint(turn, limit, tikei, im.getStageObj(), pa);
		display();
	}

	public void displayData(Point pa, int turn, int treasureLimit, String treasureCount) {
		MineImage cBlueImage = im.getStageObj()[MoveUtils.C_BLUE];
		pp = new DataPaint(turn, treasureLimit, treasureCount, cBlueImage, pa);
		display();
	}

	public void displayAnalyze(Body ba) {
		pp = new AnalyzePaint(ba);
	
		setHPBar(ba, null);
		setEXPBar(ba);
		display();
	}
	
	public void displayStatus(Body ba) {		
		pp = new StatusPaint(ba);

		setHPBar(ba, null);
		setEXPBar(ba);
		
		display();
	}
	
	public void displayTypeList(Body ba) {
		pp = new TypeListPaint(ba);

		setHPBar(ba, null);
		setEXPBar(ba);
		
		display();
	}

	public void displayWazaList(Body ba) {
		pp = new WazaListPaint(statics, ba);

		setHPBar(ba, null);
		setEXPBar(ba);
		
		display();
	}

	public void displayWaza(Body ba, int i) {
		WazaData waza = statics.getWazaData(ba.getWazaList().get(i));	
		pp = new WazaPaint(ba, waza, im.getWhiteBack());

		setHPBar(ba, null);
		setEXPBar(ba);
		
		display();
	}
	
	public void displayCounter(Attack counter) {

		Body ba = counter.getReceiver();

		pp = new CounterPaint(counter);
		bgcolor = pp.getColor();
		setLocate(pp.getPoint1(), pp.getPoint2(), 2);
		
		setHPBar(ba, counter);

		panel.setVisible(true);
	}
	
	public void displayAttack(Attack attack, Attack counter) {
		Body ba = attack.getAttacker();

		pp = new AttackPaint(attack);
		bgcolor = pp.getColor();
		setLocate(pp.getPoint1(), pp.getPoint2(), 2);
		setHPBar(ba, counter);

		panel.setVisible(true);
	}
	
	public void displayNext(Body ba) {
		if (ba != null && ba.base.getKind() == BodyKind.WAZA) {
			displayWaza(ba, 0);
		} else if (pp == null) {
			displayAnalyze(ba);
		} else if (pp instanceof StatusPaint) {
			displayAnalyze(ba);
		} else if (pp instanceof AnalyzePaint) {
			displayTypeList(ba);
		} else if (pp instanceof TypeListPaint) {
			displayWazaList(ba);
		} else if (pp instanceof WazaListPaint) {
			displayStatus(ba);
		}
	}
	
	private void display() {
		setLocate(pp.getPoint1(), pp.getPoint2(), 1);
		bgcolor = pp.getColor();
		panel.setVisible(true);
	}

	/*** Locate ***********************************************/

	public void setLocate(Point ba, Point bb, int size) {

		int mx = 0;
		int my = 0;

		mx =
			Math.min(
				(ba.x + bb.x) * 16 + 64 + 16,
				20 * 32 - width * size);
		if (Math.max(ba.y, bb.y) < 10) {
			my =
				Math.min(
					Math.max(ba.y, bb.y) * 32 + 96 + 16,
					15 * 32 - height);
		} else if (Math.min(ba.y, bb.y) >= 5) {
			my = Math.max(0, Math.min(ba.y, bb.y) * 32 - height - 64 - 16);
		} else {
			my = (ba.y + bb.y) * 16 + 16 - height / 2;
		}
		if (!left) {
			mx += width;
		}
		panel.setLocation(mx, my);
	}
	
	/*** Paint *****************************************************/

	@Override
	public void paint(MineGraphics g) {
		g.setFont("Dialog", 14);
		clear(bgcolor, g);
		if (pp != null) {
			pp.paint(this, g);
		}
	}

	/*** Damage **********************************************/

	public void damage(Body ba, int damage) {
		hpb.setMin(ba.getHp() - damage, true);
		panel.update();
		//uw.repaint(50, 50, 96, 12);
	}

	/*** Henka **************************************************/

	public void henka() {
		int st = hpb.getSleepTime() / 2;
		while (hpb.henka()) {
			panel.update();
			//uw.repaint(50, 50, 96, 12);
			sm.sleep(st);
		}
		panel.update();
		//uw.repaint();
	}
	
	public void repaint() {
		panel.update();
		//uw.repaint();
	}
	
	public void setVisible(boolean flag) {
		panel.setVisible(flag);
	}

}
