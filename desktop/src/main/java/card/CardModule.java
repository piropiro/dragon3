package card;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mine.awt.FileManagerAWT;
import mine.awt.ImageLoaderAWT;
import mine.awt.SleepManagerAWT;
import mine.event.MineCanvas;
import mine.event.PaintComponent;
import mine.event.SleepManager;
import mine.io.FileManager;
import mine.paint.MineImageLoader;

@Module
public class CardModule {

	private MineCanvas mc;
	private PaintComponent cardPanel;
	
	public CardModule() {
		
		this.mc = new MineCanvas(new ImageLoaderAWT(new FileManagerAWT()));

		cardPanel = mc.newLayer(0, 0, CardCanvas.WIDTH, CardCanvas.HEIGHT);
	}
	
	@Provides @Singleton
	MineCanvas provieMineCanvas() {
		return mc;
	}
	
	@Provides @Singleton
	MineImageLoader provideMineImageLoader(FileManager fileManager) {
		return new ImageLoaderAWT(fileManager);
	}

	@Provides @Singleton
	FileManager provideFileManager() { return new FileManagerAWT(); }

	@Provides @Singleton
	SleepManager provideSleepManager() {
		return new SleepManagerAWT();
	}
	
	@Provides @Singleton @Named("cardC")
	PaintComponent provideCardC() {
		return cardPanel;
	}
	

	
}
