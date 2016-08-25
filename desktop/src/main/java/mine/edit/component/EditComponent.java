package mine.edit.component;

import java.awt.Component;

import mine.MineException;

public interface EditComponent {
	public String getFieldName();
	public Component getComponent();
	public void getData(Object obj) throws MineException;
	public void setData(Object obj) throws MineException;
}
