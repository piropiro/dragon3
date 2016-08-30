package rara.dragon3;

import javax.inject.Singleton;

import dagger.Component;
import dragon3.DragonModule;
import dragon3.controller.DragonController;

/**
 * Created by rara on 2016/08/29.
 */

@Singleton
@Component(modules = {DragonModuleAND.class, DragonModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    DragonController getDragonController();
}
