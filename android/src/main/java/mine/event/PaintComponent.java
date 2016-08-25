package mine.event;

public interface PaintComponent {
	public void setPaintListener(PaintListener pl);
	
	/**
	 * 再描画フラグを立てる
	 */
	public void update();
	
	public void repaint();
	
	public void repaint(int x, int y, int w, int h);
	
	public void setVisible(boolean flag);
	
	public void setBounds(int x, int y, int w, int h);
	
	public void setLocation(int x, int y);

	public void setSize(int w, int h);
}
