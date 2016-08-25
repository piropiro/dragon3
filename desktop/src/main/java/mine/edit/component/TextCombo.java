package mine.edit.component;

import java.util.Map;

import javax.swing.JComboBox;

public class TextCombo extends JComboBox<String> {

	private static final long serialVersionUID = -6432889311808048637L;

	private String[] id_;

	public TextCombo() {
	}

	public void init(String[] id, String[] text) {
		id_ = id;
		this.removeAllItems();
		for (int i = 0; i < text.length; i++) {
			addItem(text[i]);
		}
	}

	public void init(Map<String, String> idAndText) {
		String[] id = idAndText.keySet().toArray(new String[0]);
		String[] text = new String[id.length];
		for (int i=0; i < id.length; i++) {
			text[i] = idAndText.get(id[i]);
		}
		init(id, text);
	}


	public void setValue(String value) {
		for (int i=0; i < id_.length; i++) {
			if (id_[i].equals(value)) {
				setSelectedIndex(i);
				return;
			}
		}
	}

	public String getValue(){
		return id_[getSelectedIndex()];
	}
}
