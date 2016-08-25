package mine.file;

import java.awt.Component;
import java.io.File;

import mine.MineException;


public class FileManager {
	
	private String historyFile;
	private String defaultFile;

	private Component invoker;
	private FileWorks worker;
	private FileHistory history;

	public FileManager(String historyFile, int max, String defaultFile, Component invoker, FileWorks worker) {
		this.historyFile = historyFile;
		this.defaultFile = defaultFile;
		this.invoker = invoker;
		this.worker = worker;

		history = new FileHistory(max);
		history.load(historyFile);
		if (history.size() == 0) {
			history.addFile(defaultFile);
		}
	}

	public String getCurrentFile(){
		return history.getCurrentFile();
	}

	/*** 新規作成 ***************************************/

	public void create() {
		history.addFile(defaultFile);
		worker.create(defaultFile);
	}

	public void load() throws MineException {
		worker.load(history.getCurrentFile());
	}

	/*** 開く *******************************************/

	public void open() throws MineException {
		try {
			File file = FileUtils.openFile(history.getCurrentFile(), invoker);
			history.addFile(file.getPath());
			history.save(historyFile);
			if (file.exists()) {
				worker.load(file.getPath());
			} else {
				worker.create(file.getPath());
			}
		} catch (CanceledException e) {
		}
	}

	/*** 指定のファイルを開く ***************************/

	public void openAt(int i) throws MineException {
		try {
			String file = history.getFile(i);
			history.addFile(file);
			history.save(historyFile);
			worker.load(file);
		} catch (CanceledException e) {
		}
	}
	
	/**
	 * 保存
	 * @throws MineException
	 */
	public void save() throws MineException {
		worker.save(history.getCurrentFile());
	}


	/*** 名前をつけて保存 *******************************/

	public void saveAs() throws MineException {
		try {
			File file = FileUtils.saveFile(history.getCurrentFile(), invoker);
			history.addFile(file.getPath());
			history.save(historyFile);
			worker.save(file.getPath());
		} catch (CanceledException e) {
		}
	}



	/*** ファイル名取得 *********************************/

	public String[] getNameList() {
		String[] files = history.getFileList();
		String[] names = new String[history.size()];
		for (int i=0; i<files.length; i++) {
			names[i] = new File(files[i]).getName();
		}
		return names;
	}
}
