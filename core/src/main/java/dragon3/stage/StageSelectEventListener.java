package dragon3.stage;

import dragon3.controller.UnitWorks;
import mine.event.MouseAllListener;
import mine.util.Point;

public class StageSelectEventListener implements MouseAllListener {

	public static final int UNIT_WIDTH = 106;
	public static final int UNIT_HEIGHT = 96;
	
	private int wx, wy;
	
	private StageManager stageManager;
	
	private UnitWorks uw;
	
	public StageSelectEventListener(UnitWorks uw, StageManager stageManager) {
		this.uw = uw;
		this.stageManager = stageManager;
		wx = -1;
		wy = -1;
	}
	

	
	@Override
	public void leftPressed(int x, int y) {
		mouseMoved(x, y);
	}

	@Override
	public void rightPressed(int x, int y) {
	}

	@Override
	public void leftReleased(int x, int y) {
	}

	@Override
	public void rightReleased(int x, int y) {
	}

	@Override
	public void mouseMoved(int x, int y) {
		Point p = new Point(x / UNIT_WIDTH, y / UNIT_HEIGHT);
		if (p.x != wx || p.y != wy) {
			this.wx = x;
			this.wy = y;
			stageManager.wakuPaint(p.x, p.y, true);
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
		stageManager.selectStage(wx, wy);	
		uw.stageStart(stageManager.getSelectedStage());
	}

	@Override
	public void cancel() {
		uw.campStart();
	}
}
