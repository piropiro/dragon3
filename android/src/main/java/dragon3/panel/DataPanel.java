package dragon3.panel;

import dragon3.Statics;
import dragon3.attack.Attack;
import dragon3.camp.Equip;
import dragon3.common.Body;
import dragon3.common.constant.BodyKind;
import dragon3.common.constant.GameColor;
import dragon3.common.util.MoveUtils;
import dragon3.data.WazaData;
import dragon3.image.ImageManager;
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
import mine.event.SleepManager;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.util.Point;

public class DataPanel extends PanelBase {

	public static final int WIDTH = 160;
	public static final int HEIGHT = 128;

	private PaintComponent panel;

	private ImageManager im;

	private DataPanelPainter pp;

	private GameColor bgcolor = GameColor.BLUE;
	
	private MineImage cBlueImage;


	/*** Constructer *******************************************/

	public DataPanel(PaintComponent panel, SleepManager sm, ImageManager im, boolean left) {
		super(panel, sm, im, WIDTH, HEIGHT, left);
		this.panel = panel;
		this.im = im;
		
		this.cBlueImage = im.getStageObj()[MoveUtils.C_BLUE];
		
		panel.setPaintListener(this);
	}

	/*** Score *******************************************/

	public void displayScore1(SaveData sd) {
		bgcolor = GameColor.BLUE;
		setLocate(new Point(2, 1), 1);
		pp = new Score1Paint(sd, cBlueImage);
		panel.repaint();
		panel.setVisible(true);
	}

	public void displayScore2(Equip equip, SaveData sd) {
		bgcolor = GameColor.BLUE;
		setLocate(new Point(3, 1), 1);
		pp = new Score2Paint(equip, sd);
		panel.repaint();
		panel.setVisible(true);
	}

	/*** Status *******************************************/

	public void displayCamp(Point pa, int tikei, GameColor bgcolor_) {
		this.bgcolor = bgcolor_;
		setLocate(pa, 1);
		pp = new CampDataPaint(tikei, im.getWhiteBack(), im.getWaku());
		panel.repaint();
		panel.setVisible(true);
	}

	public void displayPlace(Point pa, int tikei) {
		bgcolor = GameColor.GREEN;
		setLocate(pa, 1);
		pp = new PlacePaint(tikei, im.getStageObj());
		panel.repaint();
		panel.setVisible(true);
	}

	public void displayItem(Point pa, int turn, int limit, int tikei) {
		bgcolor = GameColor.GREEN;
		setLocate(pa, 1);
		pp = new ItemPaint(turn, limit, tikei, im.getStageObj());
		panel.repaint();
		panel.setVisible(true);
	}
	
	public void displaySummon(Point pa, int turn, int limit, int tikei) {
		bgcolor = GameColor.GREEN;
		setLocate(pa, 1);
		pp = new SummonPaint(turn, limit, tikei, im.getStageObj());
		panel.repaint();
		panel.setVisible(true);
	}

	public void displayData(Point pa, int turn, int treasureLimit, String treasureCount) {
		bgcolor = GameColor.GREEN;
		setLocate(pa, 1);
		pp = new DataPaint(turn, treasureLimit, treasureCount, cBlueImage);
		panel.repaint();
		panel.setVisible(true);
	}

	private void display(Body ba, DataPanelPainter pp_) {
		if (ba == null) {
			panel.setVisible(false);
			return;
		}
		this.pp = pp_;
		bgcolor = ba.getColor();
		setLocate(ba, 1);
		setHPBar(false, ba);
		setEXPBar(ba);
		panel.repaint();
		panel.setVisible(true);		
	}

	public void displayAnalyze(Body ba) {
		display(ba, new AnalyzePaint(ba));
	}
	
	public void displayStatus(Body ba) {
		display(ba, new StatusPaint(ba));
	}
	
	public void displayTypeList(Body ba) {
		display(ba, new TypeListPaint(ba));
	}

	public void displayWazaList(Body ba) {
		display(ba, new WazaListPaint(ba));
	}

	public void displayWaza(Body ba, int i) {
		WazaData waza = (WazaData)Statics.wazaList.getData(ba.getWazaList().get(i));
		display(ba, new WazaPaint(waza, im.getWhiteBack()));
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

	public void displayAttack(Attack attack, Attack counter) {
		Body ba = null;
		Body bb = null;

		if (attack == null) {
			if (counter == null) {
				panel.setVisible(false);
				return;
			} else {
				ba = counter.getReceiver();
				bb = counter.getAttacker();
				bgcolor = ba.getColor();
				pp = new CounterPaint(ba);
			}
		} else {
			ba = attack.getAttacker();
			bb = attack.getReceiver();
			bgcolor = ba.getColor();
			pp = new AttackPaint(bb, attack);
		}

		setLocate(ba, bb, 2);
		setHPBar(ba, counter);

		panel.repaint();
		panel.setVisible(true);
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

}
