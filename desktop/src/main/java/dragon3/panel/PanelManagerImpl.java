package dragon3.panel;

import javax.inject.Inject;
import javax.inject.Named;

import card.CardCanvas;
import dragon3.Statics;
import dragon3.anime.AnimePanel;
import dragon3.attack.Attack;
import dragon3.camp.Equip;
import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.util.MoveUtils;
import dragon3.controller.UnitWorks;
import dragon3.manage.SummonManager;
import dragon3.manage.TreasureManager;
import dragon3.manage.TurnManager;
import dragon3.map.MapPanel;
import dragon3.map.StageMap;
import dragon3.save.SaveData;
import dragon3.stage.StageSelectPanel;
import dragon3.view.FrameWorks;
import lombok.Getter;
import lombok.Setter;
import mine.util.Point;

/**
 * @author saito
 */
public class PanelManagerImpl implements PanelManager {

	@Inject Statics statics;
	
	@Inject FrameWorks fw;
	
	@Inject AnimePanel animeP;
	@Inject @Getter MapPanel mapP;
	@Inject @Getter StageSelectPanel stageSelectP;
	@Inject @Getter CardCanvas cardP;
	@Inject @Named("dataP1") DataPanel dataP1;
	@Inject @Named("dataP2") DataPanel dataP2;
	@Inject @Named("hpP1") HPanel hpP1;
	@Inject @Named("hpP2") HPanel hpP2;
	@Inject HelpPanel helpP;
	@Inject LargePanel largeP;
	@Inject MessagePanel messageP;
	@Inject SmallPanel smallP;
	
	@Inject TreasureManager treasureManager;
	@Inject SummonManager summonManager;
	
	@Inject StageMap map;
	
	@Setter private UnitWorks uw;
	
	private boolean helpVisible;

	@Inject
	public PanelManagerImpl() {
//		this.fw = fw;
//		this.map = map;
		
		
		// MapPanel
		//mapP = new MapPanel(fw.getMapPanel(), uw, map);

		
		// AnimePanel
		//animeP = new AnimePanel(fw.getAnimePanel(), statics);

		// StageSelectPanel
//		stageSelectP = new StageSelectPanel(fw.getStageSelectPanel(), statics, imageManager);
//		stageSelectP.setVisible(false);
		
		// HPanel
//		hpP1 = new HPanel(fw.getHPanel1(), sleepManager, true);
//		hpP2 = new HPanel(fw.getHPanel2(), sleepManager, false);
		
		// DataPanel
//		dataP1 = new DataPanel(fw.getDataPanel1(), sleepManager, imageManager, true);
//		dataP2 = new DataPanel(fw.getDataPanel2(), sleepManager, imageManager, false);
		
		// HelpPanel
//		helpP = new HelpPanel(fw.getHelpPanel());
		
		// SmallPanel
//		smallP = new SmallPanel(fw.getSmallPanel());
		
		// LargePanel
//		largeP = new LargePanel(fw.getLargePanel());
		
		// MessagePanel
//		messageP = new MessagePanel(fw.getMessagePanel());
		
		// CardPanel
//		cardP = new CardCanvas(fw.getCardPanel(), imageManager.getImageLoader(), sleepManager);
//		cardP.setVisible(false);
	}



	public void displayData(TurnManager turnManager, int x, int y) {
		int tikei = map.getMap().getData(Page.P01, x, y);
		if (tikei == MoveUtils.WHITE) {
			dataP1.displayData(new Point(x, y), turnManager.getTurn(), treasureManager.getLimitTurn(), treasureManager.getCount());
		} else {
			dataP1.displayPlace(new Point(x, y), tikei);
		}
	}

	public boolean displayPlace(TurnManager turnManager, int x, int y) {
		int tikei = map.getMap().getData(Page.P01, x, y);
		Point p = new Point(x, y);
		switch (tikei) {
			case MoveUtils.C_BLUE :
			case MoveUtils.C_RED :
			case MoveUtils.S_BLUE :
				dataP1.displayPlace(p, tikei);
				return true;
			case MoveUtils.CLOSE_BOX :
			case MoveUtils.OPEN_BOX :
			case MoveUtils.BROKEN_BOX :
				dataP1.displayItem(
						p, 
						turnManager.getTurn(), 
						treasureManager.getLimitTurn(p), 
						tikei);
				return true;
			case MoveUtils.CLOSE_MAGIC :
			case MoveUtils.OPEN_MAGIC :
				dataP1.displaySummon(p, turnManager.getTurn(), summonManager.getLimitTurn(p), tikei);
				return true;
			default :
				dataP1.setVisible(false);
				return false;
		}
	}
	public void displayCampData(int x, int y, int tikei, GameColor bgcolor) {
		dataP1.displayCamp(new Point(x, y), tikei, bgcolor);
	}

