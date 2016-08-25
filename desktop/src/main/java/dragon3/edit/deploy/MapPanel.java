package dragon3.edit.deploy;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dragon3.edit.deploy.paint.PaintListener;
import mine.awt.GraphicsAWT;
import mine.awt.MineAwtUtils;
import mine.paint.UnitMap;

class MapPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private PaintListener listener;
	private UnitMap map;

	/*** コンストラクタ **********************************************/

	MapPanel(UnitMap map) {
		super();
		this.map = map;
		addMouseListener(this);
		MineAwtUtils.setSize(this, 640, 480);
	}

	/*** リスナー登録 ************************************************/

	public void setMouseListener(PaintListener listener) {
		this.listener = listener;
	}

	/*** 描画 ********************************************************/

	public void paintComponent(Graphics g) {
		map.draw(new GraphicsAWT(g));
	}


	public void mousePressed(MouseEvent e) {
		int x = e.getX() / map.getTileWidth();
		int y = e.getY() / map.getTileHeight();

		if (SwingUtilities.isLeftMouseButton(e)) {
			listener.leftPressed(x, y);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			listener.rightPressed(x, y);
		}
	}

	public void mouseReleased(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
}
