package mine.edit;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mine.MineException;
import mine.io.JsonIO;

/**
 * 編集するBeanのリストを扱うクラス。
 *
 * @author k-saito
 */
@SuppressWarnings("serial")
public class EditList<B> extends JList<String> implements ListSelectionListener {

	private EditListener<B> editListener;

	private List<B> beanList; // Beanのリスト

	/**
	 * コンストラクタ
	 *
	 * @param listener
	 */
	public EditList(EditListener<B> listener) {
		super();
		this.editListener = listener;
		addListSelectionListener(this);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setFont(new Font("Serif", Font.PLAIN, 14));
	}

	/**
	 * データを初期化する。
	 */
	public void initData(){
		List<B> list = new ArrayList<B>();
		list.add(editListener.createInstance());
		setList(list);
	}

	/**
	 * ファイルからBeanのリストをロードする。<p>
	 *
	 * @param file
	 */
	@SuppressWarnings("unchecked")
	public void loadData(String file) {
		try {
			B[] array = (B[])JsonIO.INSTANCE.read(file, editListener.createArray().getClass());
			List<B> list = new ArrayList<>(Arrays.asList(array));
			setList(list);
		} catch (RuntimeException e) {
			initData();
		}
	}

	/**
	 * ファイルにBeanのリストをセーブする。<p>
	 *
	 * @param file
	 */
	public void saveData(String file) {
		JsonIO.INSTANCE.write(file, beanList.toArray(editListener.createArray()));
	}

	/**
	 * 新しいBeanを生成してリストに加える。<p>
	 *
	 * @return 生成されたBean
	 */
	public B createData() {
		B data = editListener.createInstance();
		editListener.getData(data);
		beanList.add(data);
		setList(beanList);
		setSelectedIndex(beanList.size() -1);
		return data;
	}

	/**
	 * 新しいBeanをリストに加える。<p>
	 *
	 * @param data リストに加えるBean
	 */
	public void addData(B data) {
		beanList.add(data);
		editListener.setData(data);
		setList(beanList);
		setSelectedIndex(beanList.size() -1);
	}

	/**
	 * 新しいBeanをリストに加える。<p>
	 *
	 * @param index 加える位置
	 * @param data リストに加えるBean
	 */
	public void addDataAt(int index, B data) {
		beanList.add(index, data);
		editListener.setData(data);
		setList(beanList);
		setSelectedIndex(index);
	}

	/**
	 * Beanを削除する。<p>
	 */
	public void removeData(Object data) {
		int index = beanList.indexOf(data);
		if (index != -1) {
			beanList.remove(index);
			setList(beanList);
		}
	}

	/**
	 * 現在選択されているBeanを削除する。<p>
	 */
	public void removeSelectedData() {
		int index = getSelectedIndex();
		if (index != -1) {
			beanList.remove(index);
			setList(beanList);
			setSelectedIndex(Math.min(index, beanList.size()-1));
		}
	}

	/**
	 * データをBeanに反映させる。<p>
	 */
	public void acceptData() {
		int index = getSelectedIndex();
		if (index != -1) {
			B data = beanList.get(index);
			editListener.getData(data);
			setList(beanList);
			setSelectedIndex(index);
		}
	}

	/**
	 * 選択されているBeanを一つ上に移動する。<p>
	 */
	public void up(){
		int index = getSelectedIndex();
		if (index > 0) {
			moveData(index, index-1);
		}
	}

	/**
	 * 選択されているBeanを一つ下に移動する。<p>
	 */
	public void down(){
		int index = getSelectedIndex();
		if (index != -1 && index < beanList.size() - 1) {
			moveData(index, index+1);
		}
	}

	/**
	 * Beanを移動する。<p>
	 *
	 * @param i 移動前の位置
	 * @param j 移動後の位置
	 */
	public void moveData(int i, int j) {
		B data = beanList.get(i);
		beanList.remove(i);
		beanList.add(j, data);
		setList(beanList);
		setSelectedIndex(j);
	}

	/**
	 * リストを設定する。<p>
	 *
	 * @param list
	 */
	public void setList(List<B> list) {
		this.beanList = list;
		String[] names = new String[list.size()];
		for (int i=0; i<list.size(); i++) {
			names[i] = i + " - " + list.get(i);
		}
		setListData(names);
	}

	/**
	 * リストを取得する。<p>
	 *
	 * @return Beanのリスト
	 */
	public List<B> getList() {
		return beanList;
	}

	/**
	 * 選択されているデータを返す。
	 *
	 * @return
	 * @throws MineException
	 */
	public Object getSelectedData() throws MineException {
		int index = getSelectedIndex();
		if (index == -1 ) {
			throw new MineException("No item selected.");
		} else {
			return beanList.get(index);
		}
	}

	public Object getDataAt(int index) {
		return beanList.get(index);
	}

	public void selectData(Object data) {
		int index = beanList.indexOf(data);
		if (index != -1) {
			this.setSelectedIndex(index);
			editListener.setData(beanList.get(index));
		}
	}

	/**
	 * Beanが選択されたら、EditListenerにデータを反映する。<p>
	 *
	 * @see ListSelectionListener#valueChanged(ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
		int index = getSelectedIndex();
		if (index != -1) {
			editListener.setData(beanList.get(index));
		}
	}

}
