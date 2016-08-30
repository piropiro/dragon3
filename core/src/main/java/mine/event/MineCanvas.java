package mine.event;

import java.awt.Graphics;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

import mine.paint.MineGraphics;
import mine.paint.MineImageLoader;

public class MineCanvas implements PaintListener {

	private MineImageLoader imageLoader;

	private List<PaintListener> layers = new ArrayList<>();

	public MineCanvas(MineImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}


	public MineImageLoader getImageLoader() {
		return imageLoader;
	}

	public List<PaintListener> getLayers() {
		return layers;
	}

	public PaintComponent newLayer(int x, int y, int w, int h) {
		MineCanvasLayer layer = new MineCanvasLayer(this, getImageLoader(), x, y, w, h);
		getLayers().add(layer);

		return layer;
	}

	public void paint(MineGraphics g) {
		for (PaintListener layer : getLayers()) {
			layer.paint(g);
		}
	}

}
