package dragon3.edit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mine.awt.FileManagerAWT;
import mine.awt.ImageLoaderAWT;
import mine.io.FileManager;
import mine.io.JsonManager;
import mine.paint.MineImageLoader;

@Module
public class EditorModule {
	
	@Provides @Singleton
	MineImageLoader provideMineImageLoader(FileManager fileManager) {
		return new ImageLoaderAWT(fileManager);
	}

	@Provides @Singleton
	FileManager provideFileManager() {
		return new FileManagerAWT();
	}
}