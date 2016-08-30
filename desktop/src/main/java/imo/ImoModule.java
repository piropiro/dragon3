package imo;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mine.awt.FileManagerAWT;
import mine.awt.ImageLoaderAWT;
import mine.awt.KeyManagerAWT;
import mine.awt.SleepManagerAWT;
import mine.event.KeyManager;
import mine.event.MineCanvas;
import mine.event.PaintComponent;
import mine.event.SleepManager;
import mine.paint.MineImageLoader;

@Module
public class ImoModule {

	private MineCanvas mc;
	private PaintComponent imoPanel;
	
	public ImoModule() {
		
		this.mc = new MineCanvas(new ImageLoaderAWT(new FileManagerAWT()));

		imoPanel = mc.newLayer(0, 0, ImoCanvas.WIDTH, ImoCanvas.HEIGHT);
	}
	
	@Provides @Singleton
	MineCanvas provieMineCanvas() {
		return mc;
	}
	
	@Provides @Singleton
	MineImageLoader provideMineImageLoader() {
		return new ImageLoaderAWT(new FileManagerAWT());
	}
	
	@Provides @Singleton
	SleepManager provideSleepManager() {
		return new SleepManagerAWT();
	}

	@Provides @Singleton
	KeyManager provideKeyManager() { return new KeyManagerAWT(); }

	@Provides @Singleton @Named("imoC")
	PaintComponent provideImoC() {
		return imoPanel;
	}
	

	
}
