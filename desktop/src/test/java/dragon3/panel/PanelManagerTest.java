package dragon3.panel;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dagger.Module;
import dragon3.DaggerDragonComponent;
import dragon3.DragonComponent;
import dragon3.DragonModule;
import dragon3.Statics;
import dragon3.common.Body;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Page;
import dragon3.common.util.MoveUtils;
import dragon3.controller.DragonController;
import dragon3.manage.TurnManager;
import dragon3.map.StageMap;
import dragon3.panel.paint.CampDataPaint;
import dragon3.view.DragonFrame;
import mine.util.Point;

public class PanelManagerTest {

	private static DragonComponent og;
	@Inject PanelManager pm;
	@Inject TurnManager tm;
	@Inject StageMap map;
	@Inject Statics statics;

	@BeforeClass
	public static void setUpClass() throws Exception {
		//og = ObjectGraph.create(new TestModule());

		og = DaggerDragonComponent.builder().build();

		DragonController dc = og.getDragonController();
		DragonFrame df = og.getDragonFrame();
		dc.setup(df);
		dc.title();
		df.launch();
	}
	
	@Module(
		      includes = DragonModule.class
		  )
		  static class TestModule {
		  }
	
//	@BeforeClass
//	public static void setUpClass() throws Exception {
//		ObjectGraph og = ObjectGraph.create(new DragonModule());
//		statics = og.get(Statics.class);
//		
//		ImageManager im = og.get(ImageManager.class);
//		MineImageLoader mil = im.getImageLoader();
//		
//		map = new UnitMap(14, 20, 15, mil);
//		map.setVisible(Page.P00, true);
//		map.setTile(Page.P00, im.getStageBack(), -1);
//		map.setVisible(Page.P01, true);
//		map.setTile(Page.P01, im.getStageObj(), -1);
//		map.setVisible(Page.P20, true);
//		map.setTile(Page.P20, im.getBodyImageList().getImageList(), 0);
//		map.setVisible(Page.P50, true);
//		map.setTile(Page.P50, im.getStatus(), 0);
//		map.setPage(Page.P01, statics.getMapData("D01"));
//		map.setData(Page.P01, 11, 10, MoveUtils.OPEN_MAGIC);
//		map.setData(Page.P20, 10, 10, 1);
//		map.fillDia(Page.P41, 10, 10, 2, 1);
//		map.clear(Page.P02, 1);
//		map.paintStep(Page.P02, Page.P03, 2, 2, 20);
//
//		DragonFrame fw = new DragonFrame();
//		
//		fw.getMapPanel().setPaintListener((g) -> stageMap.draw(g));
//
//		PanelManagerImpl pmi = new PanelManagerImpl(fw, null, map, null, im);
//		pmi.setTurnManager(new TurnManagerMock());
//		pmi.setTreasure(new TreasureManagerMock());
//		pmi.setSummon(new SummonManagerMock());
//		pm = pmi;
//
//		fw.launch();
//		
//	
//	}

	@Before
	public void setUp() throws Exception {
		//og.inject(this);
		
		map.getMap().setPage(Page.P01, statics.getMapData("D01"));
		map.getMap().setData(Page.P01, 11, 10, MoveUtils.OPEN_MAGIC);
		map.getMap().setData(Page.P20, 10, 10, 1);
		map.getMap().fillDia(Page.P41, 10, 10, 2, 1);
		map.getMap().clear(Page.P02, 1);
		map.getMap().paintStep(Page.P02, Page.P03, 2, 2, 20);
	}

	@After
	public void tearDown() throws Exception {
		Thread.sleep(2000);
		pm.setHelpVisible(false);
		pm.closeSmall();
	}

	//@Test
	public void testHelp1() {
		pm.setHelpVisible(true);
		pm.displayHelp(new Point(10, 10), GameColor.BLUE, "Hello, World!", "こんにちわ世界！");
	}

	//@Test
	public void testHelp2() {
		pm.setHelpVisible(true);
		pm.displayHelp(new Point(1, 1), GameColor.RED, "Hello, World!", "こんにちわ世界！");
	}

	//@Test
	public void testLarge() {
		pm.displayLarge("Largeでかい", GameColor.BLUE, 1500);
	}

	//@Test
	public void testSmall1() {
		Body body = new Body();
		body.setX(5);
		body.setY(5);
		pm.displaySmall("攻撃", GameColor.WHITE, body);
	}

	//@Test
	public void testSmall2() {
		Body body = new Body();
		body.setX(10);
		body.setY(10);
		pm.displaySmall("火炎輪", GameColor.RED, body);
	}

	//@Test
	public void testHp1() {
		Body ba = new Body();
		ba.setColor(GameColor.BLUE);
		ba.setX(10);
		ba.setY(10);
		ba.setHp(10);
		ba.setHpMax(20);

		Body bb = new Body();
		bb.setColor(GameColor.RED);
		bb.setX(11);
		bb.setY(11);
		bb.setHp(20);
		bb.setHpMax(30);


		pm.displayHp(true, ba, bb, 5, true);

		pm.displayHp(false, bb, ba, 5, false);

	}

	//@Test
	public void testMessage() {
		Body ba = new Body();
		ba.setColor(GameColor.BLUE);
		ba.setX(10);
		ba.setY(10);
		ba.setHp(10);
		ba.setHpMax(20);
		ba.setImageNum(1);

		pm.addMessage("てすと");
		pm.addMessage("これはテストです。");
		pm.addMessage("dada!");
		pm.addMessage("hoge!");
		pm.addMessage("hoo!");
		pm.startMessage(ba);
	}

	//@Test
	public void testDataData() {
		pm.displayData(tm, 1, 1);
	}

	//@Test
	public void testDataPlace() {
		pm.displayPlace(tm, 1, 1);
	}

	//@Test
	public void testDataWazaList() {
		Body ba = new Body();
		ba.setColor(GameColor.BLUE);
		ba.setX(10);
		ba.setY(10);
		ba.setHp(10);
		ba.setHpMax(20);
		ba.setImageNum(1);

		String[] wazaList = { "slash", "fire" };
		ba.setWazaList(Arrays.asList(wazaList));


		pm.displayWazaList(ba);
	}

	//@Test
	public void testDataAnalyze() {
		Body ba = new Body();
		ba.setColor(GameColor.BLUE);
		ba.setX(10);
		ba.setY(10);
		ba.setHp(10);
		ba.setHpMax(20);
		ba.setImageNum(1);

		pm.displayAnalyze(ba);
	}

	//@Test
	public void testDataStatus() {
		Body ba = new Body();
		ba.setColor(GameColor.BLUE);
		ba.setX(10);
		ba.setY(10);
		ba.setHp(10);
		ba.setHpMax(20);
		ba.setImageNum(1);

		ba.setStr(1);
		ba.setDef(2);
		ba.setMst(3);
		ba.setMdf(4);
		ba.setHit(5);
		ba.setMis(6);

		pm.displayStatus(ba);
	}

	//@Test
	public void testDataCamp() {
		pm.displayCampData(5, 5, CampDataPaint.C_CHARA1, GameColor.BLUE);
	}

	//@Test
	public void testDataWaza() {
		Body ba = new Body();
		ba.setColor(GameColor.BLUE);
		ba.setX(10);
		ba.setY(10);
		ba.setHp(10);
		ba.setHpMax(20);
		ba.setImageNum(1);

		String[] wazaList = { "slash", "fire" };
		ba.setWazaList(Arrays.asList(wazaList));

		pm.displayWaza(ba, 0);
	}
}
