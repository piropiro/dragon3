package mine.io;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

import mine.MineException;

/**
 * BeansXMLファイルを読み書きするクラス。
 *
 * @author k-saito
 * @version 1.0
 */
public class BeanIO {

    /**
     * ファイルからオブジェクトを読み込む。
     *
     * @param path - データファイルのパス
     * @return ファイルから読み込まれたオブジェクト
     * @throws MineException データの読み込みに失敗した。
     */
    public static Object read(String path) throws MineException {
        try (XMLDecoder in = new XMLDecoder(FileIO.INSTANCE.getInputStream(path))) {
            Object obj = in.readObject();
            if (obj != null) {
                return obj;
            } else {
                throw new MineException("Failed to read Beans.");
            }
        }
    }

    /**
     * オブジェクトをファイルに書き出す。
     *
     * @param path - データファイルのパス
     * @param obj - 書き出すオブジェクト
     * @throws MineException データの書き込みに失敗した。
     */
    public static void write(String path, Object obj) throws MineException {
        try (XMLEncoder out = new XMLEncoder(FileIO.INSTANCE.getOutputStream(path))) {
            out.writeObject(obj);
        }
    }
}
