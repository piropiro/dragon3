package mine.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import mine.MineException;
import net.arnx.jsonic.JSON;

/**
 * BeansJsonファイルを読み書きするクラス。
 *
 * @author k-saito
 * @version 1.0
 */
public class JsonIO {

    /**
     * ファイルからオブジェクトを読み込む。
     *
     * @param path - データファイルのパス
     * @return ファイルから読み込まれたオブジェクト
     * @throws MineException データの読み込みに失敗した。
     */
    public static <T> T read(String path, Class<T> clazz) throws MineException {
    	try {
    		try (InputStream is = FileIO.getInputStream(path)) {
	        	return JSON.decode(is,  clazz);
	        }
    	} catch (IOException e) {
    		throw new MineException(e);
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
        try (OutputStream os = FileIO.getOutputStream(path)) {
            String json = JSON.encode(obj, true);
        	
            IOUtils.write(json, os, "UTF-8");
        } catch (IOException e) {
    		throw new MineException(e);
    	}
    }
}
