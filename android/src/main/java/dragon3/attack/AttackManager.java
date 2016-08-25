package dragon3.attack;

import java.util.List;

import dragon3.common.Body;

public interface AttackManager {

	/*** Show ****************************************/

	public abstract void show();

	/*** Counter ***************************************************************/

	// 2-1 Counter Range
	public abstract boolean isCounterable(Body b, boolean flag);

	/*** Active ***************************************************************/

	// 2-1 Attack Range
	// 1-1 Search Enemy
	public abstract boolean isAlive(boolean enemyFlag);

	/*** Select Enemy ************************************************************/

	// 4-0 Over Frame
	public abstract void setTarget(int x, int y);

	/*** Select Enemy *****************************************************/

	public abstract void selectTarget(Body bb_);

	/*** Target Judge *******************************************/

	public abstract boolean isTarget(Body b);

	/*** Control Enemy **************************************************/

	public abstract Body getBestTarget();

	public abstract int getBestDamage();

	public abstract Body getAttacker();

	public abstract Body getReceiver();

	/*** Search Enemy *************************************************/

	// 2-1 Under Frame
	// 1-1 Search Enemy
	public abstract void getAttackUnit(List<Body> charaList);

	/*** Attack *******************************************************/

	public abstract void attack();
	
	public Attack getAttack();
	
	public boolean searchTargets();

}