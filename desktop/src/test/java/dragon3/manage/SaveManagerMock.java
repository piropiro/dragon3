package dragon3.manage;

import dragon3.camp.Equip;
import dragon3.controller.UnitWorks;
import dragon3.save.SaveData;
import dragon3.save.SaveManager;

public class SaveManagerMock implements SaveManager {

	@Override
	public Equip loadData(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveData(String filename, Equip equip) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public long getPlayTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SaveData getSaveData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUw(UnitWorks uw) {
		// TODO Auto-generated method stub
		
	}
}
