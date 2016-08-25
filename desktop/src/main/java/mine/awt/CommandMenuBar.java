/*
 * 作成日: 2004/03/06
 */
package mine.awt;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import mine.event.CommandListener;

/**
 * @author k-saito
 */
public class CommandMenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;

	private List<CommandListener> listeners;

	public CommandMenuBar() {
		super();
		listeners = new ArrayList<CommandListener>();
	}

	public CommandMenuBar(CommandListener listener) {
		this();
		addCommandListener(listener);
	}

	
	public void addCommandListener(CommandListener listener) {
		listeners.add(listener);
	}
	public void removeCommandListener(CommandListener listener) {
		listeners.remove(listener);
	}
	public void removeAllListener(){
		listeners.clear();
	}

	/**
	 * メニュー作成<p>
	 * 
	 * @param label
	 * @return
	 */
	public JMenu newMenu(String label) {
		JMenu menu = new JMenu(label);
		menu.setFont(MineAwtUtils.getFont(12));
		return menu;
	}

	public JMenu newMenu(String label, int code) {
		JMenu menu = newMenu(label);
		menu.setMnemonic(code);
		return menu;
	}

	/**
	 * アイテム作成<p>
	 * 
	 * @param label
	 * @param command
	 * @param mnemonic
	 * @return
	 */
	public JMenuItem newItem(String label, String command, int mnemonic) {
		JMenuItem item = new JMenuItem(label);
		item.setFont(MineAwtUtils.getFont(12));
		item.setActionCommand(command);
		item.addActionListener(this);
		item.setMnemonic(mnemonic);
		return item;
	}

	public JMenuItem newItem(String label, String command, int mnemonic, int accelerator) {
		JMenuItem item = newItem(label, command, mnemonic);
		item.setAccelerator(KeyStroke.getKeyStroke(accelerator, Event.CTRL_MASK));
		return item;
	}

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		for (CommandListener listener: listeners) {
			listener.doCommand(command);
		}
	}

}
