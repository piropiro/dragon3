package dragon3;

import dragon3.controller.DragonController;
import dragon3.view.DragonFrame;
import mine.awt.FileManagerAWT;
import mine.awt.ImageLoaderAWT;
import mine.awt.MineCanvasAWT;

public class DragonApp {

	public static void main(String[] args) {
		DragonComponent og = DaggerDragonComponent.builder().dragonModule(new DragonModule(new MineCanvasAWT(new ImageLoaderAWT(new FileManagerAWT())))).build();
		
		DragonFrame fw = og.getDragonFrame();
		
		DragonController dc = og.getDragonController();
		fw.setCommandListener(dc);
		dc.setup();
		dc.title();
		fw.launch();
	}

}
