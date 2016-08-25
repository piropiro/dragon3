package mine.edit.component;

import java.util.List;

import mine.MineException;
import mine.MineUtils;

public class EditTextComboList extends EditTextCombo implements EditComponent {

	private static final long serialVersionUID = -6432889311808048637L;
	
	private int index;
	
	public EditTextComboList(String fieldName, int index) {
		super(fieldName);
		this.index = index;
	}
	
	@Override
	public void getData(Object obj) throws MineException {
		String fieldName = super.getFieldName();
		@SuppressWarnings("unchecked")
		List<String> strs = (List<String>)MineUtils.INSTANCE.getField(obj, fieldName);
		
		while (index > strs.size()) {
			strs.add("none");
		}
		
		strs.set(index, getValue());
		
		MineUtils.INSTANCE.setField(obj, fieldName, strs);
	}

	@Override
	public void setData(Object obj) throws MineException {
		String fieldName = super.getFieldName();
		@SuppressWarnings("unchecked")
		List<String> strs = (List<String>)MineUtils.INSTANCE.getField(obj, fieldName);
		
		if (index < strs.size()) {
			setValue(strs.get(index));
		} else {
			setValue("none");
		}
	}

}
