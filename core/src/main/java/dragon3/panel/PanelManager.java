/*
 * Created on 2005/01/08
 */
package dragon3.panel;

import dragon3.attack.Attack;
import dragon3.camp.Equip;
import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.controller.UnitWorks;
import dragon3.manage.TurnManager;
import dragon3.save.SaveData;
import mine.util.Point;

/**
 * @author saito
 */
public interface PanelManager {
	
	// DataPanel
	public void displayData(TurnManager turnManager, int x, int y);
	public boolean displayPlace(TurnManager turnManager, int x, int y);
	public void displayWazaList(Body b);
	public void displayWaza(Body b, int i);
	public void displayAnalyze(Body b);
	public void displayStatus(Body b);
	public void displayAttack(Attack attack, Attack counter);
	public void displayCampData(int x, int y, int tikei, GameColor bgcolor);
	public void displayScore(Equip equip, SaveData saveData);
	public void repaintData();
	public void closeData();

	// HPPanel
	public void displayHp(boolean hpFlag, Body ba, Body bb, int damage, boolean hit);
	public void damageHp(boolean hpFlag, Body b, int damage);
	public void animeHp(boolean hpFlag);
	public void closeHp();
	
	// MessagePanel
	public void addMessage(String message);
	public void startMessage(Body b);
	
	// SmallPanel
	public void displaySmall(String label, GameColor color, Body b);
	public void closeSmall();
	
	// HelpPanel
	public void displayHelp(Point p, GameColor color, String... line);
	public void setHelpLocation(int x, int y);
	public void setHelpVisible(boolean flag);
	public boolean isHelpVisible();
	public void closeHelp();
	
	// LargePanel
	public void displayLarge(String text, GameColor color, int sleep);

	// StageSelectPanel
	public void displayStageSelect(SaveData saveData);
	public void closeStageSelect();
	
	// CardPanel
	public void displayCardCanvas();
	public void closeCardCanvas();

}
