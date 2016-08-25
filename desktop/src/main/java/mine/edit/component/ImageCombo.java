package mine.edit.component;

import javax.swing.JComboBox;

import mine.paint.MineImage;

public class ImageCombo extends JComboBox<Integer> {

	private static final long serialVersionUID = -6432889311808048637L;

	private ImageComboRenderer renderer;

	private String[] id_;

	public ImageCombo() {
		renderer = new ImageComboRenderer();
		this.setRenderer(renderer);
	}

	public void init(String[] id, MineImage[] images) {
		this.id_ = id;
		renderer.init(id, images);

		removeAllItems();
		for (int i=0; i<id.length; i++) {
			addItem(i);
		}
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
