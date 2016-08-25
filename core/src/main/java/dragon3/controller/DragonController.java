package dragon3.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import card.CardEventListener;
import dragon3.Statics;
import dragon3.anime.AnimeManager;
import dragon3.attack.FightManager;
import dragon3.attack.special.SpecialEffectManager;
import dragon3.camp.Camp;
import dragon3.camp.Equip;
import dragon3.card.CardManager;
import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.cpu.EnemyTurn;
import dragon3.data.StageData;
import dragon3.data.load.BodyDataLoader;
import dragon3.image.ImageManager;
import dragon3.manage.LevelManager;
import dragon3.manage.RewalkManager;
import dragon3.manage.SoulManager;
import dragon3.manage.SummonManager;
import dragon3.manage.TreasureManager;
import dragon3.manage.TurnManager;
import dragon3.map.MapEventListener;
import dragon3.map.MapWorks;
import dragon3.map.StageMap;
import dragon3.paint.BasicPaint;
import dragon3.paint.EventListener;
import dragon3.paint.PaintUtils;
import dragon3.paint.TitlePaint;
import dragon3.panel.PanelManager;
import dragon3.save.SaveManager;
import dragon3.stage.StageBack;
import dragon3.stage.StageManager;
import dragon3.stage.StageSelectEventListener;
import dragon3.view.FrameWorks;
import mine.event.MouseAllListener;
import mine.event.SleepManager;
import mine.paint.MineImageLoader;
import mine.paint.UnitMap;
import mine.util.Point;

@Singleton
public class DragonController implements UnitWorks, MouseAllListener, CommandListener {

	@Inject Statics statics;
	@Inject BodyDataLoader bodyDataLoader;
	
	@Inject StageMap stageMap;
	@Inject FrameWorks fw;
	@Inject MapWorks mw;
	
	@Inject ImageManager imageManager;
	@Inject AnimeManager animeManager;
	@Inject PanelManager panelManager;

	private List<Body> charaList;
	private List<Body> playerList;
	private List<Body> enemyList;

	@Inject MineImageLoader mil;
	@Inject SleepManager sleepManager;

	@Inject CardManager cardManager;
	@Inject TurnManager turnManager;
	@Inject SaveManager saveManager;
	@Inject RewalkManager rewalkManager;
	private Equip equip;
	private Camp camp;
	@Inject TreasureManager treasure;
	@Inject SummonManager summon;
	@Inject SoulManager soulManager;
	@Inject StageManager stageManager;
	@Inject FightManager fightManager;
	@Inject EnemyTurn enemyTurn;
	@Inject SpecialEffectManager se;

	private MouseAllListener mouseListener;

	private boolean escape;

	/*** Constructer *************************************/

	@Inject
	public DragonController(FrameWorks fw) {
		super();
		this.fw = fw;

		charaList = new ArrayList<>();
		// map = new StageMap(imageManager);
		
		//panelManager = new PanelManagerImpl(fw, this, map.getMap(), sleepManager, imageManager);

//		animeManager = panelManager.getAnimeP();
//		mw = panelManager.getMapP();

//		panelManager.setTurnManager(turnManager);
		
//		treasure = new TreasureManagerImpl(this);
//		panelManager.setTreasure(treasure);
//		
//		panelManager.setSummon(summon);
		
		
		
//		cardManager = new CardManager(panelManager.getCardP(), this, map.getMap(), panelManager, sleepManager, imageManager);


//		stageManager = panelManager.getStageSelectP();

	}
	
	public void setup() {
		this.fightManager.setUw(this);
		this.cardManager.setUw(this);
		this.enemyTurn.setUw(this);
		this.rewalkManager.setUw(this);
		this.treasure.setUw(this);
		this.turnManager.setUw(this);
		this.saveManager.setUw(this);
		this.stageManager.setUw(this);
		equip = saveManager.loadData("slgs.dat");
		fw.setMouseListener(this);
	}

	/*** Title ***********************************/

