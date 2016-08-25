package mine.edit.component;

import mine.MineException;
import mine.MineUtils;

public class EditTextFieldArray extends EditTextField implements EditComponent {

	private static final long serialVersionUID = -6432889311808048637L;
	
	private int index;
	
	public EditTextFieldArray(String fieldName, int index) {
		super(fieldName);
		this.index = index;
	}
	
	@Override
	public void getData(Object obj) throws MineException {
		String fieldName = super.getFieldName();
		String[] strs = (String[])MineUtils.INSTANCE.getField(obj, fieldName);
		strs[index] = getText();
		MineUtils.INSTANCE.setField(obj, fieldName, strs);
	}

	@Override
	public void setData(Object obj) throws MineException {
		String fieldName = super.getFieldName();
		Object value = MineUtils.INSTANCE.getField(obj, fieldName);
		String[] strs = (String[]) value;
		setText(strs[index]);
	}

}
