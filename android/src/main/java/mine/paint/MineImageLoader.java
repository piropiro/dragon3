/*
 * Created on 2004/10/10
 */
package mine.paint;

import mine.MineException;

/**
 * @author saito
 */
public interface MineImageLoader {
	public MineImage load(String fileName) throws MineException;

	public MineImage[][] loadTile(String fileName, int width, int height) throws MineException;

	public MineImage getBuffer(int width, int height);
}
