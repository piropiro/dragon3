package mine.edit.component;

import java.awt.Component;

import mine.MineException;
import mine.MineUtils;

public class EditTextCombo extends TextCombo implements EditComponent {

	private static final long serialVersionUID = -6432889311808048637L;

	private String fieldName;
	
	public EditTextCombo(String fieldName) {
		this.fieldName = fieldName;
		super.init(new String[]{"none"}, new String[]{"none"});
	}

	public String getFieldName() {
		return fieldName;
	}

	public Component getComponent() {
		return this;
	}

	public void getData(Object obj) throws MineException {
		MineUtils.INSTANCE.setField(obj, fieldName, getValue());
	}

	public void setData(Object obj) throws MineException {
		Object value = MineUtils.INSTANCE.getField(obj, fieldName);
		setValue((String)value);
	}

}
