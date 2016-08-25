package dragon3.map;

import mine.util.Point;

import dragon3.paint.EventListener;

public interface MapWorks {

	/*** Paint *************************/

	public void update();
	public void repaint();
	public void ppaint(int x, int y);
	public void ppaint(int[] box);

	/*** Waku *************************/

	public Point getWaku();
	public void wakuMove(int x, int y);
	public void wakuPaint(boolean flag);

	/*** Set Listener *******************/

	public void setEventListener(EventListener el);
	public EventListener getEventListener();
}
