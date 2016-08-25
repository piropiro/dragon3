package mine.paint;


import java.util.LinkedList;

import mine.util.Point;


/**
 * ユニットマップを扱うクラス
 * author k-saito
 * version 1.0
 */
public class UnitMap {

	public static final int FALSE = 255;

	private int map[][][]; // マップデータ map[page][y][x]
	private MineImage tile[][]; // タイルイメージ tile[page][data]

	private MineImage buffer[][]; // バッファ buffer[y][x]
	private boolean visible[]; // 表示フラグ visible[page]
	private int spriteData[]; // 透明データ番号 spriteData[data]

	private int pageNum; // ページ数
	private int mapW, mapH; // マップのサイズ（タイル個数）
	private int tileW, tileH; // タイルのサイズ

	private int dispX, dispY; // 表示する範囲の左上座標
	private int dispW, dispH; // 表示する範囲のサイズ
	
	private MineImageLoader mil;

	/**
	 * コンストラクタ<p>
	 * 
	 * @param pageNum ページ数
	 * @param mapW マップの横幅（タイル個数）
	 * @param mapH マップの高さ（タイル個数）
	 */
	public UnitMap(int pageNum, int mapW, int mapH, MineImageLoader mil) {
		this.pageNum = pageNum;
		this.mapW = mapW;
		this.mapH = mapH;
		this.mil = mil;

		dispX = 0;
		dispY = 0;
		dispW = mapW;
		dispH = mapH;

		map = new int[pageNum][mapH][mapW];
		tile = new MineImage[pageNum][];
		buffer = new MineImage[mapH][mapW];
		visible = new boolean[pageNum];
		spriteData = new int[pageNum];
		repaint();
	}

