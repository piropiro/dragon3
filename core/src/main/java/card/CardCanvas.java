package card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import card.anime.AnimeManager;
import card.body.Card;
import card.body.Enemy;
import card.body.Player;
import card.common.Page;
import card.manage.BattleManager;
import card.manage.CardManager;
import card.manage.DoubleManager;
import card.paint.ResultPainter;
import card.paint.WakuPainter;
import mine.event.PaintComponent;
import mine.event.PaintListener;
import mine.event.SleepManager;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;
import mine.paint.PaintBox;
import mine.thread.Lock;


@Singleton
public class CardCanvas
	implements CardWorks, PaintListener {

	public static final int WIDTH = 32*11;
	public static final int HEIGHT = 32*13;
	
	private PaintComponent panel;
	@Inject SleepManager sleepManager;
	
	private CardListener listener;

	private List<Card> cards;
	@Inject CardManager cardManager;
	@Inject AnimeManager animeManager;

	@Inject BattleManager battleManager;
	@Inject ResultPainter resultPainter;
	@Inject DoubleManager doubleManager;
	@Inject WakuPainter wakuMover;
	private Enemy enemy;
	private Player player;
	@Inject CardMap map;
	private Lock lock;
	private Random random;

	private MineImage blueChara;
	private MineImage redChara;

	private int[] blueNum;
	private int[] redNum;

	@Inject
	public CardCanvas(@Named("cardC") PaintComponent panel, MineImageLoader imageLoader){
		super();
		this.panel = panel;
		panel.setLocation(32 * 4, 32 * 1);
		//ImageList il = new ImageList(imageLoader);
		cards = new ArrayList<Card>();
		//MineAwtUtils.setSize(this, WIDTH, HEIGHT);

		//initMap(il);
		//wakuMover = new WakuPainter(this, map);
		//animeManager = new AnimeManager(this, map, il);
		//cardManager = new CardManager(this, animeManager, il);
		//battleManager = new BattleManager(animeManager);
		//doubleManager = new DoubleManager(cardManager, animeManager);
		//resultPainter = new ResultPainter();

		lock = new Lock();
		random = new Random();
		panel.setPaintListener(this);
	}

	public void setCardListener(CardListener listener) {
		this.listener = listener;
	}

//	private void initMap(ImageList il){
//		map = new UnitMap(3, 11, 13, il.getImageLoader());
//		map.setTile(Page.BACK, il.getBack(), -1);
//		map.setTile(Page.WAKU, il.getWaku(), 0);
//		map.clear(Page.BACK, -1);
//		map.clear(Page.CHARA, -1);
//		map.clear(Page.WAKU, 0);
//		map.setVisible(0, true);
//		map.setVisible(1, true);
//		map.setVisible(2, true);
//	}

	private void initialize(){
		battleManager.initialize();
		doubleManager.initialize();
		enemy = new Enemy(this, battleManager, cardManager);
		player = new Player(cardManager);

		cards.removeAll(cards);
		cardManager.setBlueCards(this, blueNum);
		cardManager.setRedCards(this, redNum);
		map.getMap().setTile(Page.CHARA, new MineImage[]{blueChara, redChara}, -1);
	}

	public void setBlueChara(MineImage blueChara, int[] blueNum){
		this.blueChara = blueChara;
		this.blueNum = blueNum;
	}

	public void setRedChara(MineImage redChara, int[] redNum){
		this.redChara = redChara;
		this.redNum = redNum;
	}

	public void addCard(Card card){
		synchronized (cards) {
			cards.add(card);
		}
	}
	public void removeCard(Card card){
		synchronized (cards) {
			cards.remove(card);
		}
	}

	@Override
	public void paint(MineGraphics g){
		map.getMap().draw(g);
		synchronized (cards) {
			for (Card card : cards) {
				card.paint(g);
			}
		}
		resultPainter.paint(g);
	}

	public void setVisible(boolean flag) {
		panel.setVisible(flag);
	}
	public void repaint() {
		panel.update();
		listener.repaint();
	}
	
	public void repaint(PaintBox box){
		panel.update();
		listener.repaint(box.getX(), box.getY(), box.getW(), box.getH());
	}

	public void sleep(long msec){
		sleepManager.sleep(msec);
	}

	public int nextInt(int max) {
		return random.nextInt(max);
	}

	public void start(){
		lock.lock();
		initialize();
		animeManager.opening(this, cardManager.getRedCards(), cardManager.getBlueCards());
		enemy.openCard(this);
		enemy.openCard(this);
		enemy.openCard(this);
		lock.unlock();
	}

	public void dispose(){
		lock.lock();
		animeManager.closing(this, (Card[])cards.toArray(new Card[0]));
		resultPainter.setResult(ResultPainter.NONE);
		repaint();
		lock.unlock();
	}


	private void startBattle(int n){
		Card red = enemy.selectCard();
		Card blue = player.selectCard(n);
		battleManager.startBattle(this, red, blue);
		if (battleManager.isEnd()) {
			gameset();
		} else if (!player.hasCard()){
			battleManager.retireBlue();
			gameset();
		} else {
			enemy.openCard(this);
		}
	}

	private void gameset(){
		int redWin = battleManager.getRedWin();
		int blueWin = battleManager.getBlueWin();
		if (redWin > blueWin) {
			resultPainter.setResult(ResultPainter.LOSE);
		} else if ( redWin < blueWin ) {
			resultPainter.setResult(ResultPainter.WIN);
		} else {
			resultPainter.setResult(ResultPainter.DRAW);
		}

		repaint();
		sleep(1000);
		listener.gameExit(redWin, blueWin);
	}

	public void wakuMove(int x, int y) {
		if (wakuMover.isMoved(x, y)) {
			wakuMover.moveWaku(this, x, y);
		}
	}

	public void accept() {
		int x = wakuMover.getX();
		int y = wakuMover.getY();

		if ( 2 <= x && x <= 8 && y == 8) {
			int n = x - 2;
			if (cardManager.isOpenedBlue(n)) {
				startBattle(n);
			} else {
				player.openCard(this, n);
			}
		} else if ( x == 3 && y == 10) {
			if (doubleManager.clickDoubleCard(this)) {
				player.doubleCard();
			}
		}
		doubleManager.checkDoubleCard(this);
	}

	@Override
	public Lock getLock() {
		return lock;
	}
}
