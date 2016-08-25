package mine.thread;

import java.util.LinkedList;


/**
 * @author k-saito
 *
 * スレッドを連続して実行するスレッド
 */
public class RelayThread implements Runnable {

	private LinkedList<Runnable> runnerList;

	private Thread thread;
	
	public RelayThread(){
		runnerList = new LinkedList<Runnable>();
	}
	
	public void run(){
		while ( thread == Thread.currentThread() ) {
		
			Runnable runner;
			
			synchronized (runnerList) {
				if ( runnerList.isEmpty() ) {
					break;
				} else {
					runner = (Runnable)runnerList.getFirst();
					runnerList.removeFirst();
				}
			}
			runner.run();
		}
	}
	
	public void add(Runnable runner){
		synchronized(runnerList){
			runnerList.addLast(runner);
		}
	}

	public void stop(){
		thread = null;
	}

	public void start(){
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void join() throws InterruptedException {
		if (thread != null) {
			thread.join();
		}
	}
	
	public boolean isAlive(){
		if (thread != null && thread.isAlive()) {
			return true;
		} else {
			return false;
		}
	}
}
	