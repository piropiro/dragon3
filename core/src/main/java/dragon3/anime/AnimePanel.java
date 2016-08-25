package dragon3.anime;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dragon3.Statics;
import dragon3.anime.listener.AllAnime;
import dragon3.anime.listener.AnimeListener;
import dragon3.anime.listener.ArrowAnime;
import dragon3.anime.listener.CloseAnime;
import dragon3.anime.listener.CriticalAnime;
import dragon3.anime.listener.DropTextAnime;
import dragon3.anime.listener.EraseAnime;
import dragon3.anime.listener.NumberAnime;
import dragon3.anime.listener.PictureAnime;
import dragon3.anime.listener.RotateAnime;
import dragon3.anime.listener.SingleAnime;
import dragon3.anime.listener.SlideTextAnime;
import dragon3.anime.listener.SomeArrowAnime;
import dragon3.anime.listener.StatusAnime;
import dragon3.anime.listener.SummonAnime;
import dragon3.anime.listener.WalkAnime;
import dragon3.common.DataList;
import dragon3.common.constant.AnimeType;
import dragon3.data.AnimeData;
import dragon3.image.ImageManager;
import dragon3.map.MapWorks;
import dragon3.map.StageMap;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.event.SleepManager;
import mine.paint.MineGraphics;
import mine.paint.MineImage;

@Singleton
public class AnimePanel implements AnimeManager, AnimeWorks, PaintListener {

	private PaintComponent panel;
	
	@Inject SleepManager sm;

	@Inject MapWorks mw;
	
	@Inject StageMap map;

	@Inject ImageManager imageManager;

	private DataList<AnimeData> animeList;

	private AnimeListener np;
	private AnimeListener al;

	@Inject
	public AnimePanel(@Named("animeC") PaintComponent panel, Statics statics) {
		this.panel = panel;
		this.animeList = statics.getAnimeList();

		np = null;
		al = null;
		panel.setPaintListener(this);
	}

	@Override
	public AnimeData getData(String id) {
		return animeList.getData(id);
	}

	/**
	 * Dispose
	 */
	@Override
	public void dispose() {
		np = null;
		al = null;
		setVisible(false);
	}

	/**
	 * タイトル表示アニメーション
	 */
	@Override
	public void openTitle() {
		panel.setBounds(0, 0, 640, 480);
		al = new PictureAnime(imageManager.getImage("title.png"));
		al.animation(this);
		setVisible(true);
	}

	/**
	 * タイトル消去アニメーション１
	 */
	@Override
	public void closeTitleOut() {
		al = new CloseAnime(CloseAnime.OUT, imageManager.getImage("title.png"));
		al.animation(this);
	}

	/**
	 * タイトル消去アニメーション２
	 */
	@Override
	public void closeTitleIn() {
		al = new CloseAnime(CloseAnime.IN, imageManager.getImage("title.png"));
		al.animation(this);
		setVisible(false);
		al = null;
	}

	/**
	 * キャラ消去アニメーション
	 *
	 * @param x
	 * @param y
	 */
	@Override
	public void eraseAnime(int x, int y) {
		panel.setBounds(x * 32, y * 32, 32, 32);
		setVisible(true);
		al = new EraseAnime(mw, map.getMap(), x, y);
		al.animation(this);
		al = null;
	}

	/**
	 * 移動アニメーション
	 *
	 * @param x
	 * @param y
	 */
	@Override
	public void walkAnime(int x, int y) {
		panel.setBounds(x * 32, y * 32, 32, 32);
		np = null;
		al = new WalkAnime(mw, map.getMap(), x, y);
		setVisible(true);
		al.animation(this);
		setVisible(false);
		sm.sleep(15);
		al = null;
	}

	/**
	 * ダメージアニメーション
	 *
	 * @param n
	 * @param x
	 * @param y
	 */
	@Override
	public void numberAnime(int n, int x, int y) {
		panel.setBounds(x * 32, y * 32, 32, 32);
		setVisible(true);
		np = new NumberAnime(n, imageManager.getNum());
		np.animation(this);
		np = null;
	}

	/**
	 * 即死アニメーション
	 *
	 * @param x
	 * @param y
	 */
	@Override
	public void criticalAnime(int x, int y) {
		np = null;
		al = new CriticalAnime(mw, map.getMap(), x, y);
		panel.setBounds(x * 32 - 32, y * 32, 96, 32);
		setVisible(true);
		al.animation(this);
		al = null;
	}

	/**
	 * 落下テキストアニメーション
	 *
	 * @param text
	 * @param x
	 * @param y
	 */
	public void dropText(int text, int x, int y) {
		panel.setBounds(x * 32, y * 32, 32, 32);
		setVisible(true);
		al = new DropTextAnime(text, imageManager.getText());
		al.animation(this);
		al = null;
	}

