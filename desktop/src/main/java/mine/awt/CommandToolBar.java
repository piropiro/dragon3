/*
 * 作成日: 2004/03/06
 */
package mine.awt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import mine.event.CommandListener;

/**
 * @author k-saito
 */
@SuppressWarnings("serial")
public class CommandToolBar extends JToolBar implements ActionListener {

	private List<CommandListener> listeners;

	public CommandToolBar() {
		super();
		listeners = new ArrayList<CommandListener>();
	}

	public CommandToolBar(CommandListener listener) {
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


	public JButton newButton(String label, String command, String image) {
		JButton button = new JButton();
		initButton(button, label, command);
		setIcon(button, label, image);
		return button;
	}

	public JButton newButton(String label, String command) {
		JButton button = new JButton(label);
		initButton(button, label, command);
		return button;
	}

	public JToggleButton newToggleButton(String label, String command, String image) {
		JToggleButton button = new JToggleButton();
		initButton(button, label, command);
		setIcon(button, label, image);
		return button;
	}

	public JToggleButton newToggleButton(String label, String command) {
		JToggleButton button = new JToggleButton();
		initButton(button, label, command);
		return button;
	}


	private void initButton(AbstractButton button, String label, String command) {
		button.setFont(MineAwtUtils.getFont(12));
		button.setActionCommand(command);
		button.setToolTipText(label);
		button.setFocusPainted(false);
		button.addActionListener(this);
	}

	private void setIcon(AbstractButton button, String label, String image) {
		try {
			ImageIcon icon = new ImageIcon(ImageLoaderAWT.loadNative(image));
			button.setIcon(icon);
		} catch (RuntimeException e) {
			e.printStackTrace();
			button.setText(label);
		}
	}

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		for (CommandListener listener : listeners) {
			listener.doCommand(command);
		}
	}

}
