package mine.util;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 履歴を管理する。<p>
 * 
 * @author k-saito
 */
public class History<B> implements Serializable {

	private static final long serialVersionUID = 2775989549971497021L;

	private int max = 10;

	private LinkedList<B> list = new LinkedList<B>();
	
	public History() {
	}

	/**
	 * 履歴の最大数を指定してインスタンスを生成する。<p>
	 * 
	 * @param max
	 */
	public History(int max) {
		this.max = max;
	}

	/**
	 * 追加<p>
	 * 
	 * @param obj
	 */
	public void add(B obj) {
		if (obj != null) {
			if (list.contains(obj)) {
				list.remove(obj);
			}

			list.addFirst(obj);

			if (list.size() > max) {
				list.removeLast();
			}
		}
	}

	/**
	 * 指定されたインデックスの履歴を返す。<p>
	 * 
	 * @param i
	 * @return
	 */
	public B get(int i) {
		return list.get(i);
	}

	public int size() {
		return list.size();
	}
	
	public B[] toArray(B[] b) {
		return list.toArray(b);
	}

	/**
	 * @param src
	 */
	public void reset(B[] src) {
		list.clear();
		for (int i = 0; i < src.length; i++) {
			if (src[i] != null) {
				list.add(src[i]);
			}
		}
	}
	
	public void clear() {
		list.clear();
	}

}
