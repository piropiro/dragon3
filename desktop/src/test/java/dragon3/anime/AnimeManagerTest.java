package dragon3.anime;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dagger.Module;
import dragon3.DaggerDragonComponent;
import dragon3.DragonComponent;
import dragon3.DragonModule;
import dragon3.Statics;
import dragon3.common.constant.Page;
import dragon3.common.util.MoveUtils;
import dragon3.controller.DragonController;
import dragon3.data.AnimeData;
import dragon3.map.StageMap;
import dragon3.view.DragonFrame;

public class AnimeManagerTest {

	private static DragonComponent og;
	
	@Inject Statics statics;
	
	@Inject StageMap map;
	@Inject AnimeManager am;
	@Inject DragonFrame df;

	@BeforeClass
	public static void setUpClass() throws Exception {
		//og = DaggerDragonTestComponent.builder().build();

		og = DaggerDragonComponent.create();

		DragonController dc = og.getDragonController();
		dc.setup();
		dc.title();
		DragonFrame df = og.getDragonFrame();
		df.launch();
	}

	
	@Module(
		      includes = DragonModule.class
		  )
		  static class TestModule {
		  }

	@Before
	public void setUp() throws Exception {
		//og.inject(this);
		
		map.getMap().setPage(Page.P01, statics.getMapData("D01"));
		map.getMap().setData(Page.P01, 11, 10, MoveUtils.OPEN_MAGIC);
		map.getMap().setData(Page.P20, 10, 10, 1);
		map.getMap().fillDia(Page.P41, 10, 10, 2, 1);
		map.getMap().clear(Page.P02, 1);
		map.getMap().paintStep(Page.P02, Page.P03, 2, 2, 20);
		df.repaint();
	}

	//@Test
	public void testOpenCloseTitle(){
		am.openTitle();
		am.closeTitleOut();
		am.closeTitleIn();
	}

	//@Test
	public void testEraseAnime(){
		am.eraseAnime(10, 10);
	}
	//@Test
	public void testNumberAnime(){
		am.numberAnime(278, 10, 10);
	}
	//@Test
	public void testStatusAnime(){
		am.statusAnime(AnimeManager.STATUS_SLEEP, 10, 10);
	}
	//@Test
	public void testSingleSystemFire(){
		am.singleAnime((AnimeData) am.getData("system.fire"), 10, 10);
	}
	//@Test
	public void testDropTextFinish(){
		am.dropText(AnimeManager.TEXT_FINISH, 10, 10);
	}
	//@Test
	public void testSingleSystemSummon(){
		am.singleAnime((AnimeData) am.getData("system.summon"), 11, 10);
	}
	//@Test
	public void testSummonAnime(){
		am.summonAnime(1, 11, 10);
	}
	//@Test
	public void testWalkAnime(){
		am.walkAnime(10, 10);
	}
	//@Test
	public void testRotateAnime(){
		am.rotateAnime(am.getData("ROTATE.roll_blue"), 10, 10, 11, 11);
	}
	//@Test
	public void testSomeArrowAnime(){
		am.someArrowAnime(am.getData("SINGLE_ARROW.arrow_blue"), 0, 0);
	}
	//@Test
	public void testSingleArrowAnime(){
		am.singleArrowAnime(am.getData("SINGLE_ARROW.arrow"), 10, 10, 12, 12);
	}
	//@Test
	public void testAllSystemFire(){
		am.allAnime(am.getData("system.fire"));
	}
	//@Test
	public void testSingleSystemDeath(){
		am.singleAnime(am.getData("system.death"), 10, 10);
	}
	//@Test
	public void testCriticalAnime(){
		am.criticalAnime(10, 10);
	}
	//@Test
	public void testSlideTextDeath(){
		am.slideText(AnimeManager.TEXT_DEATH, 10, 10);
	}
}
