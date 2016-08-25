package mine.thread;


public class Lock {

	private volatile boolean lock;
	
	public Lock(){
		lock = false;
	}
	
	public synchronized boolean lock(){
		if (lock) {
			return false;
		} else {
			lock = true;
			return true;
		}
	}
	
	public synchronized void unlock(){
		lock = false;
	}
}
