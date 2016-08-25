package dragon3.map;

import java.util.List;

import dragon3.Statics;
import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.util.MoveUtils;
import dragon3.data.StageData;
import dragon3.image.ImageManager;
import dragon3.stage.StageBack;
import lombok.Getter;
import mine.MineUtils;
import mine.paint.UnitMap;
import mine.util.Point;

public class StageMap {

	private ImageManager imageManager;

	@Getter private UnitMap map;
	
	private Point blueCrystal;
	private Point redCrystal;
	
	
	public StageMap(ImageManager imageManager) {
		this.imageManager = imageManager;
		
		map = createMap();
	}
	
	private UnitMap createMap() {
//		int mapW = 20;
//		int mapH = 15;
//		int unitW = 32;
//		int unitH = 32;

		UnitMap map = new UnitMap(15, 20, 15, imageManager.getImageLoader());
		map.setTile(Page.P00, imageManager.getStageBack(), -1);
		map.setTile(Page.P01, imageManager.getStageObj(), 0);
		map.setTile(Page.P10, imageManager.getWaku()[1], 0);
		map.setTile(Page.P20, imageManager.getBodyImageList().getImageList(), 0);
		map.setTile(Page.P30, imageManager.getWaku()[0], 0);
		map.setTile(Page.P40, imageManager.getWaku()[2], 0);
		map.setTile(Page.P50, imageManager.getStatus(), 0);
		map.setVisible(Page.P00, true);
		map.setVisible(Page.P01, true);
		map.setVisible(Page.P10, true);
		map.setVisible(Page.P20, true);
		map.setVisible(Page.P30, true);
		map.setVisible(Page.P40, true);
		map.setVisible(Page.P50, true);
		
		return map;
	}
	
	public void putUnit(List<Body> v) {
		map.clear(Page.P20, 0);
		for (Body b : v) {
			if (!b.isAlive())
				continue;
			map.setData(Page.P20, b.getX(), b.getY(), b.getImageNum());
		}
	}
	
	public void resetBack(StageBack stageBack) {
		imageManager.resetBack(stageBack);
		map.setTile(Page.P00, imageManager.getStageBack(), -1);
		map.setTile(Page.P01, imageManager.getStageObj(), -1);
		map.repaint();
	}
	
	public void mapLoad(StageData stageData) {
		
		resetBack(stageData.getBack());
		int[][] data = Statics.getMapData(stageData.getId());
		if (data != null) {
			map.setPage(Page.P01, data);
		}
		map.clear(Page.P10, 0);
		map.clear(Page.P20, 0);
		map.clear(Page.P30, 0);
		map.clear(Page.P40, 0);
		map.clear(Page.P50, 0);
	}
	
	public void setMensEnd() {
		map.change(Page.P01, MoveUtils.S_BLUE, Page.P01, 0);
		map.change(Page.P01, MoveUtils.S_RED, Page.P01, 0);
	}
	
	public void reverseMap() {
		int[] af = { -1, 0, 20 - 1, 0, -1, 15 - 1 };
		int[][] data = new int[15][20];
		MineUtils.affine(map.getPage(Page.P01), data, af);
		map.setPage(Page.P01, data);
	}
	
	/*** Set Crystal ***************************/

	public void setCrystal() {
		int width = map.getMapWidth();
		int height = map.getMapHeight();
		blueCrystal = null;
		redCrystal = null;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (map.getData(Page.P01, x, y) == MoveUtils.C_BLUE) {
					blueCrystal = new Point(x, y);
				}
				if (map.getData(Page.P01, x, y) == MoveUtils.C_RED) {
					redCrystal = new Point(x, y);
				}
			}
		}
	}

	public Point getCrystal(GameColor color) {
		if (color.equals(GameColor.BLUE)) {
			return blueCrystal;
		} else if (color.equals(GameColor.RED)) {
			return redCrystal;
		} else {
			return null;
		}
	}
	
	public boolean crushCrystal(int x, int y, GameColor color) {
		
		switch (color) {
		case RED:
			if (map.getData(Page.P01, x, y) == MoveUtils.C_RED) {
				map.fillDia(Page.P01, x, y, 1, MoveUtils.C_REDC);
				return true;
			} else {
				return false;
			}
		case BLUE:
			if (map.getData(Page.P01, x, y) == MoveUtils.C_BLUE) {
				map.fillDia(Page.P01, x, y, 1, MoveUtils.C_BLUEC);
				return true;
			} else {
				return false;
			}
		default:
			return false;
		}
	}
	
	public void setEnd(int x, int y) {
		map.setData(Page.P30, x, y, 1);
	}
	
	public boolean isEnd(int x, int y) {
		return map.getData(Page.P30, x, y) == 1;
	}
}
