package mine.edit.component;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import mine.paint.MineImage;

public class ImageComboRenderer extends JLabel implements ListCellRenderer<Integer> {

	private static final long serialVersionUID = 7643382757117145228L;

	private ImageIcon[] images;
	private String[] texts;

	public ImageComboRenderer() {
        setOpaque(true);
    }

	public void init(String[] inTexts, MineImage[] bimgs) {
		this.texts = inTexts;
		this.images = new ImageIcon[texts.length];
		for (int i=0; i<bimgs.length; i++) {
			images[i] = new ImageIcon((BufferedImage)bimgs[i].getImage());
		}
	}


	@Override
	public Component getListCellRendererComponent(
			JList<? extends Integer> list, Integer value, int index,
			boolean isSelected, boolean cellHasFocus) {
		int selectedIndex = value;

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //Set the icon and text.  If icon was null, say so.
        ImageIcon icon = images[selectedIndex];
        String text = texts[selectedIndex];
        setIcon(icon);
        if (icon != null) {
            setText(text);
            setFont(list.getFont());
        } else {
            setText(text + " (no image available)");
        }

        return this;

	}

}
