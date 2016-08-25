/*
 * Created on 2004/10/11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mine.event;

/**
 * @author saito
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface SleepManager {
	public abstract void sleep(long msec);

	public abstract void speedup();

	public abstract void slowdown();
}