	public void title() {
		fw.setMenu(FrameWorks.T_TITLE);
		animeManager.openTitle();
		setEventListener(new TitlePaint(this));
	}

	/*** Setup ***********************************/

	public void startup() {
		if (isTutorial()) {
			panelManager.setHelpVisible(true);
		}
		campStart();
	}


	/*** Deploy Chara *****************************************/

	public void putUnit(List<Body> v) {
		stageMap.putUnit(v);
	}

	/*** Camp **************************************/

	private void Camp() {
		charaList.clear();
		camp = new Camp(this, treasure, equip);
		stageMap.resetBack(StageBack.WHITE);
		camp.repaint(statics.getCampMap());
		PaintUtils.setCampPaint(this, camp);
//		panelManager.getCardP().setVisible(false);
		mw.repaint();
	}

	/*** Map Load **********************************/


	
	private StageData mapLoad(StageData stageData) {
		
		stageMap.mapLoad(stageData);
		stageMap.setCrystal();
		charaList.clear();
		playerList = equip.getPlayers();
		String stageId = stageManager.getSelectedStage().getId();
		int addLevel = saveManager.getSaveData().getStarNum(stageId) * 5;
		enemyList = this.loadEnemyData(stageData.getId(), addLevel);
		//randomize(Enemys);
		reverse(enemyList);
		treasure.setup(enemyList);
		summon.setup(enemyList);
		soulManager.setup();
		charaList.addAll(enemyList);
		putUnit(enemyList);
		turnManager.reset();
		PaintUtils.setWaitPaint(this);
//		panelManager.getCardP().setVisible(false);
		mw.repaint();
		
		return stageData;
	}



	/*** Deploy End *************************************/

	@Override
	public void finishPutPlayers() {
		stageMap.finishPutPlayers();
		putUnit(charaList);
		turnManager.playerTurnStart();
		panelManager.displayLarge("Turn " + turnManager.getTurn(), GameColor.BLUE, 1500);
	}


	/*** Start *************************************/

	private void stageSelect() {
		fw.setMenu(FrameWorks.T_STAGESELECT);
		panelManager.displayStageSelect(saveManager.getSaveData());
		setMouseListener(new StageSelectEventListener(this, stageManager));
	}
	
	@Override
	public void stageStart(StageData stageData) {
		fw.setMenu(FrameWorks.T_SETMENS);
		mapLoad(stageData);
		panelManager.displayLarge(stageData.getName(), GameColor.BLUE, 1500);
		
//		if (saveManager.isFirst()) {
//			panelManager.displayLarge("Tutorial", GameColor.BLUE, 1500);
//		} else if (saveManager.isFinalStage()) {
//			panelManager.displayLarge("Final Stage", GameColor.BLUE, 1500);
//		} else {
//			char n = (char) ('A' + saveManager.getMapNum() - 1);
//			panelManager.displayLarge("Stage " + n, GameColor.BLUE, 1500);
//		}
		PaintUtils.setPutPlayersPaint(this, charaList, playerList);
		mw.repaint();
		panelManager.closeStageSelect();
		//setEventListener(new BasicPaint(this));
	}

	@Override
	public void campStart() {
		fw.setMenu(FrameWorks.T_CAMP);
		panelManager.closeSmall();
		panelManager.closeHelp();
		panelManager.closeStageSelect();
		Camp();
	}
	
	@Override
	public void enemyTurnStart() {
		turnManager.enemyTurnStart();
		enemyTurn.start(turnManager.getTurn());
	}

	/*** Map Reverse ***********************************/



	private void reverse(List<Body> v) {
		if (!saveManager.getSaveData().getReverse())
			return;
		for (Body b : v) {
			b.setX(19 - b.getX());
			b.setY(14 - b.getY());
			if (b.getGoalX() != 0 || b.getGoalY() != 0) {
				b.setGoalX(19 - b.getGoalX());
				b.setGoalY(14 - b.getGoalY());
			}
		}
	}

	/*** UnitWorks *************************************/

	/*** Die ******************************************************/

