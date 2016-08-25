package mine.event;

import java.util.List;

import mine.paint.MineGraphics;
import mine.paint.MineImageLoader;

public interface MineCanvas {

	public PaintComponent newLayer(int x, int y, int w, int h);
//	public default PaintComponent newLayer(int x, int y, int w, int h) {
//		MineCanvasLayer layer = new MineCanvasLayer(this, getImageLoader(), x, y, w, h);
//		getLayers().add(layer);
//
//		return layer;
//	}
//
	public void paint(MineGraphics g);
//	public default void paint(MineGraphics g) {
//		getLayers().forEach((layer) -> layer.paint(g));
//	}
	
	public MineImageLoader getImageLoader();
	
	public List<PaintListener> getLayers();
	
	public void repaint();
	
	public void repaint(int x, int y, int w, int h);
	
	public void setMouseAllListener(MouseAllListener mal);
	
	public boolean isRunning();
}
