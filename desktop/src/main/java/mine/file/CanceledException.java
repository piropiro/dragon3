/*
 * 作成日: 2004/03/18
 */
package mine.file;

import mine.MineException;

/**
 * @author k-saito
 */
public class CanceledException extends MineException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1327148215082072643L;

	/**
	 * 
	 */
	public CanceledException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public CanceledException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public CanceledException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CanceledException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
