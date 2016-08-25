package mine.edit.component;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;

import mine.MineException;
import mine.MineUtils;

public class EditTextField extends JTextField implements EditComponent {

	private static final long serialVersionUID = 507302028508491347L;

	private String fieldName;
	
	public EditTextField(String fieldName) {
		this.fieldName = fieldName;
		setBackground(Color.white);
		setForeground(Color.black);
	}

	public String getFieldName() {
		return fieldName;
	}

	public Component getComponent() {
		return this;
	}

	public void getData(Object obj) throws MineException {
		MineUtils.INSTANCE.setField(obj, fieldName, getText());
	}

	public void setData(Object obj) throws MineException {
		Object value = MineUtils.INSTANCE.getField(obj, fieldName);
		setText((String)value);
	}

}
