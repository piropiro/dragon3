package mine.event;

import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

public class MineCanvasLayer implements PaintComponent, PaintListener {

	private MineCanvas parent;
	
	private MineImageLoader mil;
	
	private MineImage buffer;
	
	private PaintListener paintListener;
	
	private int x, y, w, h;
	
	private boolean visible;
	
	private boolean updated;
	
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
		}
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
}