	// ba win
	// bb lose
	public void dead(Body ba, Body bb) {
		animeManager.eraseAnime(bb.getX(), bb.getY());

		if (ba == null)
			return;
		if (GameColor.Companion.isPlayer(ba.getColor())) {
			saveManager.getSaveData().countKill();
			equip.getExp(ba, bb);
			treasure.getTreasure(bb, false);
			Body soul = soulManager.getSoul(bb);
			if (soul != null)
				treasure.add(soul);
		}
		if (GameColor.Companion.isPlayer(bb.getColor())) {
			saveManager.getSaveData().countDead();
		}
	}

	public void levelup(Body ba) {
		if (ba != null && GameColor.Companion.isPlayer(ba.getColor())) {
			new LevelManager(equip, panelManager).levelup(ba);
		}
	}

	/*** Escape ************************************/

	public void escape() {
		if (escape) {
			saveManager.getSaveData().countEscape();
			PaintUtils.setWaitPaint(this);
			panelManager.displayLarge("ESCAPE", GameColor.RED, 3000);
			fw.setMenu(FrameWorks.T_CLEAR);
		} else {
			panelManager.displayLarge("FAILED", GameColor.RED, 500);
		}
	}


	/*** End *************************************/

	public void setEnd(Body ba, boolean flag) {
		escape = false;

		if (ba.isAlive()) {
			treasure.searchTreasure(panelManager, ba);
			stageMap.setEnd(ba.getX(), ba.getY());
		}
		mw.repaint();
		if (endJudge(ba))
			return;

		if (isTurnEnd()) {
			enemyTurnStart();
			return;
		}
		BasicPaint bp = new BasicPaint(this);
		setEventListener(bp);
		if (flag)
			bp.leftPressed();
	}

	/*** Turn End ***********************************/

	private boolean isTurnEnd() {
		for (Body b : charaList) {
			if (!b.isAlive())
				continue;
			if (!GameColor.Companion.isPlayer(b.getColor()))
				continue;
			if (b.hasAttr(BodyAttribute.SLEEP))
				continue;
			if (b.hasAttr(BodyAttribute.CHARM))
				continue;
			if (getChangeChara(b) != null)
				return false;
			if (!stageMap.isEnd(b.getX(), b.getY()))
				return false;
		}
		return true;
	}

	/*** End Judge *******************************************/

	public boolean endJudge(Body ba) {
		if (blueJudge1())
			return true;
		if (redJudge1())
			return true;
		if (blueJudge2(ba))
			return true;
		if (redJudge2(ba))
			return true;
		return false;
	}

	/*** Game Clear ***********************************/

	private boolean blueJudge1() {
		for (Body b : charaList) {
			if (!GameColor.Companion.isPlayer(b.getColor())) {
				if (b.isAlive())
					return false;
			}
		}
		stageClear();
		return true;
	}
	private boolean blueJudge2(Body ba) {
		if (!ba.isAlive())
			return false;
		if (ba.getColor() != GameColor.BLUE)
			return false;
		if (!stageMap.crushCrystal(ba.getX(), ba.getY(), GameColor.RED)) {
			return false;
		}
		
		if (GameColor.Companion.isPlayer(ba.getColor()))
			stageClear();
		else
			gameOver();
	
		return true;
	}

