package dragon3;

import javax.inject.Singleton;

import dagger.Component;
import dragon3.controller.DragonController;
import dragon3.view.DragonFrame;

@Singleton
@Component(modules = DragonModule.class)
public interface DragonComponent {
	DragonFrame getDragonFrame();
	DragonController getDragonController();
}
