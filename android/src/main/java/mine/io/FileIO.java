/*
 * 作成日: 2003/09/28
 */
package mine.io;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mine.MineException;
import mine.MineUtils;

/**
 * @author k-saito
 *
 */
public class FileIO {

    public static Context context;

    /**
     * 指定されたファイルの入力ストリームを取得する。
     * <p>
     *
     * @param path ファイルパス
     * @return 指定されたファイルの入力ストリーム
     */
    public static BufferedInputStream getInputStream(String path) {

        final AssetManager assetManager = context.getAssets();

        try {
            BufferedInputStream bis = new BufferedInputStream(assetManager.open(path));
            return bis;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 指定されたファイルの出力ストリームを取得する。
     * <p>
     *
     * @param path ファイルパス
     * @return 指定されたファイルの出力ストリーム
     */
    public static BufferedOutputStream getOutputStream(String path) {


        try {
            OutputStream os = new FileOutputStream(path);
            return new BufferedOutputStream(os);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
