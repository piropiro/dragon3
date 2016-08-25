package dragon3.edit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mine.awt.ImageLoaderAWT;
import mine.paint.MineImageLoader;

@Module
public class EditorModule {
	
	@Provides @Singleton
	MineImageLoader provideMineImageLoader() {
		return new ImageLoaderAWT();
	}
}