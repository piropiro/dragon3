package dragon3.save;

import dragon3.camp.Equip;
import dragon3.controller.UnitWorks;

public interface SaveManager {

	public Equip loadData(String filename);

	public void saveData(String filename, Equip equip);

	/*** DataLoad ******************************/

	public long getPlayTime();

	public SaveData getSaveData();
	
	public void setUw(UnitWorks uw);
}