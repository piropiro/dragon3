package dragon3.manage;

import javax.inject.Inject;
import javax.inject.Singleton;

import dragon3.common.Body;
import dragon3.common.constant.Page;
import dragon3.controller.UnitWorks;
import dragon3.map.MapWorks;
import dragon3.map.StageMap;
import dragon3.paint.PaintUtils;
import dragon3.panel.PanelManager;
import mine.paint.UnitMap;

@Singleton
public class RewalkManager {

	
	UnitWorks uw;
	@Inject PanelManager pm;
	@Inject StageMap map;
	@Inject MapWorks mw;
	
	private int x, y;

	/*** Setup *******************************/

	@Inject
	public RewalkManager() {
	}

	/*** Set *********************************/

	public void set(Body b) {
		x = b.getX();
		y = b.getY();
	}

	/*** Rewalk ******************************/

	public void rewalk(Body b) {
		UnitMap map = this.map.getMap();
		map.clear(Page.P40, 0);
		map.clear(Page.P10, 0);
		map.setData(Page.P20, b.getX(), b.getY(), 0);
		map.setData(Page.P30, b.getX(), b.getY(), 0);
		int sts = map.getData(Page.P50, b.getX(), b.getY());
		map.setData(Page.P50, b.getX(), b.getY(), 0);
		b.setX(x);
		b.setY(y);
		map.setData(Page.P20, b.getX(), b.getY(), b.getImageNum());
		map.setData(Page.P50, b.getX(), b.getY(), sts);
		PaintUtils.setWalkPaint(uw, b);
		mw.repaint();
		pm.closeHp();
		pm.closeSmall();
		pm.closeData();
	}

	/*** Walk Judge ********************/

	public boolean isWalked(Body b) {
		if (b.getX() != x)
			return true;
		if (b.getY() != y)
			return true;
		return false;
	}

	public void setUw(UnitWorks uw) {
		this.uw = uw;
	}
}
