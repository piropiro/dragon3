package dragon3.map;

import dragon3.common.Body;
import dragon3.paint.EventListener;
import mine.event.MouseAllListener;
import mine.util.Point;

public class MapEventListener implements MouseAllListener {

	private StageMap stageMap;
	private EventListener el;
	private int wx, wy;
	
	public MapEventListener(StageMap stageMap, EventListener el) {
		this.stageMap = stageMap;
		this.el = el;
	}
	
	@Override
	public void leftPressed(int x, int y) {
		mouseMoved(x, y);
		el.leftPressed();
	}

	@Override
	public void rightPressed(int x, int y) {
		//mouseMoved(x, y);
		el.cancel();
	}

	@Override
	public void leftReleased(int x, int y) {
	}

	@Override
	public void rightReleased(int x, int y) {
	}

	@Override
	public void mouseMoved(int x, int y) {
		Point p = new Point(x / 32, y / 32);
		
		
		if (p.x != wx || p.y != wy) {
			Body b = stageMap.search(p.x, p.y);
			if (b != null) {
				el.setSelectBody(b);
			} else {
				el.setSelectPlace(x, y);
			}
			
			el.mouseMoved(p.x, p.y);
			wx = p.x;
			wy = p.y;
		}
	}


	@Override
	public void leftDragged(int x, int y) {
	}
	@Override
	public void rightDragged(int x, int y) {
	}
	@Override
	public void mouseEntered(int x, int y) {
	}
	
	@Override
	public void mouseExited(int x, int y) {
	}

	@Override
	public void accept() {
		el.accept();
		
	}

	@Override
	public void cancel() {
		el.cancel();
	}
	
}