	/**
	 * 指定されたページが正しい値かどうかチェックする。<p>
	 * 
	 * @param page 指定されたページ
	 * @return 正しければtrue
	 */
	private boolean truePage(int page) {
		if (0 <= page && page < pageNum) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 指定されたX座標が正しい値かどうかチェックする。<p>
	 * 
	 * @param x 指定されたX座標
	 * @return 正しければtrue
	 */
	private boolean trueX(int x) {
		if (0 <= x && x < mapW) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 指定されたY座標が正しい値かどうかチェックする。<p>
	 * 
	 * @param y 指定されたY座標
	 * @return 正しければtrue
	 */
	private boolean trueY(int y) {
		if (0 <= y && y < mapH) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 指定されたデータが表示可能かどうかチェックする。<p>
	 * 
	 * @param page 指定されたページ
	 * @param data 指定されたデータ
	 * @return 正しければtrue
	 */
	private boolean trueData(int page, int data) {
		if (tile[page] == null)
			return false;
		if (data < 0)
			return false;
		if (data >= tile[page].length)
			return false;
		if (data == spriteData[page])
			return false;
		return true;
	}

	/**
	 * 指定された座標が正しいかどうかチェックする。<p>
	 * 
	 * @param x 指定されたX座標
	 * @param y 指定されたY座標
	 * @return 正しければtrue
	 */
	private boolean truePoint(int x, int y) {
		return trueX(x) && trueY(y);
	}

	/**
	 * @return ページ数
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @return 横幅（タイル個数）
	 */
	public int getMapWidth() {
		return mapW;
	}

	/**
	 * @return 高さ（タイル個数）
	 */
	public int getMapHeight() {
		return mapH;
	}

	/**
	 * @return タイルの横幅
	 */
	public int getTileWidth() {
		return tileW;
	}

	/**
	 * @return タイルの高さ
	 */
	public int getTileHeight() {
		return tileH;
	}

	/**
	 * タイルイメージを返す。<p>
	 * 
	 * @param p ページ
	 * @param n タイル番号
	 * @return タイルイメージ
	 */
	public MineImage getTile(int p, int n) {
		return tile[p][n];
	}

	/**
	 * マップサイズを変更する。<p>
	 * 
	 * @param mapW
	 * @param mapH
	 */
	public void setSize(int mapW, int mapH) {
		this.mapW = mapW;
		this.mapH = mapH;
		int[][][] maps = map;
		map = new int[pageNum][mapH][mapW];

		for (int page = 0; page < pageNum; page++) {
			for (int y = 0; y < map[page].length; y++) {
				for (int x = 0; x < map[page][y].length; x++) {
					if (y < maps[page].length) {
						if (x < maps[page][y].length) {
							map[page][y][x] = maps[page][y][x];
						}
					}
				}
			}
		}

		dispX = 0;
		dispY = 0;
		dispW = mapW;
		dispH = mapH;

		repaint();
	}

	/**
	 * 表示位置とサイズを設定する。<p>
	 * 
	 * @param dispX 表示位置の左上X座標
	 * @param dispY 表示位置の左上Y座標
	 * @param dispW 表示する横幅
	 * @param dispH 表示する高さ
	 */
	public void setDisplay(int dispX, int dispY, int dispW, int dispH) {
		setDisplayPosition(dispX, dispY);
		setDisplaySize(dispW, dispH);
	}

	/**
	 * 表示位置を設定する。<p>
	 * 
	 * @param dispX 表示位置の左上X座標
	 * @param dispY 表示位置の左上Y座標
	 */
	public void setDisplayPosition(int dispX, int dispY) {
		if (this.dispX != dispX || this.dispY != dispY)
			repaint();
		if (dispX >= 0)
			this.dispX = dispX;
		if (dispY >= 0)
			this.dispY = dispY;
	}

	/**
	 * 表示サイズを設定する。<p>
	 * 
	 * @param dispW 表示する横幅
	 * @param dispH 表示する高さ
	 */
	public void setDisplaySize(int dispW, int dispH) {
		if (dispW >= 0)
			this.dispW = dispW;
		if (dispH >= 0)
			this.dispH = dispH;
	}
	
	/**
	 * 指定された値が設定されている座標を検索
	 * @param page
	 * @param data
	 * @return
	 */
	public Point searchData(int page, int data) {
		for (int y = 0; y < map[page].length; y++) {
			for (int x = 0; x < map[page][y].length; x++) {
				if (data == map[page][y][x]) {
					return new Point(x, y);
				}
			}
		}
		return null;
	}


	/**
	 * @return 表示位置X座標
	 */
	public int getDisplayX() {
		return dispX;
	}
	
	
	/**
	 * @return 表示位置Y座標
	 */
	public int getDisplayY() {
		return dispY;
	}


	/**
	 * @return 表示する横幅
	 */
	public int getDisplayWidth() {
		return dispW;
	}


	/**
	 * @return 表示する高さ
	 */
	public int getDisplayHeight() {
		return dispH;
	}


	/**
	 * バッファをクリアする。
	 */
	public void repaint() {
		synchronized (buffer) {
			buffer = new MineImage[mapH][mapW];
		}
	}

	/**
	 * 再描画する範囲を取得する。<p>
	 * 
	 * @param page 再描画計算用のページ。データが0以外の座標は再描画対象。
	 * @return 再描画する範囲を示すペイントボックス
	 */
	public PaintBox getPaintBox(int page) {
		int sx = dispX + dispW;
		int sy = dispY + dispH;
		int fx = dispX;
		int fy = dispY;
		for (int y = dispY; y < dispY + dispH; y++) {
			for (int x = dispX; x < dispX + dispW; x++) {
				if (map[page][y][x] != 0) {
					if (x < sx)
						sx = x;
					if (y < sy)
						sy = y;
					if (x > fx)
						fx = x;
					if (y > fy)
						fy = y;
				}
			}
		}

		int x = (sx - dispX) * tileW;
		int y = (sy - dispY) * tileH;
		int w = (fx - sx + 1) * tileW;
		int h = (fy - sy + 1) * tileH;

		return new PaintBox(x, y, w, h);
	}

	/**
	 * 再描画する範囲を取得する。<p>
	 * 
	 * @return 再描画する範囲を示すペイントボックス
	 */
	public PaintBox getPaintBox() {
		synchronized (buffer) {
			int sx = dispX + dispW;
			int sy = dispY + dispH;
			int fx = dispX;
			int fy = dispY;
			for (int y = dispY; y < dispY + dispH; y++) {
				for (int x = dispX; x < dispX + dispW; x++) {
					if (buffer[y][x] == null) {
						if (x < sx)
							sx = x;
						if (y < sy)
							sy = y;
						if (x > fx)
							fx = x;
						if (y > fy)
							fy = y;
					}
				}
			}

			int x = (sx - dispX) * tileW;
			int y = (sy - dispY) * tileH;
			int w = (fx - sx + 1) * tileW;
			int h = (fy - sy + 1) * tileH;

			return new PaintBox(x, y, w, h);
		}
	}

	/**
	 * 指定されたページのタイルをセットする。<p>
	 * 
	 * @param page 指定されたページ
	 * @param tiles タイル
	 * @param sprite 透明データ番号
	 */
	public void setTile(int page, MineImage[] tiles, int sprite) {
		tile[page] = tiles;
		spriteData[page] = sprite;
		tileW = tile[page][0].getWidth();
		tileH = tile[page][0].getHeight();
	}

	/**
	 * 指定されたページのタイルをセットする。<p>
	 * 
	 * @param page 指定されたページ
	 * @param tiles タイル
	 * @param sprite 透明データ番号
	 */
	public void setTile(int page, MineImage[][] tiles, int sprite) {

		int xs = tiles[0].length;
		int ys = tiles.length;
		MineImage[] tmp_tile = new MineImage[xs * ys];
		int i = 0;

		for (int y = 0; y < ys; y++) {
			for (int x = 0; x < xs; x++) {
				tmp_tile[i++] = tiles[y][x];
			}
		}
		setTile(page, tmp_tile, sprite);

	}

	/**
	 * ページごとの表示非表示の設定<p>
	 * 
	 * @param page 指定されたページ
	 * @param flag 表示フラグ
	 */
	public void setVisible(int page, boolean flag) {
		if (truePage(page)) {
			visible[page] = flag;
		}
	}

	/**
	 * データの設定<p>
	 * 
	 * @param page 指定されたページ
	 * @param x 指定されたX座標
	 * @param y 指定されたY座標
	 * @param data データ
	 */
	public void setData(int page, int x, int y, int data) {
		if (truePage(page) && truePoint(x, y)) {
			setDataP(page, x, y, data);
		}
	}


	/**
	 * データの設定。引数のチェックはしない。<p>
	 * 
	 * @param page
	 * @param x
	 * @param y
	 * @param data
	 */
	private void setDataP(int page, int x, int y, int data) {
		if (map[page][y][x] != data) {
			map[page][y][x] = data;
			if (visible[page]) {
				synchronized (buffer) {
					buffer[y][x] = null;
				}
			}
		}
	}

	/**
	 * データの取得。<p>
	 * 
	 * @param page 指定されたページ
	 * @param x 指定されたX座標
	 * @param y 指定されたY座標
	 * @return データ（マップ外の場合はFALSEを返す。）
	 */
	public int getData(int page, int x, int y) {
		if (truePage(page) && truePoint(x, y)) {
			return map[page][y][x];
		} else {
			return FALSE;
		}
	}

	/**
	 * データの取得。引数のチェックはしない。<p>
	 * 
	 * @param page
	 * @param x
	 * @param y
	 * @return
	 */
	private int getDataP(int page, int x, int y) {
		return map[page][y][x];
	}

	/**
	 * ページの設定。<p>
	 * 
	 * @param page 指定されたページ
	 * @param data ページデータ
	 */
	public void setPage(int page, int[][] data) {

		int maxX = Math.min(mapW, data[0].length);
		int maxY = Math.min(mapH, data.length);

		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				setDataP(page, x, y, data[y][x]);
			}
		}
	}

	/**
	 * ページの取得。<p>
	 * 
	 * @param p 指定されたページ
	 * @return ページデータ
	 */
	public int[][] getPage(int p) {
		int data[][] = new int[mapH][mapW];

		for (int y = 0; y < mapH; y++) {
			for (int x = 0; x < mapW; x++) {
				data[y][x] = map[p][y][x];
			}
		}
		return data;
	}

	/**
	 * 指定された座標のバッファを取得する。<p>
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public MineImage getBuffer(int x, int y) {
		synchronized (buffer) {
			if (buffer[y][x] == null) {
				buffer[y][x] = mil.getBuffer(tileW, tileH);
				MineGraphics g = buffer[y][x].getGraphics();
				for (int page = 0; page < pageNum; page++) {

					if (!visible[page])
						continue;
					int data = getDataP(page, x, y);
					if (!trueData(page, data))
						continue;
					g.drawImage(tile[page][data], 0, 0);
				}
			}
			return buffer[y][x];
		}
	}

	/**
	 * 指定された座標を描画する。<p>
	 * 
	 * @param x
	 * @param y
	 * @param g
	 */
	public synchronized void drawPoint(int x, int y, MineGraphics g) {
		if (truePoint(x, y)) {
			g.drawImage(getBuffer(x, y), (x - dispX) * tileW, (y - dispY) * tileH);
		}
	}

	/**
	 * マップを描画する。<p>
	 * 
	 * @param g
	 */
	public synchronized void draw(MineGraphics g) {
		for (int x = 0; x < dispW; x++) {
			for (int y = 0; y < dispH; y++) {
				int xs = x + dispX;
				int ys = y + dispY;
				if (truePoint(xs, ys)) {
					g.drawImage(getBuffer(xs, ys), x * tileW, y * tileH);
				}
			}
		}
	}

	/**
	 * 歩数ペイント<p>
	 * 
	 * @param src 歩数ペイント用データページ
	 * @param dest 歩数ペイント結果出力ページ
	 * @param x 始点X座標
	 * @param y 始点Y座標
	 * @param max 最大歩数
	 */
	public void paintStep(int src, int dest, int x, int y, int max) {

		if (!truePoint(x, y))
			return;

		int srcTemp = map[src][y][x];
		map[src][y][x] = 0;

		clear(dest, FALSE);

		LinkedList<int[]> dim = new LinkedList<int[]>();
		dim.add(new int[]{x, y, 0});

		while (!dim.isEmpty()) {
			int[] data = (int[]) dim.getFirst();
			dim.removeFirst();
			int px = data[0];
			int py = data[1];
			int step = data[2];
			if (truePoint(px, py)) {
				step += map[src][py][px];
				
				if (step <= max && step < map[dest][py][px]) {
					map[dest][py][px] = step;
					dim.add(new int[]{px - 1, py, step} );
					dim.add(new int[]{px + 1, py, step} );
					dim.add(new int[]{px, py - 1, step} );
					dim.add(new int[]{px, py + 1, step} );
				}
			}
		}
		map[src][y][x] = srcTemp;
	}

	/**
	 * 指定されたデータでペイントする<p>
	 * 
	 * @param page ページ
	 * @param x 始点X座標
	 * @param y 始点Y座標
	 * @param cd ペイントするデータ
	 */

	public void paint(int page, int x, int y, int cd) {

		if (!truePoint(x, y))
			return;

		LinkedList<Point> dim = new LinkedList<Point>();
		int sd = map[page][y][x];
		if (cd == sd)
			return;
			
		dim.add(new Point(x, y));
		while (dim.size() != 0) {
			Point p = (Point) dim.getFirst();
			dim.remove(0);
			if (truePoint(p.x, p.y)) {
				if (map[page][p.y][p.x] == sd) {
					setDataP(page, p.x, p.y, cd);
					dim.add(new Point(p.x-1, p.y));
					dim.add(new Point(p.x+1, p.y));
					dim.add(new Point(p.x, p.y-1));
					dim.add(new Point(p.x, p.y+1));
				}
			}
		}
	}

	/**
	 * fillRect
	 */
	public void fillRect(int page, int px, int py, int pw, int ph, int data) {

		int sx = Math.max(0, px);
		int sy = Math.max(0, py);
		int lx = Math.min(px + pw, mapW);
		int ly = Math.min(py + ph, mapH);

		for (int y = sy; y < ly; y++) {
			for (int x = sx; x < lx; x++) {
				setDataP(page, x, y, data);
			}
		}
	}

	/**
	 * drawRect
	 */
	public void drawRect(int page, int px, int py, int pw, int ph, int data) {
		drawBox(page, px, py, px + pw - 1, py + ph - 1, data);
	}

	/**
	 * fillBox
	 */
	public void fillBox(int page, int ax, int ay, int bx, int by, int data) {
		int px = Math.min(ax, bx);
		int py = Math.min(ay, by);
		int pw = Math.abs(ax - bx);
		int ph = Math.abs(ay - by);
		fillRect(page, px, py, pw, ph, data);
	}

	/**
	 * boxLine
	 */
	public void drawBox(int page, int ax, int ay, int bx, int by, int data) {

		int sx = Math.min(ax, bx);
		int sy = Math.min(ay, by);
		int lx = Math.max(ax, bx);
		int ly = Math.max(ay, by);

		for (int x = sx; x <= lx; x++) {
			if (!trueX(x))
				continue;
			if (trueY(sy))
				setDataP(page, x, sy, data);
			if (trueY(ly))
				setDataP(page, x, ly, data);
		}
		for (int y = sy; y <= ly; y++) {
			if (!trueY(y))
				continue;
			if (trueX(sx))
				setDataP(page, sx, y, data);
			if (trueX(lx))
				setDataP(page, lx, y, data);
		}
	}

	/**
	 * fillDia 
	 */
	public void fillDia(int page, int x, int y, int range, int data) {

		for (int i = 0; i <= range; i++) {
			for (int j = i - range; j <= range - i; j++) {
				if (!trueX(x + j))
					continue;
				if (trueY(y - i))
					setDataP(page, x + j, y - i, data);
				if (trueY(y + i))
					setDataP(page, x + j, y + i, data);
			}
		}
	}

	/**
	 * drawDia
	 */
	public void drawDia(int page, int x, int y, int range, int data) {

		for (int i = 0; i <= range; i++) {
			int j = range - i;
			if (trueX(x + i)) {
				if (trueY(y - j))
					setDataP(page, x + i, y - j, data);
				if (trueY(y + j))
					setDataP(page, x + i, y + j, data);
			}
			if (trueX(x - i)) {
				if (trueY(y - j))
					setDataP(page, x - i, y - j, data);
				if (trueY(y + j))
					setDataP(page, x - i, y + j, data);
			}
		}
	}

	/**
	 * copyPage
	 */
	public void copyPage(int src, int dest) {

		for (int x = 0; x < mapW; x++) {
			for (int y = 0; y < mapH; y++) {
				setDataP(dest, x, y, map[src][y][x]);
			}
		}
	}

	/**
	 * copyRect
	 */
	public void copyRect(int ap, int ax, int ay, int w, int h, int bp, int bx, int by) {

		for (int y = 0; y < h; y++) {
			if (!trueY(ay + y))
				continue;
			if (!trueY(by + y))
				continue;
			for (int x = 0; x < w; x++) {
				if (!trueX(ax + x))
					continue;
				if (!trueX(bx + x))
					continue;
				setDataP(bp, bx + x, by + y, map[ap][ay + y][ax + x]);
			}
		}
	}

	/**
	 * clear
	 */
	public void clear(int page, int data) {

		for (int y = 0; y < mapH; y++) {
			for (int x = 0; x < mapW; x++) {
				setDataP(page, x, y, data);
			}
		}
	}

	/**
	 * change
	 */
	public void change(int ap, int ad, int bp, int bd) {

		for (int y = 0; y < mapH; y++) {
			for (int x = 0; x < mapW; x++) {
				if (map[ap][y][x] != ad)
					continue;
				setDataP(bp, x, y, bd);
			}
		}
	}

	/**
	 * change
	 */
	public void change(int ap, int ad, int bp, int bd, int be) {

		for (int y = 0; y < mapH; y++) {
			for (int x = 0; x < mapW; x++) {
				int data = (map[ap][y][x] == ad) ? bd : be;
				setDataP(bp, x, y, data);
			}
		}
	}

	/**
	 * change
	 */
	public void change(int ap, int bp, int[] bd) {

		for (int y = 0; y < mapH; y++) {
			for (int x = 0; x < mapW; x++) {
				int ad = map[ap][y][x];
				if (ad < bd.length) {
					setDataP(bp, x, y, bd[ad]);
				}
			}
		}
	}

	public void setData(int page, int ax, int ay, int[][] data) {
		for (int y = 0; y < data.length; y++) {
			if (!trueY(ay + y))
				continue;
			for (int x = 0; x < data[0].length; x++) {
				if (!trueX(ax + x))
					continue;
				setData(page, ax + x, ay + y, data[y][x]);
			}
		}

	}

	public int[][] getData(int page, int ax, int ay, int w, int h) {
		int[][] data = new int[h][w];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				data[y][x] = getData(page, ax + x, ay + y);
			}
		}
		return data;
	}
}
