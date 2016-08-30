package dragon3.panel;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dragon3.common.constant.GameColor;
import dragon3.controller.UnitWorks;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.paint.MineGraphics;

@Singleton
public class SmallPanel implements PaintListener {

	public static final int MAP_WIDTH = 640;
	public static final int MAP_HEIGHT = 480;
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 15;

	private PaintComponent panel;
	
	private String label = "";
	
	private GameColor color = GameColor.NONE;
	
	private int width;
	
	private int height;

	/*** Constructer *********************************************/

	@Inject
	public SmallPanel(@Named("smallC") PaintComponent panel) {
		this.panel = panel;
		panel.setVisible(false);
		panel.setPaintListener(this);
	}

	/*** Locate ***********************************************/

	private void calcLocation(int x, int y) {
		int mx = x * 32 + 16 - width / 2;
		mx = Math.max(0, Math.min(mx, MAP_WIDTH - width));
		int my = y * 32 + 20;
		my = Math.max(0, Math.min(my, MAP_HEIGHT - height));
		panel.setLocation(mx, my);
	}

	/*** Display ******************************************************/

	public void display(String label, GameColor color, int x, int y) {

		this.label = label;
		this.color = color;
		
		this.width = label.getBytes().length * 4 + 6;
		this.height = 15;
		
		panel.setSize(width, height);
		calcLocation(x, y);
		panel.setVisible(true);
	}

	/*** Paint *********************************************************/

	@Override
	public void paint(MineGraphics g) {
		g.setColor(color.getBg());
		g.fillRect(0, 0, width, height);
		g.setColor(0, 0, 0);
		g.drawRect(0, 0, width - 1, height - 1);
		g.setColor(color.getFg());
		g.drawString(label, 2, 12);
	}
	
	public void setVisible(boolean flag) {
		panel.setVisible(flag);
	}

}
