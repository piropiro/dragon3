package mine.awt;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import mine.event.CommandListener;

/**
 * @author k-saito
 *
 */
@SuppressWarnings("serial")
public class CommandPopupMenu extends JPopupMenu implements ActionListener {

	private Component invoker;

	private List<CommandListener> listeners;

	public CommandPopupMenu(Component invoker) {
		super();
		this.invoker = invoker;
		listeners = new ArrayList<CommandListener>();
	}

	public CommandPopupMenu(Component invoker, CommandListener listener) {
		this(invoker);
		addCommandListener(listener);
	}

	
	public void addCommandListener(CommandListener listener) {
		listeners.add(listener);
	}
	public void removeCommandListener(CommandListener listener) {
		listeners.remove(listener);
	}
	public void removeAllListener(){
		listeners = new ArrayList<CommandListener>();
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
	
	public void show(int x, int y){
		super.show(invoker, x, y);
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
