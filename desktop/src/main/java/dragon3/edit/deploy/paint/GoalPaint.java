package dragon3.edit.deploy.paint;

import dragon3.data.DeployData;
import dragon3.edit.deploy.MainWorks;

public class GoalPaint implements PaintListener {

	private MainWorks<DeployData> mw;

	/**
	 * コンストラクタ<p>
	 *
	 * @param mw
	 */
	public GoalPaint(MainWorks<DeployData> mw) {
		this.mw = mw;
	}


	/* (非 Javadoc)
	 * @see dragon3.edit.enemy.PaintListener#leftPressed(int, int)
	 */
	public void leftPressed(int x, int y) {
		mw.setGoal(x, y);
	}


	/* (非 Javadoc)
	 * @see dragon3.edit.enemy.PaintListener#rightPressed(int, int)
	 */
	public void rightPressed(int x, int y) {
		mw.setBasicPaint();
	}
}
