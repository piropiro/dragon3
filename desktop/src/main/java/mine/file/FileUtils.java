/*
 * 作成日: 2004/03/06
 */
package mine.file;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * @author k-saito
 */
public class FileUtils {

	/**
	 * ファイルを開く。<p>
	 * 
	 * @param file
	 * @param c
	 * @return
	 */
	public static File openFile(String file, Component c) throws CanceledException {
		JFileChooser fileChooser = new JFileChooser();
		if (file != null) {
			fileChooser.setSelectedFile(new File(file).getAbsoluteFile());
		}
		int result = fileChooser.showOpenDialog(c);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else {
			throw new CanceledException("No file selected.");
		}
	}

	/**
	 * ファイルを保存する。<p>
	 * 
	 * @param file
	 * @param c
	 * @return
	 */
	public static File saveFile(String file, Component c) throws CanceledException {
		JFileChooser fileChooser = new JFileChooser();
		if (file != null) {
			fileChooser.setSelectedFile(new File(file).getAbsoluteFile());
		}
		int result = fileChooser.showSaveDialog(c);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else {
			throw new CanceledException("No file selected.");
		}
	}
}
