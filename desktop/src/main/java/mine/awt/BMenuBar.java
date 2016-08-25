package mine.awt;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class BMenuBar extends JMenuBar {

	private ActionListener al;
	private JMenu menu;

	/*** Constructer ***************************/

	public BMenuBar() {
		super();
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		fl.setVgap(2);
		setLayout(fl);
	}

	/*** Reset *********************************/

	public void reset(ActionListener new_al) {
		this.al = new_al;
		removeAll();
	}

	/*** Add *************************************/

	public void add(String name, String command, int code) {
		JMenuItem m = new JMenuItem(name);
		m.setActionCommand(command);
		m.addActionListener(al);
		m.setAccelerator(KeyStroke.getKeyStroke(code, 0));
		add(m);
	}

	public void add(String name, int code) {
		add(name, name, code);
	}

	public void addMenu(String name, char code) {
		menu = new JMenu(name);
		menu.setMnemonic(code);
		add(menu);
	}

	public void addItem(String name, String command, int code) {
		JMenuItem m = new JMenuItem(name);
		m.setActionCommand(command);
		m.addActionListener(al);
		m.setAccelerator(KeyStroke.getKeyStroke(code, 0));
		menu.add(m);
	}
	public void addItem(String name, int code) {
		addItem(name, name, code);
	}
}
