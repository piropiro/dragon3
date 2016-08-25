package mine.event;

public abstract class MouseAllAdapter implements MouseAllListener {
	public void leftPressed(int x, int y){}
	public void rightPressed(int x, int y){}
	public void leftReleased(int x, int y){}
	public void rightReleased(int x, int y){}
	public void leftDragged(int x, int y){}
	public void rightDragged(int x, int y){}
	public void mouseMoved(int x, int y){}
	public void mouseEntered(int x, int y){}
	public void mouseExited(int x, int y){}
}
