package dragon3.manage;

import java.util.List;

import dragon3.common.Body;
import mine.util.Point;

public interface SummonManager {

	public void clean();
	
	public void setup(List<Body> Charas);
	
	/*** Limit ******************************/

	public abstract int getLimitTurn(Point p);

	/*** Summon ******************************/

	public abstract void summon();

}