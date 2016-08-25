package mine.edit;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import mine.awt.CommandMenuBar;
import mine.event.CommandListener;
import mine.file.FileCommand;


public class EditMenuBar extends CommandMenuBar {

	private static final long serialVersionUID = 1L;

	private JMenu fileMenu;

	public EditMenuBar(CommandListener listener) {
		super(listener);

		fileMenu = newMenu("File", 'F');
		add(fileMenu);

		JMenu editMenu = newMenu("Data", 'D');
		editMenu.add(newItem("Create", "create", 'C', KeyEvent.VK_INSERT));
		editMenu.add(newItem("Delete", "delete", 'D', KeyEvent.VK_DELETE));
		editMenu.add(newItem("Accept", "accept", 'A', KeyEvent.VK_ENTER));
		add(editMenu);
		
		JMenu moveMenu = newMenu("Move", 'M');
		moveMenu.add(newItem("Up", "up", 'U', KeyEvent.VK_UP));
		moveMenu.add(newItem("Down", "down", 'D', KeyEvent.VK_DOWN));
		add(moveMenu);
	}

	/**
	 * Fileメニューを設定する。
	 */
	public void setFileList(String[] fileNames){
		fileMenu.removeAll();
		fileMenu.add(newItem("New", FileCommand.NEW, 'N', 'N'));
		fileMenu.add(newItem("Open", FileCommand.OPEN, 'O', 'O'));
		fileMenu.add(newItem("Save", FileCommand.SAVE, 'S', 'S'));
		fileMenu.add(newItem("Save As", FileCommand.SAVE_AS, 'A'));
		fileMenu.addSeparator();
		for (int i=0; i<fileNames.length; i++) {
			fileMenu.add(newItem(i + " " + fileNames[i], FileCommand.OPEN_AT + i, '0' + i));
		}
		fileMenu.addSeparator();
		fileMenu.add(newItem("Exit", "exit", 'X'));
	}
}
