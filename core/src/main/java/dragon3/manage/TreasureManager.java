package dragon3.manage;

import java.util.List;

import dragon3.common.Body;
import dragon3.controller.UnitWorks;
import dragon3.panel.PanelManager;
import mine.util.Point;

public interface TreasureManager {

	public void clean();
	
	public void setup(List<Body> Charas);
	
	/*** Data *******************************/

	public abstract int getLimitTurn(Point p);

	public abstract int getLimitTurn();

	public abstract String getCount();

	public abstract List<Body> getSources();

	public abstract void add(Body ba);

	/*** LimitOver ****************************/

	public abstract void limitOver();

	/*** Add Member ******************************/

	public abstract void addMember(PanelManager pm, Body ba);

	/*** Retrieve ***********************************/

	// flag true  Nakama
	//      false Kill
	public abstract void getTreasure(Body ba, boolean flag);

	/*** Search **********************************/

	public abstract void searchTreasure(PanelManager pm, Body ba);

	/*** ClearItem ***************************/

	public abstract void getClearItem(PanelManager pm);

	/*** Message *******************************/

	public abstract void message(PanelManager pm);

	public abstract void message(PanelManager pm, Body ba, Body tre);
	
	public void setUw(UnitWorks uw);

}