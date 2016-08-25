package dragon3.stage;

import dragon3.controller.UnitWorks;
import dragon3.data.StageData;

public interface StageManager {
	
	public boolean isFinalStage();
	
	public void selectStage(int x, int y);
	
	public StageData getSelectedStage();
	
	public void wakuPaint(int wx, int wy, boolean flag);
	
	public void setUw(UnitWorks uw);
	
	
}
