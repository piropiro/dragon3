package dragon3.manage;

import mine.util.Point;
import java.util.List;

import dragon3.common.Body;

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

	public abstract void addMember(Body ba);

	/*** Retrieve ***********************************/

	// flag true  Nakama
	//      false Kill
	public abstract void getTreasure(Body ba, boolean flag);

	/*** Search **********************************/

	public abstract void searchTreasure(Body ba);

	/*** ClearItem ***************************/

	public abstract void getClearItem();

	/*** Message *******************************/

	public abstract void message();

	public abstract void message(Body ba, Body tre);

}