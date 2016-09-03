package rara.dragon3;

import android.app.Application;

import dragon3.DragonModule;
import mine.android.ImageLoaderAND;
import mine.android.MineCanvasAND;
import mine.event.MineCanvas;

/**
 * Created by rara on 2016/08/29.
 */
public class AppApplication extends Application {

    private AppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {

        applicationComponent = DaggerAppComponent.builder()
                .dragonModuleAND(new DragonModuleAND(this))
                .build();
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }
}

