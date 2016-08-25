package mine.edit;

import mine.awt.CommandToolBar;
import mine.event.CommandListener;
import mine.file.FileCommand;


public class EditToolBar extends CommandToolBar {

	private static final long serialVersionUID = 4079891763397268302L;

	public EditToolBar(CommandListener listener) {
		super(listener);
		add(newButton("New", FileCommand.NEW, "mine/image/new.png"));
		add(newButton("Open", FileCommand.OPEN, "mine/image/open.png"));
		add(newButton("Save", FileCommand.SAVE, "mine/image/save.png"));
		add(newButton("SaveAs", FileCommand.SAVE_AS, "mine/image/saveas.png"));
		super.addSeparator();
		add(newButton("Create", "create"));
		add(newButton("Delete", "delete"));
		add(newButton("Accept", "accept"));
		super.addSeparator();
		add(newButton("Up", "up"));
		add(newButton("Down", "down"));
	}
}
