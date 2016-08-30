package card;

public interface CardListener {

	public void repaint();

	public void repaint(int x, int y, int w, int h);

	public void gameExit(int redWin, int blueWin);
}
