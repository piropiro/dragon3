package dragon3;

import dragon3.controller.DragonController;
import dragon3.view.DragonFrame;
import mine.awt.FileManagerAWT;
import mine.awt.ImageLoaderAWT;
import mine.event.MineCanvas;

public class DragonApp {

	public static void main(String[] args) {
		DragonComponent og = DaggerDragonComponent.builder().dragonModule(new DragonModule(new MineCanvas(new ImageLoaderAWT(new FileManagerAWT())))).build();
		
		DragonFrame fw = og.getDragonFrame();
		
		DragonController dc = og.getDragonController();
		fw.setCommandListener(dc);
		dc.setup(fw);
		dc.title();
		fw.launch();
	}

}
