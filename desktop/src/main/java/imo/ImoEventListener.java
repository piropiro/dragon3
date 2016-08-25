package imo;


import mine.paint.MineGraphics;



public interface ImoEventListener extends Runnable {
	public void keyPressed(char character, int keyCode);
	public void keyReleased(char character, int keyCode);
	public void paint(MineGraphics g);
}
