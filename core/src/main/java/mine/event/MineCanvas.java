package mine.event;

import java.util.List;

import mine.paint.MineGraphics;
import mine.paint.MineImageLoader;

public interface MineCanvas {
	
	public PaintComponent newLayer(int x, int y, int w, int h);

	public void paint(MineGraphics g);

	public MineImageLoader getImageLoader();
	
	public List<PaintListener> getLayers();
	
	public void repaint();
	
	public void repaint(int x, int y, int w, int h);
	
	public void setMouseAllListener(MouseAllListener mal);
	
	public boolean isRunning();
	
	//public void requestFocus();
}
