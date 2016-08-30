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
import dragon3.view.DragonFrame;
import dragon3.view.FrameWorks;
import mine.awt.FileManagerAWT;
import mine.awt.ImageLoaderAWT;
import mine.awt.MineCanvasAWT;
import mine.awt.SleepManagerAWT;
import mine.event.MineCanvas;
import mine.event.PaintComponent;
import mine.event.SleepManager;
import mine.io.FileManager;
import mine.paint.MineImageLoader;

@Module
public class DragonModuleAWT {

	@Provides @Singleton
	FileManager provideFileManager() {
		return new FileManagerAWT();
	}

	@Provides @Singleton
	MineImageLoader provideMineImageLoader(FileManager fileManager) {
		return new ImageLoaderAWT(fileManager);
	}
	
	@Provides @Singleton
	SleepManager provideSleepManager() {
		return new SleepManagerAWT();
	}

	@Provides @Singleton
	FrameWorks provideFrameWorks(DragonFrame impl) {
		return impl;
	}
}
