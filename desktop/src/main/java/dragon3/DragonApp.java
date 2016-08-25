package dragon3;

import dragon3.controller.DragonController;
import dragon3.view.DragonFrame;

public class DragonApp {

	public static void main(String[] args) {
		DragonComponent og = DaggerDragonComponent.builder().build();
		
		DragonFrame fw = og.getDragonFrame();
		
		DragonController dc = og.getDragonController();
		fw.setCommandListener(dc);
		dc.setup();
		dc.title();
		fw.launch();
	}

}
