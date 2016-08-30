/*
 * Created on 2005/01/10
 */
package dragon3.manage;

import dragon3.controller.UnitWorks;

/**
 * @author saito
 */
public interface TurnManager {

	public void reset();

	public int getTurn();

	public void turnChange(boolean flag);


	public void setUw(UnitWorks uw);
}