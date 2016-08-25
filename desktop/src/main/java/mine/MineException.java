package mine;

/**
 * @author k-saito
 */
@SuppressWarnings("serial")
public class MineException extends Exception {

	public MineException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public MineException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public MineException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MineException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
