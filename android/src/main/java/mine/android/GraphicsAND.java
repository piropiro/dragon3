package mine.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import mine.paint.MineColor;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

/**
 * Created by rara on 2016/08/02.
 */
public class GraphicsAND implements MineGraphics {

    private Canvas c;
    private Paint paint;
    private int alpha = 255;


    public GraphicsAND(Canvas c) {
        this.c = c;
        paint = new Paint();
    }

    @Override
    public void setColor(MineColor color) {
        int[] rgb = color.getRgb();
        paint.setARGB(alpha, rgb[0], rgb[1], rgb[2]);
    }

    @Override
    public void setColor(int r, int g, int b) {
        paint.setARGB(alpha, r, g, b);
    }

    @Override
    public void setColor(int r, int g, int b, int a) {
        this.alpha = a;
        paint.setARGB(a, r, g, b);
    }

    @Override
    public void setColor(int[] rgba) {
        this.alpha = rgba[3];
        paint.setARGB(rgba[3], rgba[0], rgba[1], rgba[2]);
    }

    @Override
    public void fillRect(int x, int y, int w, int h) {
        paint.setStyle(Paint.Style.FILL);
        c.drawRect(x, y, x + w, y + h, paint);
    }

    @Override
    public void drawRect(int x, int y, int w, int h) {
        paint.setStyle(Paint.Style.STROKE);
        c.drawRect(x, y, x + w, y + h, paint);
    }

    @Override
    public void drawLine(int sx, int sy, int dx, int dy) {
        paint.setStyle(Paint.Style.STROKE);
        c.drawLine(sx, sy, dx, dy, paint);
    }

    @Override
    public void drawString(String s, int x, int y) {
        paint.setStyle(Paint.Style.FILL);
        c.drawText(s, x, y, paint);
    }

    @Override
    public void drawImage(MineImage image, int x, int y) {
        c.drawBitmap((Bitmap) image.getImage(), x, y, paint);
    }

    @Override
    public void drawImage(MineImage image, int sx, int sy, int w, int h, int dx, int dy) {

    }

    @Override
    public void drawFitImage(MineImage image, int dw, int dh) {
        Paint paint = new Paint();
        paint.setFilterBitmap(true);

        c.drawBitmap((Bitmap)image.getImage(), null, new Rect(0, 0, dw, dh), paint);
    }

    @Override
    public void drawRotateImage(MineImage image, int dx, int dy, double angle) {
    }

    @Override
    public void setAlpha(double alpha) {
        this.alpha = (int)(255 * alpha);
        paint.setAlpha(this.alpha);
    }

    @Override
    public void setAntialias(boolean flag) {

    }

    @Override
    public void setFont(String font, int size) {

    }

    @Override
    public void drawString(String s, int x, int y, int xs) {
        c.drawText(s, x, y, paint);
    }
}
