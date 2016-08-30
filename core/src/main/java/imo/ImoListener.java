package imo;

public interface ImoListener {
	public void gameExit(int exp);

	public void repaint();

	public void repaint(int x, int y, int w, int h);
}
