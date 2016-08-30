package rara.dragon3;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dragon3.view.FrameWorks;
import mine.android.FileManagerAND;
import mine.android.ImageLoaderAND;
import mine.android.SleepManagerAND;
import mine.event.SleepManager;
import mine.io.FileManager;
import mine.paint.MineImageLoader;

/**
 * Created by rara on 2016/08/29.
 */
@Module
public class DragonModuleAND {
    private final Application application;

    public DragonModuleAND(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }

    @Provides @Singleton
    FileManager provideFileManager(Context context) {
        return new FileManagerAND(context);
    }

    @Provides @Singleton
    MineImageLoader provideMineImageLoader(Context context) {
        return new ImageLoaderAND(context);
    }

    @Provides @Singleton
    SleepManager provideSleepManager() {
        return new SleepManagerAND();
    }

    @Provides @Singleton
    FrameWorks provideFrameWorks(MainActivity impl) {
        return impl;
    }
}
