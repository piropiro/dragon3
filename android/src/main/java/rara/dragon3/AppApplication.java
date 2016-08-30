package rara.dragon3;

import android.app.Application;

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
                .appModule(new DragonModuleAND(this))
                .build();
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }
}

