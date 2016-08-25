/*
 * Created on 2004/10/04
 */
package mine.edit.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mine.MineException;
import mine.MineUtils;
import mine.edit.EditPanel;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author saito
 */
public class EditPanelFactory<B> extends DefaultHandler {

	protected EditPanel<B> editPanel;

	private StringBuffer textBuffer;


	private String name;
	private String label;
	private String location;
	private String itemFile;
	private String max;

	public EditPanel<B> createEditPanel(String configFilePath) throws MineException {
		try {
			File configFile = new File(configFilePath);
			System.out.println("ConfigFile is [" + configFile.getAbsolutePath() + "]");


			if (!configFile.exists()) {
				throw new FileNotFoundException("[" + configFile.getAbsolutePath() + "]");
			}

			// SAXパーサーファクトリを生成
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			// SAXパーサーを生成
			SAXParser parser = spfactory.newSAXParser();

			// XMLファイルを指定されたデフォルトハンドラーで処理します
			parser.parse(configFile, this);

			return editPanel;
		} catch (Exception e) {
			throw new MineException(e);
		}
	}

	/**
	 * 要素の開始タグ読み込み時
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {
		System.out.println("startElement:" + qName);

		textBuffer = new StringBuffer();

		if (qName.equals("editor")) {
			try {
				String className = attributes.getValue("class");
				@SuppressWarnings("unchecked")
				Class<B> clazz = (Class<B>)ClassLoader.getSystemClassLoader().loadClass(className);
				editPanel = new EditPanel<B>(clazz);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		} else if (qName.equals("string")) {
			name = attributes.getValue("name");
			label = attributes.getValue("label");
			location = attributes.getValue("location");
			itemFile = null;
		} else if (qName.equals("int")) {
			name = attributes.getValue("name");
			label = attributes.getValue("label");
			location = attributes.getValue("location");
			max = null;
		}
	}
	/**
	 * テキストデータ読み込み時
	 */
	public void characters(char[] ch, int offset, int length) {
		textBuffer.append(new String(ch, offset, length));
	}

	/**
	 * テキストを取得する。
	 */
	public String getText(){
		return textBuffer.toString().trim();
	}

	/**
	 * 要素の終了タグ読み込み時
	 */
	public void endElement(String uri, String localName, String qName) {
		System.out.println("endElement:" + qName);
		try {
			if (qName.equals("itemFile")) {
				itemFile = getText();
			} else if (qName.equals("max")) {
				max = getText();
			} else if (qName.equals("string")) {
				if (itemFile == null) {
					editPanel.setField(getLocation(), name, label);
				} else {
					Map<String, String> idAndText = MineUtils.INSTANCE.readIdAndTextMap(itemFile);
					editPanel.setTextCombo(getLocation(), name, label);
					editPanel.initCombo(name, idAndText);
				}
			} else if (qName.equals("int")) {
				if (max != null) {
					editPanel.setSlider(getLocation(), name, label, Integer.parseInt(max));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int getLocation() {
		if (location == null) {
			return EditPanel.CENTER;
		} else if (location.equals("center")) {
			return EditPanel.CENTER;
		} else if (location.equals("left")) {
			return EditPanel.LEFT;
		} else if (location.equals("right")) {
			return EditPanel.RIGHT;
		} else {
			return EditPanel.CENTER;
		}
	}
}
