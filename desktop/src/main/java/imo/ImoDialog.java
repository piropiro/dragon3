package imo;

import javax.swing.JDialog;

import mine.awt.MineAwtUtils;

public class ImoDialog extends JDialog implements ImoListener {

	private static final long serialVersionUID = -7591068665219456599L;

	private boolean life;
	private int exp;
	private ImoGari imoGari;
	private int level = 1;

	public static void main(String[] args){
		ImoDialog id = new ImoDialog("俺", 1);
		id.pack();
		MineAwtUtils.setCenter(id);
		id.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		id.setVisible(true);
	}

	public ImoDialog(String name, int level) {
		super();
		setTitle("ImoGari");
		imoGari = new ImoGari(this, name, level);
		getContentPane().add(imoGari.getCanvas());
	}

	public void gameExit(int exp_) {
		this.life = true;
		this.exp = exp_;
		if ( exp > 0) level++;
		imoGari.gameReset("俺", level);
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLife() {
		return life;
	}

	public void setLife(boolean life) {
		this.life = life;
	}
}
