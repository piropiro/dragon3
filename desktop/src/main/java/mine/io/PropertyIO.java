package mine.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import mine.MineException;

/**
 * プロパティファイルを読み書きする。
 *
 * @author k-saito
 * @version 1.0
 */
public class PropertyIO {

    /**
     * プロパティをファイルから読み込む。
     * <p>
     *
     * @param path プロパティファイルのパス
     * @return プロパティ
     * @throws MineException 読み込みに失敗した。
     */
    public static Properties read(String path) throws MineException {
        try (InputStream in = FileIO.INSTANCE.getInputStream(path)) {
            Properties prop = new Properties();
            prop.load(in);
            return prop;
        } catch (IOException e) {
            throw new MineException(e);
        }
    }

    /**
     * プロパティをファイルに書き出す。
     * <p>
     *
     * @param path プロパティファイルのパス
     * @param prop プロパティ
     * @throws MineException 書き出しに失敗した。
     */
    public static void write(String path, Properties prop) throws MineException {
        try (OutputStream out = FileIO.INSTANCE.getOutputStream(path)) {
            prop.store(out, null);
        } catch (IOException e) {
            throw new MineException(e);
        }
    }
}
