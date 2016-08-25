package mine.edit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mine.edit.component.EditComponent;
import mine.edit.component.EditEnumCombo;
import mine.edit.component.EditEnumComboList;
import mine.edit.component.EditImageCombo;
import mine.edit.component.EditImageComboArray;
import mine.edit.component.EditIntCombo;
import mine.edit.component.EditIntComboArray;
import mine.edit.component.EditIntSlider;
import mine.edit.component.EditIntSliderArray;
import mine.edit.component.EditTextCombo;
import mine.edit.component.EditTextComboList;
import mine.edit.component.EditTextField;
import mine.edit.component.EditTextFieldArray;
import mine.edit.component.TextCombo;
import mine.paint.MineImage;


public class EditPanel<B> extends JPanel implements EditListener<B> {

	private static final long serialVersionUID = -1L;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int CENTER = 2;

	private GridBagLayout layout;
	private Font font;

	private HashMap<String, EditComponent> componentMap;

	private Class<B> clazz; // 編集対象クラス
	private int current_line; // 現在位置

	public EditPanel(Class<B> clazz) {
		this.clazz = clazz;
		current_line = 0;
		componentMap = new LinkedHashMap<String, EditComponent>();
		layout = new GridBagLayout();
		font = new Font("Serif", Font.PLAIN, 12);

		setLayout(layout);
	}

	public B createInstance() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public B[] createArray() {
		return (B[]) Array.newInstance(clazz, 0);
	}

	public JComponent getComponent() {
		return this;
	}

	/*** データ出力 ************************************************/

	public void getData(Object obj) {
		try {
			for (EditComponent component: componentMap.values()) {
				component.getData(obj);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*** データ入力 *****************************************************/

	public void setData(Object obj) {
		try {
			for (EditComponent component: componentMap.values()) {
				component.setData(obj);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/*** 設置 ********************************************************/

	public void add(EditComponent ec, String key, String label, int pos) {
		componentMap.put(key, ec);

		JLabel labe = new JLabel(label);
		labe.setFont(font);
		labe.setForeground(Color.black);

		Component comp = ec.getComponent();
		comp.setFont(font);

		switch (pos) {
			case CENTER :
				current_line++;
				add(labe, 1, current_line, 1, 1);
				add(comp, 2, current_line, 3, 1);
				break;
			case LEFT :
				current_line++;
				add(labe, 1, current_line, 1, 1);
				add(comp, 2, current_line, 1, 1);
				break;
			case RIGHT :
				add(labe, 3, current_line, 1, 1);
				add(comp, 4, current_line, 1, 1);
				break;
		}
	}

	/*** レイアウト **************************************************/

	private void add(Component obj, int x, int y, int xs, int ys) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = xs;
		c.gridheight = ys;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(obj, c);
		add(obj);
	}

	/*** フィールド設定 **********************************************/

	public void setField(int pos, String name, String label) {
		add(new EditTextField(name), name, label, pos);
	}

	public void setImageCombo(int pos, String name, String label) {
		add(new EditImageCombo(name), name, label, pos);
	}

	public void setTextCombo(int pos, String name, String label) {
		add(new EditTextCombo(name), name, label, pos);
	}
	
	public void setEnumCombo(int pos, String name, String label, Class<?> enu) {
		add(new EditEnumCombo(name, enu), name, label, pos);
	}

	public void setIntCombo(int pos, String name, String label, int max) {
		EditIntCombo combo = new EditIntCombo(name);
		combo.init(max);
		add(combo, name, label, pos);
	}

	public void setSlider(int pos, String name, String label, int max) {
		EditIntSlider slider = new EditIntSlider(name);
		slider.init(max);
		add(slider, name, label, pos);	}

	public void setImageCombo(int pos, String name, int index, String label) {
		add(new EditImageComboArray(name, index), name + "." + index, label, pos);
	}

	public void setField(int pos, String name, int index, String label) {
		add(new EditTextFieldArray(name, index), name + "." + index, label, pos);
	}

	public void setTextCombo(int pos, String name, int index, String label) {
		add(new EditTextComboList(name, index), name + "." + index, label, pos);
	}
	
	public <T> void setEnumCombo(int pos, String name, int index, String label, Class<T> enu) {
		add(new EditEnumComboList<T>(name, index, enu), name + "." + index, label, pos);
	}

	public void setIntCombo(int pos, String name, int index, String label, int max) {
		EditIntCombo combo = new EditIntComboArray(name, index);
		combo.init(max);
		add(combo, name + "." + index, label, pos);
	}

	public void setSlider(int pos, String name, int index, String label, int max) {
		EditIntSlider slider = new EditIntSliderArray(name, index);
		slider.init(max);
		add(slider, name, label, pos);
	}


	/*** 選択項目初期化 **********************************************/
	
	public void initCombo(String name, Map<String, String> idAndText) {
		TextCombo combo = (TextCombo)componentMap.get(name);
		combo.init(idAndText);
	}

	public void initCombo(String name, String[] id, String[] text) {
		TextCombo combo = (TextCombo)componentMap.get(name);
		combo.init(id, text);
	}

	public void initCombo(String name, String[] id, MineImage[] image) {
		EditImageCombo combo = (EditImageCombo)componentMap.get(name);
		combo.init(id, image);
	}

	public void initCombo(String name, int index, Map<String, String> idAndText) {
		initCombo(name + "." + index, idAndText);
	}

	public void initCombo(String name, int index, String[] id, String[] text) {
		initCombo(name + "." + index, id, text);
	}

	public void initCombo(String name, int index, String[] id, MineImage[] image) {
		initCombo(name + "." + index, id, image);
	}
}
