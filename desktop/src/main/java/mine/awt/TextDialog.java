package mine.awt;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mine.MineUtils;

@SuppressWarnings("serial")
public class TextDialog extends JDialog implements ActionListener {

	private JLabel label;
	private JTextField text;
	private boolean life;
	private int size;

	/*** Constructer *******************************/

	public TextDialog(Frame f, int size) {
		super(f, true);
		this.size = size;
		initPanel();
	}
	public TextDialog(Frame f) {
		this(f, 15);
	}

	/*** Setup *********************************/

	public void setup(String title, String def) {
		if (MineUtils.INSTANCE.isWindows()) {
			setTitle(title);
		} else {
			setTitle("Java");
			label.setText("@ " + title);
		}
		text.setText(def);
	}

	/*** Show **************************************/

	public void show() {
		life = false;
		pack();
		MineAwtUtils.setCenter(this);
		super.setVisible(true);
	}

	/*** Panel Init ***************************/

	private void initPanel() {
		JButton jb = new JButton("OK");
		jb.addActionListener(this);
		text = new JTextField(size);
		text.addActionListener(this);
		label = new JLabel();
		label.setFont(MineAwtUtils.getFont(12));
		label.setForeground(Color.white);
		GridBagPanel panel = new GridBagPanel();
		panel.setBackground(new Color(0, 0, 150));
		panel.add(label, 1, 1, 2, 1);
		panel.add(text, 1, 2, 1, 1);
		panel.add(jb, 2, 2, 1, 1);
		setContentPane(panel);
	}

	/*** Get Text *****************************/

	public String getText() {
		return text.getText();
	}

	public boolean isOK(){
		return life;
	}

	/*** Press MenuButton **********************************/

	public void actionPerformed(ActionEvent e) {
		life = true;
		dispose();
	}
}
