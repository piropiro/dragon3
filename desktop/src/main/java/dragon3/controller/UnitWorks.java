package dragon3.controller;

import java.util.List;

import dragon3.Statics;
import dragon3.anime.AnimeManager;
import dragon3.attack.FightManager;
import dragon3.attack.special.SpecialEffectManager;
import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.data.StageData;
import dragon3.manage.RewalkManager;
import dragon3.manage.TurnManager;
import dragon3.map.MapWorks;
import dragon3.map.StageMap;
import dragon3.paint.EventListener;
import dragon3.panel.PanelManager;
import dragon3.save.SaveManager;
import dragon3.view.FrameWorks;
import mine.event.MouseAllListener;
import mine.event.SleepManager;
import mine.paint.UnitMap;
import mine.util.Point;

public interface UnitWorks {

	/*** Main ************************/

	public void title();
	public void finishPutPlayers();
	public void escape();
	public boolean endJudge(Body b);
	public Point getCrystal(GameColor color);
	public void changeChara(Body before, Body after);
	public Body getChangeChara(Body before);
	public void bersekChara(Body doll);
	public void startup();
	public void backToCamp();
	public void backFromImogari();
	public List<Body> loadEnemyData(String file, int addLevel);
	public void stageStart(StageData stageData);
	public void campStart();
	public void enemyTurnStart();
	
	/*** Main2 **************************/

	public void dead(Body ba, Body bb);
	public void levelup(Body ba);
	public void setEnd(Body b, boolean flag);
	public void putUnit(List<Body> vec);


	public boolean have(Body b);
	public void limitOver();

	public void addMember(Body b);
	public void message();

	public TurnManager getTurnManager();
	public SaveManager getSaveManager();
	public SleepManager getSleepManager();
	public AnimeManager getAnimeManager();
	public PanelManager getPanelManager();
	public RewalkManager getRewalkManager();
	public FightManager getFightManager();
	public MapWorks getMapWorks();
	public UnitMap getUnitMap();
	public List<Body> getCharaList();
	public StageMap getStageMap();
	public Statics getStatics();
	public SpecialEffectManager getSe();

	// CardPanel
	public void displayCardBattle(Body ba, Body bb);
	public void closeCardBattle(Body ba, Body bb, boolean win);
	
	public boolean isTutorial();

	public void setEventListener(EventListener el);
    public void setMouseListener(MouseAllListener mal);
}
