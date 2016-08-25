/*
 * 作成日: 2004/03/18
 */
package mine.file;

import mine.MineException;

/**
 * @author k-saito
 */
public interface FileWorks {
	public void create(String file);
	public void load(String file) throws MineException;
	public void save(String file) throws MineException;
}
