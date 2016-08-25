package mine.edit.component;

import java.util.List;

import mine.EnumUtils;
import mine.MineException;
import mine.MineUtils;

public class EditEnumComboList<T> extends EditTextCombo implements EditComponent {

	private static final long serialVersionUID = -6432889311808048637L;
	
	private int index;
	
	private Class<T> enu;
	
	public EditEnumComboList(String fieldName, int index, Class<T> enu) {
		super(fieldName);
		this.index = index;
		this.enu = enu;
	}
	
	@Override
	public void getData(Object obj) throws MineException {
		String fieldName = super.getFieldName();
		@SuppressWarnings("unchecked")
		List<T> strs = (List<T>)MineUtils.INSTANCE.getField(obj, fieldName);
		
		while (index >= strs.size()) {
			strs.add((T)EnumUtils.callValueOf(enu, "NONE"));
		}
		
		strs.set(index, EnumUtils.callValueOf(enu, getValue()));
		
		MineUtils.INSTANCE.setField(obj, fieldName, strs);
	}

	@Override
	public void setData(Object obj) throws MineException {
		String fieldName = super.getFieldName();
		@SuppressWarnings("unchecked")
		List<T> strs = (List<T>)MineUtils.INSTANCE.getField(obj, fieldName);
		
		if (index < strs.size()) {
			setValue(strs.get(index).toString());
		} else {
			setValue("NONE");
		}
	}

}
