package dragon3.panel;

import java.util.ArrayList;
import java.util.List;

import dragon3.common.Body;
import dragon3.image.ImageManager;
import mine.event.PaintComponent;
import mine.event.SleepManager;
import mine.paint.MineGraphics;

public class MessagePanel extends PanelBase {

	public static final int WIDTH = 160;
	public static final int HEIGHT = 128;

	private PaintComponent panel;
	
	private List<String> list;
	private Body ba;
	private int n;
	private int line;

	static final int MAX = 3;

	public MessagePanel(PaintComponent panel, SleepManager sm, ImageManager im) {
		super(panel, sm, im, WIDTH, HEIGHT, true);
		this.panel = panel;
		
		list = new ArrayList<>();
		//panel.setFontSize(14);
		panel.setPaintListener(this);
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
		setHPBar(false, ba);
		setLocate();
		panel.setVisible(true);

		for (int i = 0; i < list.size(); i++) {
			n = i;
			String text = (String) list.get(n);
			for (line = 0; line <= text.length(); line++) {
				panel.repaint();
				sleep(80);
			}
			sleep(200);
		}
		sleep(700);
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
