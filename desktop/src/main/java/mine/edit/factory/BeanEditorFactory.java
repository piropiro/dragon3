/*
 * Created on 2004/10/04
 */
package mine.edit.factory;

import mine.edit.BeanEditor;
import mine.edit.EditPanel;



/**
 * @author saito
 */
public class BeanEditorFactory<B> extends EditPanelFactory<B> {

	private String title = "BeanEditor";
	private String historyFile ="history.xml";
	private String defaultFile ="data.xml";

	public static void main(String[] args) throws Exception {
		BeanEditorFactory<Object> factory = new BeanEditorFactory<>();
		EditPanel<Object> editPanel = factory.createEditPanel(args[0]);
		new BeanEditor<Object>(factory.getTitle(), factory.getHistoryFile(), factory.getDefaultFile(), editPanel);
	}

	/**
	 * 要素の終了タグ読み込み時
	 */
	public void endElement(String uri, String localName, String qName) {
		super.endElement(uri, localName, qName);

		if (qName.equals("title")) {
			title = getText();
		} else if (qName.equals("historyFile")) {
			historyFile = getText();
		} else if (qName.equals("defaultFile")) {
			defaultFile = getText();
		}
	}

	/**
	 * @return Returns the defaultFile.
	 */
	public String getDefaultFile() {
		return defaultFile;
	}
	/**
	 * @return Returns the historyFile.
	 */
	public String getHistoryFile() {
		return historyFile;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
}