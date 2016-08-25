/*
 * Created on 2004/10/10
 */
package mine.util;

import java.util.Random;

/**
 * @author saito
 */
public class Randomer extends Random {

	private static final long serialVersionUID = 420845759446346283L;

	private static Randomer randomer;
	
	public static Randomer getInstance() {
		if (randomer == null) {
			randomer = new Randomer();
		}
		return randomer;
	}
}