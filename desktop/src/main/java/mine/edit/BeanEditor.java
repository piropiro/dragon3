package mine.edit;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import mine.MineException;
import mine.awt.FileManagerAWT;
import mine.awt.MineAwtUtils;
import mine.event.CommandListener;
import mine.file.FileCommand;
import mine.file.FileManager;
import mine.file.FileWorks;
import mine.io.JsonManager;


public class BeanEditor<B> extends JFrame implements CommandListener, FileWorks {
	
	private static final long serialVersionUID = 6210161316199948253L;

	private String title;

	private JsonManager jsonManager;

	private FileManager fileManager;
	
	private EditMenuBar menuBar;

	private EditList<B> editList;

	public BeanEditor(String title, String historyFile, String defaultFile, EditListener<B> editListener) {
		super();
		this.title = title;
		fileManager = new FileManager(historyFile, 10, defaultFile, editListener.getComponent(), this);
		menuBar = new EditMenuBar(this);
		jsonManager = new JsonManager();
		jsonManager.setFileManager(new FileManagerAWT());
		editList = new EditList<B>(jsonManager, editListener);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new EditToolBar(this), "North");
		getContentPane().add(editListener.getComponent(), "East");
		getContentPane().add(new JScrollPane(editList), "Center");
		setJMenuBar(menuBar);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		MineAwtUtils.setCenter(this);
		setVisible(true);
		try {
			fileManager.load();
		} catch (MineException e) {
			fileManager.create();
		}
		menuBar.setFileList(fileManager.getNameList());
	}


	/* (非 Javadoc)
	 * @see mine.file.FileWorks#create()
	 */
	public void create(String file) {
		editList.initData();
		setTitle(new File(file).getName() + " - " + title);	
	}


	/* (非 Javadoc)
	 * @see mine.file.FileWorks#load(java.lang.String)
	 */
	public void load(String file) throws MineException {
		editList.loadData(file);
		setTitle(new File(file).getName() + " - " + title);	
		menuBar.setFileList(fileManager.getNameList());
	}


	/* (非 Javadoc)
	 * @see mine.file.FileWorks#save(java.lang.String)
	 */
	public void save(String file) throws MineException {
		editList.saveData(file);
		setTitle(new File(file).getName() + " - " + title);	
		menuBar.setFileList(fileManager.getNameList());
	}

	/*** アクション ***********************************/

	public void doCommand(String command) {
		try {
			if (command.equals(FileCommand.NEW)) {
				fileManager.create();
			}
			if (command.equals(FileCommand.OPEN)) {
				fileManager.open();
			}
			for (int i = 0; i < 10; i++) {
				if (command.equals(FileCommand.OPEN_AT + i)) {
					fileManager.openAt(i);
				}
			}
			if (command.equals(FileCommand.SAVE)) {
				fileManager.save();
			}
			if (command.equals(FileCommand.SAVE_AS)) {
				fileManager.saveAs();
			}
		} catch (MineException e) {
			e.printStackTrace();
		}

		if (command.equals("create")) {
			editList.createData();
		}
		if (command.equals("delete")) {
			editList.removeSelectedData();
		}
		if (command.equals("accept")) {
			editList.acceptData();
		}
		if (command.equals("up")) {
			editList.up();
		}
		if (command.equals("down")) {
			editList.down();
		}
		if (command.equals("exit")) {
			dispose();
		}
	}
}