	public void displayStatus(Body b) {
		dataP1.displayStatus(b);
	}
	public void displayAnalyze(Body b) {
		dataP1.displayNext(b);
	}
	public void displayWazaList(Body b) {
		dataP1.displayWazaList(b);
	}
	public void displayWaza(Body b, int i) {
		dataP1.displayWaza(b, i);
	}
	
	public void displayScore(Equip equip, SaveData sd) {
		dataP1.displayScore1(sd);
		dataP2.displayScore2(equip, sd);
	}
	public void repaintData() {
		dataP1.repaint();
		dataP2.repaint();
	}
	public void displayAttack(Attack attack, Attack counter) {
		displayAttackInternal(dataP1, attack, counter);
		displayAttackInternal(dataP2, counter, attack);
	}
	
	private void displayAttackInternal(DataPanel dp, Attack attack, Attack counter) {
		if (attack == null) {
			if (counter == null) {
				dp.setVisible(false);
			} else {
				dp.displayCounter(counter);
			}
		} else {
			dp.displayAttack(attack, counter);
		}
	}

	public void closeData() {
		dataP1.setVisible(false);
		dataP2.setVisible(false);
	}

	/*** HPanel ************************************************/

	public void displayHp(boolean hpFlag, Body ba, Body bb, int damage, boolean hit) {
		if (hpFlag) {
			hpP1.display(ba, bb, damage, hit);
		} else {
			hpP2.display(ba, bb, damage, hit);
		}
	}

	public void damageHp(boolean hpFlag, Body b, int damage) {
		if (hpFlag) {
			dataP2.damage(b, damage);
			hpP1.damage(damage);
		} else {
			dataP1.damage(b, damage);
			hpP2.damage(damage);
		}
	}

	public void animeHp(boolean hpFlag) {
		if (hpFlag) {
			hpP1.henka();
			dataP2.henka();
		} else {
			hpP2.henka();
			dataP1.henka();
		}
	}

	public void closeHp() {
		hpP1.setVisible(false);
		hpP2.setVisible(false);
	}
	
	// MPanel
	public void addMessage(String message) {
		messageP.addMessage(message);
	}

	public void startMessage(Body b) {
		messageP.display(b);
	}
	
	// SmallPanel
	@Override
	public void displaySmall(String label, GameColor color, Body b) {
		smallP.display(label, color, b.getX(), b.getY());
	}
	
	public void closeSmall() {
		smallP.setVisible(false);
	}	
	
	// HelpPanel
	@Override
	public void displayHelp(Point p, GameColor color, String... line) {
		helpP.setLocate(p.x, p.y, true);
		helpP.setLine(color, line);
		helpP.setVisible(helpVisible);
		helpP.repaint();
	}
	@Override
	public void setHelpLocation(int x, int y) {
		helpP.setLocate(x, y, false);
	}
	@Override
	public void setHelpVisible(boolean helpVisible){
		this.helpVisible = helpVisible;
		helpP.setVisible(helpVisible);
	}
	@Override
	public boolean isHelpVisible(){
		return helpVisible;
	}
	@Override
	public void closeHelp(){
		helpP.setVisible(false);
	}

	// LargePanel
	@Override
	public void displayLarge(String text, GameColor color, int sleep) {
		largeP.display(text, color, sleep);
	}
	
	// StageSelectPanel
	@Override
	public void displayStageSelect(SaveData saveData) {
		stageSelectP.updateStageStatus(saveData);
		stageSelectP.setVisible(true);
	}
	
	@Override
	public void closeStageSelect() {
		stageSelectP.setVisible(false);
		//fw.setMouseListener(mapP);
	}

	@Override
	public void displayCardCanvas() {
		cardP.setVisible(true);
		cardP.start();
		//fw.setMouseListener(cardP);
	}

	@Override
	public void closeCardCanvas() {
		cardP.dispose();
		cardP.setVisible(false);
		//fw.setMouseListener(mapP);
	}
	

	
	
}
