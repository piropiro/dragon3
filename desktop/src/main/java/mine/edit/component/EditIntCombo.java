package mine.edit.component;

import java.awt.Component;

import javax.swing.JComboBox;

import mine.MineException;
import mine.MineUtils;

public class EditIntCombo extends JComboBox<String> implements EditComponent {

	private static final long serialVersionUID = -6432889311808048637L;

	private String fieldName;

	public EditIntCombo(String fieldName) {
		this.fieldName = fieldName;
		addItem("" + 0);
	}

	public void init(int max) {
		removeAllItems();
		for (int i = 0; i <= max; i++) {
			addItem("" + i);
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public Component getComponent() {
		return this;
	}

	public void getData(Object obj) throws MineException {
		MineUtils.INSTANCE.setField(obj, fieldName, new Integer(getSelectedIndex()));
	}

	public void setData(Object obj) throws MineException {
		Object value = MineUtils.INSTANCE.getField(obj, fieldName);
		setSelectedIndex(((Integer)value).intValue());
	}

}