	/**
	 * スライドテキストアニメーション
	 *
	 * @param text
	 * @param x
	 * @param y
	 */
	@Override
	public void slideText(int text, int x, int y) {
		panel.setBounds(x * 32, y * 32, 32, 32);
		setVisible(true);
		al = new SlideTextAnime(text, imageManager.getText());
		al.animation(this);
		al = null;
	}

	/**
	 * ステータスアニメーション
	 *
	 * @param status
	 * @param x
	 * @param y
	 */
	@Override
	public void statusAnime(int status, int x, int y) {
		panel.setBounds(x * 32, y * 32 - 16, 32, 48);
		setVisible(true);
		al = new StatusAnime(mw, map.getMap(), status, x, y, imageManager.getStatus());
		al.animation(this);
		al = null;
		setVisible(false);
	}

	/**
	 * 召喚アニメーション
	 *
	 * @param image
	 * @param x
	 * @param y
	 */
	@Override
	public void summonAnime(int image, int x, int y) {
		panel.setBounds(x * 32, y * 32 - 32, 32, 56);
		setVisible(true);
		al = new SummonAnime(mw, map.getMap(), image, x, y);
		al.animation(this);
		al = null;
		setVisible(false);
	}

	/**
	 * 単体アニメーション
	 *
	 * @param data
	 * @param x
	 * @param y
	 */
	@Override
	public void singleAnime(AnimeData data, int x, int y) {
		panel.setBounds(x * 32, y * 32, 32, 32);
		setVisible(true);
		MineImage[] image = imageManager.getAnimeImageList().getImage(data.getImage());
		al = new SingleAnime(image, data.getSleep());
		al.animation(this);
		al = null;
	}

	/**
	 * 全体アニメーション
	 *
	 * @param data
	 */
	public void allAnime(AnimeData data) {
		setVisible(true);
		MineImage[] image = imageManager.getAnimeImageList().getImage(data.getImage());
		al = new AllAnime(map.getMap(), image, data.getSleep());
		al.animation(this);
		al = null;
	}

	/**
	 * 単体矢アニメーション
	 *
	 * @param data
	 * @param startX
	 * @param startY
	 * @param goalX
	 * @param goalY
	 */
	@Override
	public void singleArrowAnime(AnimeData data, int startX, int startY, int goalX, int goalY) {
		np = null;
		panel.setBounds(startX * 32, startY * 32, 32, 32);
		setVisible(true);
		MineImage[] image = imageManager.getAnimeImageList().getImage(data.getImage());
		al = new ArrowAnime(image, data.getSleep(), startX * 32, startY * 32, goalX * 32, goalY * 32);
		al.animation(this);
		al = null;
	}

	/**
	 * 複数矢アニメーション
	 *
	 * @param data
	 * @param x
	 * @param y
	 */
	@Override
	public void someArrowAnime(AnimeData data, int x, int y) {
		np = null;
		setVisible(true);
		MineImage[] image = imageManager.getAnimeImageList().getImage(data.getImage());
		al = new SomeArrowAnime(map.getMap(), image, data.getSleep(), x*32, y*32);
		al.animation(this);
		al = null;
	}

	/**
	 * 回転アニメーション
	 *
	 * @param data
	 * @param startX
	 * @param startY
	 * @param goalX
	 * @param goalY
	 */
	@Override
	public void rotateAnime(AnimeData data, int startX, int startY, int goalX, int goalY) {
		np = null;
		setVisible(true);
		MineImage[] image = imageManager.getAnimeImageList().getImage(data.getImage());
		al = new RotateAnime(image, data.getSleep(), startX * 32, startY * 32, goalX * 32, goalY * 32);
		al.animation(this);
		al = null;
	}

	/**
	 * システムアニメーション
	 *
	 * @param id
	 * @param x
	 * @param y
	 */
	@Override
	public void systemAnime(String id, int x, int y) {
		AnimeData animeData = animeList.getData(id);

		if (animeData.getType() == AnimeType.SINGLE) {
			singleAnime(animeData, x, y);
			return;
		}
	}

	/*** Paint *************************************************/
	
	@Override
	public void paint(MineGraphics g) {
		if (np != null)
			np.paint(g);
		if (al != null)
			al.paint(g);
	}


	@Override
	public void sleep(long t) {
		sm.sleep(t);
	}

	@Override
	public void repaint() {
		panel.repaint();
	}

	@Override
	public void setLocation(int x, int y) {
		panel.setLocation(x, y);
	}

	@Override
	public void setSize(int w, int h) {
		panel.setSize(w, h);
	}

	@Override
	public void setVisible(boolean flag) {
		panel.setVisible(flag);
	}
}
