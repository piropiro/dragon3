package imo;


public interface ImoWorks {
	public void gameStart();
	public void gameEnd(int n);
	public void gameExit();
	public void gameReset(String name, int level);
	public void repaint();
}
