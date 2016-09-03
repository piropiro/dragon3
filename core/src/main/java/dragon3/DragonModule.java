package dragon3;

import javax.inject.Named;
import javax.inject.Singleton;

import card.CardCanvas;
import dagger.Module;
import dagger.Provides;
import dragon3.anime.AnimeManager;
import dragon3.anime.AnimePanel;
import dragon3.controller.DragonController;
import dragon3.controller.UnitWorks;
import dragon3.image.ImageManager;
import dragon3.manage.SummonManager;
import dragon3.manage.SummonManagerImpl;
import dragon3.manage.TreasureManager;
import dragon3.manage.TreasureManagerImpl;
import dragon3.manage.TurnManager;
import dragon3.manage.TurnManagerImpl;
import dragon3.map.MapPanel;
import dragon3.map.MapWorks;
import dragon3.panel.DataPanel;
import dragon3.panel.HPanel;
import dragon3.panel.HelpPanel;
import dragon3.panel.LargePanel;
import dragon3.panel.MessagePanel;
import dragon3.panel.PanelManager;
import dragon3.panel.PanelManagerImpl;
import dragon3.panel.SmallPanel;
import dragon3.save.SaveManager;
import dragon3.save.SaveManagerImpl;
import dragon3.stage.StageManager;
import dragon3.stage.StageSelectPanel;
import dragon3.view.FrameWorks;
import mine.event.MineCanvas;
import mine.event.PaintComponent;
import mine.event.SleepManager;
import mine.paint.MineImageLoader;

@Module
public class DragonModule {


	@Provides @Singleton
	MineCanvas provideMainC(MineImageLoader mil) {
		return new MineCanvas(mil);
	}
	
	@Provides @Singleton @Named("mapC")
	PaintComponent provideMapC(MineCanvas mc) {
		PaintComponent mapP = mc.newLayer(0, 0, 640, 480);
		mapP.setVisible(true);
		return mapP;
	}
	
	@Provides @Singleton @Named("animeC")
	PaintComponent provideAnimeC(MineCanvas mc) {
		return mc.newLayer(0, 0, 640, 480);
	}
	
	@Provides @Singleton @Named("hpC1")
	PaintComponent proivdeHpC1(MineCanvas mc) {
		return mc.newLayer(0, 0, HPanel.WIDTH, HPanel.HEIGHT);
	}
	
	@Provides @Singleton @Named("hpC2")
	PaintComponent proivdeHpC2(MineCanvas mc) {
		return mc.newLayer(0, 0, HPanel.WIDTH, HPanel.HEIGHT);
	}
	
	@Provides @Singleton @Named("helpC")
	PaintComponent proivdeHelpC(MineCanvas mc) {
		return mc.newLayer(0, 0, HelpPanel.WIDTH, HelpPanel.HEIGHT);
	}
	
	@Provides @Singleton @Named("smallC")
	PaintComponent proivdeSmallC(MineCanvas mc) {
		return mc.newLayer(0, 0, SmallPanel.WIDTH, SmallPanel.HEIGHT);
	}
	
	@Provides @Singleton @Named("largeC")
	PaintComponent proivdeLargeC(MineCanvas mc) {
		return mc.newLayer(0, 0, LargePanel.WIDTH, LargePanel.HEIGHT);
	}
	
	@Provides @Singleton @Named("cardC")
	PaintComponent proivdeCardC(MineCanvas mc) {
		return mc.newLayer(0, 0, CardCanvas.WIDTH, CardCanvas.HEIGHT);
	}
	
	@Provides @Singleton @Named("dataC1")
	PaintComponent proivdeDataC1(MineCanvas mc) {
		return mc.newLayer(0, 0, DataPanel.WIDTH, DataPanel.HEIGHT);
	}
	
	@Provides @Singleton @Named("dataC2")
	PaintComponent proivdeDataC2(MineCanvas mc) {
		return mc.newLayer(0, 0, DataPanel.WIDTH, DataPanel.HEIGHT);
	}
	
	@Provides @Singleton @Named("messageC")
	PaintComponent proivdeMessageC(MineCanvas mc) {
		return mc.newLayer(0, 0, MessagePanel.WIDTH, MessagePanel.HEIGHT);
	}
	
	@Provides @Singleton @Named("stageSelectC")
	PaintComponent provideStageSelectC(MineCanvas mc) {
		return mc.newLayer(0, 0, 640, 480);
	}
	
	@Provides @Singleton @Named("dataP1")
	DataPanel provideDataP1(@Named("dataC1") PaintComponent panel, Statics statics, SleepManager sm, ImageManager im) {
		return new DataPanel(panel, statics, sm, im, true);
	}
	
	@Provides @Singleton @Named("dataP2")
	DataPanel provideDataP2(@Named("dataC2") PaintComponent panel, Statics statics, SleepManager sm, ImageManager im) {
		return new DataPanel(panel, statics, sm, im, false);
	}
	
	@Provides @Singleton @Named("hpP1")
	HPanel provideHpP1(@Named("hpC1") PaintComponent panel, SleepManager sm) {
		return new HPanel(panel, sm, true);
	}
	
	@Provides @Singleton @Named("hpP2")
	HPanel provideHpP2(@Named("hpC2") PaintComponent panel, SleepManager sm) {
		return new HPanel(panel, sm, false);
	}
	


	@Provides @Singleton
	SaveManager provideSaveManager(SaveManagerImpl impl) {
		return impl;
	}
	
	@Provides @Singleton
	SummonManager provideSummonManager(SummonManagerImpl impl) {
		return impl;
	}

	@Provides @Singleton
	TurnManager provideTurnManager(TurnManagerImpl impl) {
		return impl;
	}
	
	@Provides @Singleton
	TreasureManager provideTreasureManager(TreasureManagerImpl impl) {
		return impl;
	}
	
	@Provides @Singleton
	AnimeManager provideAnimeManager(AnimePanel animePanel) {
		return animePanel;
	}
	
	@Provides @Singleton
	PanelManager providePanelManager(PanelManagerImpl impl) {
		return impl;
	}
	
	@Provides @Singleton
	StageManager provideStageManager(StageSelectPanel impl) {
		return impl;
	}
	@Provides @Singleton
	MapWorks provideMapWorks(MapPanel mapPanel) {
		return mapPanel;
	}
	
	@Provides @Singleton
	UnitWorks provideUnitWorks(DragonController impl) {
		return impl;
	}
	
}
