package mine.android;

import mine.event.SleepManager;

public class SleepManagerAND implements SleepManager {

	private boolean quick_flag;
	
	public SleepManagerAND() {
		
	}


	public void sleep(long msec){
		try {
			if (quick_flag) {
				msec = msec / 3;
			}
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void speedup(){
		quick_flag = true;
	}
	public void slowdown(){
		quick_flag = false;
	}
}
