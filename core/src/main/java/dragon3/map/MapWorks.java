package dragon3.map;

import mine.util.Point;

public interface MapWorks {

	/*** Paint *************************/

	public void update();
	public void repaint();
	public void ppaint(int x, int y);
	public void ppaint(int[] box);

	/*** Waku *************************/

	public Point getWaku();
	public void wakuPaint(int wx, int wy, boolean flag);

}
