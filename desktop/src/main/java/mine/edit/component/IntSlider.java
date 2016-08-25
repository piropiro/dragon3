package mine.edit.component;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class IntSlider extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 4447281947419461499L;

	private JLabel label;
	private JSlider slider;
	
	public IntSlider () {
		label = new JLabel(" 00");
		slider = new JSlider();
		setLayout(new BorderLayout());
		add(label, "West");
		add(slider, "Center");
		slider.addChangeListener(this);
	}
	
	public void init(int max) {
		slider.setMaximum(max);
	}

	public void stateChanged(ChangeEvent e) {
		label.setText(new DecimalFormat(" 00").format(slider.getValue()));	
	}
	
	public void setValue(int value) {
		label.setText(new DecimalFormat(" 00").format(slider.getValue()));
		slider.setValue(value);
	}
	
	public int getValue() {
		return slider.getValue();
	}
}