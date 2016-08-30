package dragon3.panel;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dragon3.common.constant.GameColor;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.paint.MineGraphics;

@Singleton
public class LargePanel implements PaintListener {

	public static final int WIDTH = 200;
	public static final int HEIGHT = 100;
	
	private PaintComponent panel;

	private String text = "";
	
	private GameColor bgcolor = GameColor.NONE;
	
	private int width;
	
	private int height;


	@Inject
	public LargePanel(@Named("largeC") PaintComponent panel) {
		super();
		this.panel = panel;

		panel.setVisible(false);
		//panel.setFontSize(24);
		panel.setPaintListener(this);
	}

	/*** Locate ***********************************************/

	public void setLocate() {
		int mx = 300 - width / 2;
		int my = 200 - height / 2;
		panel.setLocation(mx, my);
	}

	/*** Display ******************************************************************/

	public void display(String text, GameColor bgcolor, final int sleep) {
		this.text = text;
		this.bgcolor = bgcolor;
		
		width = text.getBytes().length * 14 + 20;
		height = 32;
		
		panel.setSize(width, height);
		
		setLocate();
		panel.setVisible(true);

		new Thread(){
			public void run() {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				panel.setVisible(false);
			}
		}.start();
	}

	/*** Paint **************************************************************/

	public void paint(MineGraphics g) {
		g.setFont("Dialog", 24);
		g.setColor(bgcolor.getAlphaBg());
		g.fillRect(0, 0, width, height);
		g.setColor(bgcolor.getFg());
		g.drawRect(2, 2, width - 5, height - 5);

		//MineAwtUtils.setAntialias(g, true);
		//MineAwtUtils.drawString(text, 0, 24, width, g);
		g.drawString(text,  0,  24, width);
	}

	/*** Dispose ******************************************************/

	public void setVisible(boolean flag) {
		panel.setVisible(flag);
	}

}
