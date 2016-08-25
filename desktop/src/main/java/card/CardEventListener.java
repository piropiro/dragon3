package card;

import mine.event.MouseAllListener;
import mine.thread.Lock;

public class CardEventListener implements MouseAllListener {

	private CardWorks cw;
	private Lock lock;

	
	public CardEventListener(CardWorks cw) {
		this.cw = cw;
		this.lock = cw.getLock();
	}
	

	@Override
	public void leftPressed(int x, int y) {
		mouseMoved(x, y);
		accept();
	}

	@Override
	public void mouseMoved(int x, int y) {
		int wx = x/32 - 4;
		int wy = y/32 - 1;
		
		if (lock.lock()) {
			cw.wakuMove(wx, wy);
			lock.unlock();
		}
	}

	@Override
	public void mouseExited(int x, int y) {
		if (lock.lock()) {
			cw.wakuMove(-1, -1);
			lock.unlock();
		}
	}

	@Override
	public void leftReleased(int x, int y) {}

	@Override
	public void rightPressed(int x, int y) {}

	@Override
	public void rightReleased(int x, int y) {}

	@Override
	public void leftDragged(int x, int y) {}

	@Override
	public void rightDragged(int x, int y) {}

	@Override
	public void mouseEntered(int x, int y) {}

	@Override
	public void accept() {
		if (lock.lock()) {
			cw.accept();
			lock.unlock();
		}
	}

	@Override
	public void cancel() {
	}



}
