package mine.event;

import lombok.Getter;
import lombok.Setter;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

public class MineCanvasLayer implements PaintComponent, PaintListener {

	private MineCanvas parent;
	
	private MineImageLoader mil;
	
	private MineImage buffer;
	
	private PaintListener paintListener;
	
	@Getter private int x, y, w, h;
	
	@Getter private boolean visible;
	
	@Getter @Setter private boolean updated;
	
	public MineCanvasLayer(MineCanvas parent, MineImageLoader mil, int x, int y, int w, int h) {
		this.parent = parent;
		this.mil = mil;
		setBounds(x, y, w, h);
	}

	@Override
	public void update() {
		updated = true;
	}

	@Override
	public void repaint() {
		updated = true;
		parent.repaint();
	}
	
	@Override
	public void paint(MineGraphics g) {
		if (visible) {
			if (updated) {
				buffer = mil.getBuffer(w, h);
				paintListener.paint(buffer.getGraphics());
			}

			g.drawImage(buffer, x, y);
			
			updated = false;
		}
	}
	
	@Override
	public void setVisible(boolean flag) {
		if (visible != flag) {
			this.visible = flag;
			this.updated = true;
			parent.repaint();
		}
	}

	@Override
	public void repaint(int x, int y, int w, int h) {
		updated = true;
		parent.repaint();
	}

	@Override
	public void setBounds(int x, int y, int w, int h) {
		setLocation(x, y);
		setSize(w, h);
	}

	@Override
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		updated = true;
	}

	@Override
	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
		this.buffer = mil.getBuffer(w, h);
		updated = true;
	}

	@Override
	public void setPaintListener(PaintListener pl) {
		System.out.println(pl);
		this.paintListener = pl;
	}
}
