package card.paint;

import card.UnitWorks;
import card.common.Page;
import lombok.Getter;
import mine.paint.UnitMap;

public class WakuPainter {

	@Getter private int x;
	@Getter private int y;
	private UnitWorks canvas;
	private UnitMap map;

	public WakuPainter(UnitWorks canvas, UnitMap map){
		this.canvas = canvas;
		this.map = map;
	}

	public boolean isMoved(int new_x, int new_y){
		return ( x != new_x || y != new_y );
	}

	public void moveWaku(int new_x, int new_y) {

		if ( isMoved(new_x, new_y) ) { 
			map.setData(Page.WAKU, x, y, 0);
			map.setData(Page.WAKU, new_x, new_y, 1);
			x = new_x;
			y = new_y;
			canvas.repaint(map.getPaintBox());
		}
	}
}