	private void stageClear() {
		treasure.getClearItem(panelManager);
		PaintUtils.setWaitPaint(this);
		panelManager.closeData();

		if (stageManager.isFinalStage()) {
			panelManager.displayLarge("ALL CLEAR!!", GameColor.BLUE, 5000);
		} else {
			panelManager.displayLarge("STAGE CLEAR", GameColor.BLUE, 5000);
		}
		saveManager.getSaveData().countStarNum(stageManager.getSelectedStage().getId());
		fw.setMenu(FrameWorks.T_CLEAR);
		panelManager.displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_CLEAR]);
	}

	/*** Game Over ****************************/

	private boolean redJudge1() {
		for (Body b : charaList) {
			if (GameColor.Companion.isPlayer(b.getColor())) {
				if (b.isAlive())
					return false;
			}
		}
		gameOver();
		return true;
	}
	private boolean redJudge2(Body ba) {
		if (!ba.isAlive())
			return false;
		if (ba.getColor() == GameColor.BLUE)
			return false;
		if (!stageMap.crushCrystal(ba.getX(), ba.getY(), GameColor.BLUE)) {
			return false;
		}
		if (GameColor.Companion.isPlayer(ba.getColor()))
			stageClear();
		else
			gameOver();
		return true;
	}

	private void gameOver() {
		PaintUtils.setWaitPaint(this);
		panelManager.displayLarge("GAME OVER", GameColor.RED, 5000);
		fw.setMenu(FrameWorks.T_GAMEOVER);
		panelManager.displayHelp(mw.getWaku(), GameColor.BLUE, Texts.help[Texts.H_OVER]);
	}

	/***************************************************/

	public boolean have(Body b) {
		return equip.have(b);
	}
	public void limitOver() {
		int turn = turnManager.getTurn();
		summon.summon(turn);
		treasure.limitOver();
	}


	public void message() {
		treasure.message(panelManager);
		soulManager.message(panelManager);
	}

	public void addMember(Body b) {
		treasure.addMember(panelManager, b);
	}

	public void bersekChara(Body doll) {
		charaList.remove(doll);
		charaList.add(0, doll);
	}

	public void changeChara(Body before, Body after) {
		charaList.remove(before);
		charaList.add(after);
	}

	public Body getChangeChara(Body before) {
		return equip.getChangeChara(before);
	}

	/*** Score ****************************************/

	private void showScore() {
		panelManager.displayScore(equip, saveManager.getSaveData());

		PaintUtils.setScorePaint(this);
		fw.setMenu(FrameWorks.T_SCORE);
	}

	@Override
	public void backToCamp() {
		panelManager.closeData();
		fw.setMenu(FrameWorks.T_CAMP);
		PaintUtils.setCampPaint(this, camp);
		stageMap.resetBack(StageBack.WHITE);
		camp.repaint(statics.getCampMap());
		mw.repaint();
	}
	
	@Override
	public void backFromImogari() {
		PaintUtils.setCampPaint(this, camp);
	}

	/*** KeyEvent **************************************/

	@Override
	public void executeFKeyCommand(int n, boolean shiftDown) {
//		if (mouseManager.isAlive())
//			return;
		String filename = "slgs" + n + ".dat";
		if (shiftDown) {
			saveManager.saveData(filename, equip);
			panelManager.displayLarge("Save " + n, GameColor.BLUE, 1500);
		} else {
			File f = new File(filename);
			if (!f.exists()) {
				panelManager.displayLarge("Not Found " + n, GameColor.RED, 1500);
				return;
			}
			equip = saveManager.loadData(filename);
			treasure = null;
			animeManager.setVisible(false);
			campStart();
			panelManager.displayLarge("Load " + n, GameColor.BLUE, 1500);
		}
	}

	/*** MenuBar ***************************************/

	@Override
	public void executeMenuCommand(String command) {
		switch (command) {
		case "help":
			if (panelManager.isHelpVisible()) {
				panelManager.displayLarge(Texts.help_off, GameColor.BLUE, 1000);
				panelManager.setHelpVisible(false);
			} else {
				panelManager.displayLarge(Texts.help_on, GameColor.BLUE, 1000);
				panelManager.setHelpVisible(true);
			}
			break;
		case "remove":
			camp.removeDust();
			break;
		case "sort":
			camp.sortItem();
			break;
		case "back":
			backToCamp();
			break;
		case "score":
			showScore();
			break;
		case "camp":
			campStart();
			break;
		case "escape":
			escape();
			break;
		case "stage":
			stageSelect();
			break;
		case "start":
			finishPutPlayers();
			break;
		case "turnend":
			enemyTurnStart();
			break;
		case "mapload":
			equip = saveManager.loadData("slgs.dat");
			treasure.clean();

			if (saveManager.getSaveData().getPlayerName() != null) {
				stageSelect();
			} else {
				title();
			}
			break;
		case "campload":
			equip = saveManager.loadData("slgs.dat");
			treasure.clean();
			
			if (saveManager.getSaveData().getPlayerName() != null) {
				campStart();
			} else {
				title();
			}
			break;
		case "save":
			saveManager.saveData("slgs.dat", equip);
			panelManager.displayLarge("SAVE", GameColor.BLUE, 1500);
		default:
		}
	}

	@Override
	public List<Body> loadEnemyData(String stageId, int addLevel) {

		List<Body> bodyList = bodyDataLoader.loadBodyDataList(stageId, addLevel);
		
		for (Body body : bodyList) {
			body.setImageNum(imageManager.getBodyImageList().getNum(body.base.getImage()));
		}
		return bodyList;
	}
	
	@Override
	public SaveManager getSaveManager() {
		return saveManager;
	}

	@Override
	public TurnManager getTurnManager() {
		return turnManager;
	}

	@Override
	public SleepManager getSleepManager() {
		return sleepManager;
	}

	@Override
	public AnimeManager getAnimeManager() {
		return animeManager;
	}

	@Override
	public PanelManager getPanelManager() {
		return panelManager;
	}

	@Override
	public RewalkManager getRewalkManager() {
		return rewalkManager;
	}

	@Override
	public MapWorks getMapWorks() {
		return mw;
	}

	@Override
	public UnitMap getUnitMap() {
		return stageMap.getMap();
	}

	@Override
	public void displayCardBattle(Body ba, Body bb) {
		cardManager.setup(ba, bb);
		setMouseListener(new CardEventListener(cardManager.getCardCanvas()));
	}
	
	@Override
	public void closeCardBattle(Body ba, Body bb, boolean win) {
		if (win) {
			addMember(bb);
		}
		setEnd(ba, false);
		setEventListener(new BasicPaint(this));
	}
	
	@Override
	public boolean isTutorial() {
		return saveManager.getSaveData().sumStars() == 0;
	}

	@Override
	public Point getCrystal(GameColor color) {
		return stageMap.getCrystal(color);
	}

	@Override
	public FightManager getFightManager() {
		return fightManager;
	}

	@Override
	public StageMap getStageMap() {
		return stageMap;
	}

	@Override
	public void leftPressed(int x, int y) {
		this.mouseListener.leftPressed(x, y);
		
	}

	@Override
	public void rightPressed(int x, int y) {
		this.mouseListener.rightPressed(x, y);
		
	}

	@Override
	public void leftReleased(int x, int y) {
		this.mouseListener.leftReleased(x, y);
		
	}

	@Override
	public void rightReleased(int x, int y) {
		this.mouseListener.rightReleased(x, y);
		
	}

	@Override
	public void leftDragged(int x, int y) {
		this.mouseListener.leftDragged(x, y);
		
	}

	@Override
	public void rightDragged(int x, int y) {
		this.mouseListener.rightDragged(x, y);
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		this.mouseListener.mouseMoved(x, y);
		
	}

	@Override
	public void mouseEntered(int x, int y) {
		this.mouseListener.mouseEntered(x, y);
		
	}

	@Override
	public void mouseExited(int x, int y) {
		this.mouseListener.mouseExited(x, y);
		
	}

	@Override
	public void accept() {
		System.out.println("accept");
		this.mouseListener.accept();
		
	}

	@Override
	public void cancel() {
		System.out.println("cancel");
		this.mouseListener.cancel();
	}

	@Override
	public void setEventListener(EventListener el) {
		System.out.println(el.getClass());
		this.mouseListener = new MapEventListener(stageMap, el);
	}

	@Override
	public void setMouseListener(MouseAllListener mal) {
		System.out.println(mal.getClass());
		this.mouseListener = mal;
	}

	@Override
	public Statics getStatics() {
		return statics;
	}

	@Override
	public SpecialEffectManager getSe() {
		return se;
	}

	@Override
	public List<Body> getCharaList() {
		return charaList;
	}
}
