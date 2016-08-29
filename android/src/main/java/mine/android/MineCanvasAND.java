package mine.android;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


import mine.event.MineCanvas;
import mine.event.MineCanvasLayer;
import mine.event.MouseAllListener;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

@SuppressWarnings("serial")
public class MineCanvasAND extends View implements MineCanvas {

    private MouseAllListener mouseAllListener;


	private MineImageLoader imageLoader;

	private List<PaintListener> layers = new ArrayList<>();

    private int width = 1;

    private int height = 1;

    private int xs = -1;
    private int ys = -1;

	public MineCanvasAND(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);


		this.imageLoader = new ImageLoaderAND(context);
	}

    public void setBufferSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public PaintComponent newLayer(int x, int y, int w, int h) {
		MineCanvasLayer layer = new MineCanvasLayer(this, getImageLoader(), x, y, w, h);
		getLayers().add(layer);

		return layer;
	}

    @Override
    public void paint(MineGraphics g) {
        MineImage buffer = getImageLoader().getBuffer(width, height);
        MineGraphics bg = buffer.getGraphics();
        for (PaintListener layer : getLayers()) {
            layer.paint(bg);
        }
        g.drawFitImage(buffer, this.getWidth(), this.getHeight());
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
	protected void onDraw(Canvas canvas) {
		paint(new GraphicsAND(canvas));
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TouchEvent", "X:" + event.getX() + ",Y:" + event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("TouchEvent", "getAction()" + "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TouchEvent", "getAction()" + "ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("TouchEvent", "getAction()" + "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("TouchEvent", "getAction()" + "ACTION_CANCEL");
                break;
        }

        final int x = (int) event.getX() * width / getWidth();
        final int y = (int) event.getY() * height / getHeight();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            new Thread() {
                @Override
                public void run() {
                    mouseAllListener.leftPressed(x, y);
                }
            }.start();
        }

        return true;
    }

    @Override
    public void repaint() {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void repaint(int x, int y, int w, int h) {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void setMouseAllListener(MouseAllListener mal) {
        this.mouseAllListener = mal;
    }

    public MouseAllListener getMouseAllListener() {
        return mouseAllListener;
    }

    @Override
	public boolean isRunning() {
		return false;
	}

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            invalidate();
        }
    };

}

