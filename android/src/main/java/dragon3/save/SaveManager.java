package dragon3.save;

import dragon3.camp.Equip;

public interface SaveManager {

	public Equip loadData(String filename);

	public void saveData(String filename, Equip equip);

	/*** DataLoad ******************************/

	public long getPlayTime();

	public SaveData getSaveData();
}