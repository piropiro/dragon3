package dragon3.edit.deploy;

import java.util.List;

import dragon3.data.Data;

public interface MainWorks<D extends Data> {

	/*** BasicPaint ************************/

	public void addUnit(int x, int y);
	public void copyUnit(int x, int y);
	public void removeUnit(int x, int y);

	/*** SortPaint *************************/

	public void moveUnit(int x, int y, List<D> dstData);
	public void moveNextUnit(List<D> dstData);
	public void undoUnit(List<D> dstData);

	/*** GoalPaint ************************/

	public void setGoal(int x, int y);
	public void setBasicPaint();

}