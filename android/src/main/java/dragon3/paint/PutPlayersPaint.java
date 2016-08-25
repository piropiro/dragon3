package dragon3.paint;

import mine.util.Point;
import java.util.List;

import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.constant.Texts;
import dragon3.common.util.MoveUtils;
import dragon3.controller.UnitWorks;
import dragon3.map.MapWorks;
import dragon3.panel.PanelManager;
import mine.paint.UnitMap;

public class PutPlayersPaint implements EventListener {

	private UnitWorks uw;
	private MapWorks mw;
	private UnitMap map;
	private PanelManager pm;
	
	static final int MAX = 5;

	private List<Body> charaList;
	private List<Body> playerList;

	private Point ps; // Last Position

	private int max;
	private int n;


	/**
	 * @param uw
	 * @param mw
	 * @param map
	 * @param charaList
	 * @param playerList
	 */
	public PutPlayersPaint(UnitWorks uw, List<Body> charaList, List<Body> playerList) {
		this.uw = uw;
		this.mw = uw.getMapWorks();
		this.map = uw.getUnitMap();
		this.pm = uw.getPanelManager();
		this.pm = uw.getPanelManager();
		
		this.charaList = charaList;
		this.playerList = playerList;
		n = 0;
		setColor();
		autoPut();
		setHelp();
		mw.repaint();
	}

	/**
	 *
	 */
	private void setHelp() {
		pm.displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_SETMENS]);
	}


	/**
	 *
	 */
	private void setColor() {
		map.change(Page.P01, MoveUtils.S_RED, Page.P01, 0);
	}


	/**
	 *
	 */
	private void autoPut() {
		int width = map.getMapWidth();
		int height = map.getMapHeight();
		max = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int d = map.getData(Page.P01, x, y);
				if (d == MoveUtils.S_BLUE) {
					if (!putChara(x, y))
						return;
					max++;
					if (max == MAX)
						return;
				}
			}
		}
	}


	/**
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean putChara(int x, int y) {
		if (playerList.size() == 0)
			return false;

		Body ba = (Body) playerList.get(playerList.size()-1);
		charaList.add(ba);
		playerList.remove(ba);
		ba.setX(x);
		ba.setY(y);
		map.setData(Page.P20, x, y, ba.getImageNum());
		ps = null;
		n++;
		return true;
	}


	/**
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean pickChara(int x, int y) {
		if (n == 0)
			return false;

		Body ba = uw.search(x, y);
		if (ba == null)
			return false;

		charaList.remove(ba);
		playerList.add(ba);
		ps = new Point(x, y);
		n--;
		return true;
	}


	/**
	 * @param x
	 * @param y
	 * @param flag
	 */
	private void nextChara(int x, int y, boolean flag) {
		if (playerList.size() <= 1)
			return;

		if (flag) {
			Body ba = (Body) playerList.get(playerList.size()-1);
			playerList.remove(ba);
			playerList.add(0, ba);
		} else {
			Body ba = (Body) playerList.get(0);
			playerList.remove(ba);
			playerList.add(ba);
		}

		moveChara(x, y);
		mw.ppaint(x, y);
	}


	/**
	 * @param x
	 * @param y
	 */
	private void changeChara(int x, int y) {
		if (playerList.size() == 0)
			return;

		pickChara(x, y);
		nextChara(x, y, true);
		putChara(x, y);
		mw.ppaint(x, y);
		pm.displayStatus(uw.search(x, y));
	}


	/**
	 * @param x
	 * @param y
	 */
	private synchronized void moveChara(int x, int y) {
		if (playerList.size() == 0)
			return;
		if (n == max)
			return;
		int tikei = map.getData(Page.P01, x, y);

		if (ps != null) {
			map.setData(Page.P20, ps.x, ps.y, 0);
		}
		if (tikei == MoveUtils.CLOSE_BOX)
			return;
		if (tikei == MoveUtils.C_BLUE)
			return;
		if (tikei == MoveUtils.C_RED)
			return;
		if (tikei == MoveUtils.CLOSE_MAGIC)
			return;

		if (map.getData(Page.P20, x, y) == 0) {
			Body ba = (Body) playerList.get(playerList.size()-1);
			map.setData(Page.P20, x, y, ba.getImageNum());
			ps = new Point(x, y);
		} else {
			ps = null;
		}
	}


	@Override
	public void leftPressed() {
		Point p = mw.getWaku();
		if (map.getData(Page.P20, p.x, p.y) == 0) {
			uw.getPanelManager().displayData(p.x, p.y);
			return;
		}
		if (map.getData(Page.P01, p.x, p.y) == MoveUtils.S_BLUE) {
			if (uw.search(p.x, p.y) == null) {
				putChara(p.x, p.y);
			} else {
				pickChara(p.x, p.y);
			}
		} else {
			nextChara(p.x, p.y, true);
		}
	}


	@Override
	public void mouseMoved(int x, int y) {
		mw.wakuMove(x, y);
		moveChara(x, y);
		mw.wakuPaint(true);
	}


	@Override
	public boolean isNextPoint(int x, int y) {
		Body b = uw.search(x, y);
		if (b != null)
			return (GameColor.isPlayer(b));
		return false;
	}
	
	/*** Place *****************************************/

	@Override
	public void setSelectPlace(int x, int y) {
		uw.getPanelManager().displayPlace(x, y);
	}

	/*** Select Body *****************************************/

	@Override
	public void setSelectBody(Body b) {
		pm.displayStatus(b);
	}

	/*** Mouse Moved ***********************************/


	/*** Event ************************************/


	@Override
	public void accept() {
		uw.setMensEnd();
	}

	@Override
	public void cancel() {
		Point p = mw.getWaku();
		if (map.getData(Page.P20, p.x, p.y) == 0) {
			PaintUtils.setButtonPaint(uw, p.x, p.y, this, 6);
			return;
		}
		Body b = uw.search(p.x, p.y);
		if (map.getData(Page.P01, p.x, p.y) == MoveUtils.S_BLUE) {
			if (b != null) {
				changeChara(p.x, p.y);
				return;
			}
		}
		if (b != null) {
			pm.displayAnalyze(b);
		} else {
			nextChara(p.x, p.y, false);
		}
	};

}
