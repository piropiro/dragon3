package dragon3.edit;

import dagger.Module;
import dagger.Provides;
import mine.awt.ImageLoaderAWT;
import mine.paint.MineImageLoader;

import javax.inject.Singleton;

@Module
public class EditorModule {
	
	@Provides @Singleton
	MineImageLoader provideMineImageLoader() {
		return new ImageLoaderAWT();
	}
}