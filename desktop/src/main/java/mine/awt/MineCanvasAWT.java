package mine.awt;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import mine.event.MineCanvas;
import mine.event.MouseAllListener;
import mine.event.MouseManager;
import mine.event.PaintListener;
import mine.paint.MineImageLoader;

@SuppressWarnings("serial")
public class MineCanvasAWT extends JComponent implements MineCanvas {

	private MouseManager mm;
	
	private MineImageLoader imageLoader;
	
	private List<PaintListener> layers = new ArrayList<>();
	
	public MineCanvasAWT(MineImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		paint(new GraphicsAWT(g));
	}

	@Override
	public MineImageLoader getImageLoader() {
		return imageLoader;
	}

	@Override
	public List<PaintListener> getLayers() {
		return layers;
	}

	@Override
	public void setMouseAllListener(MouseAllListener mal) {
		if (mm == null) {
			mm = new MouseManagerAWT(this);
		}
		mm.setMouseAllListener(mal);
	}

	@Override
	public boolean isRunning() {
		return mm.isAlive();
	}
}
