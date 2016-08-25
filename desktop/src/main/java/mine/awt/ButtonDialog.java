package mine.awt;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

@SuppressWarnings("serial")
public class ButtonDialog extends JDialog implements ActionListener {

	private GridBagPanel panel;
	private ActionListener al;
	private int n;

	/*** Constructer *******************************/

	public ButtonDialog(Frame f, ActionListener al) {
		super(f, true);
		this.al = al;
		panel = new GridBagPanel();
		setContentPane(panel);
		n = 0;
	}

	/*** Show *****************************************/

	public void show() {
		pack();
		MineAwtUtils.setCenter(this);
		super.setVisible(true);
	}

	/*** Add *****************************************/

	public void add(String name, String command) {
		JButton jb = new JButton(name);
		jb.setActionCommand(command);
		jb.addActionListener(al);
		jb.addActionListener(this);
		panel.add(jb, n % 2, n / 2, 1, 1);
		n++;
	}

	public void add(String name) {
		add(name, name);
	}

	/*** Dispose when button pressed  ************************/

	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}
