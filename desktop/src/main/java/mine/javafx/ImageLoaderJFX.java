/*
 * Created on 2004/10/10
 */
package mine.javafx;

import java.io.IOException;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import mine.MineException;
import mine.io.FileIO;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

/**
 * @author saito
 */
public class ImageLoaderJFX implements MineImageLoader {
    /**
     * ファイルからイメージを読み込む。
     * <p>
     *
     * @param path イメージファイルのパス
     * @return ファイルから読み込んだイメージ
     * @throws MineException 読み込みに失敗した。
     */
    public static Image loadNative(String path) {
        try (InputStream in = FileIO.INSTANCE.getInputStream(path)) {
        	Image img = new Image(in);
            return img;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ファイルからタイルを読み込む。
     * <p>
     *
     * @param path イメージファイルのパス
     * @param width タイルの横幅
     * @param height タイルの高さ
     * @return ファイルから読み込んだタイル
     * @throws MineException ファイルの読み込みに失敗した。
     */
    public static Image[][] loadTileNative(String path, int width, int height) {
        Image img = loadNative(path);

        int xNum = (int)img.getWidth() / width;
        int yNum = (int)img.getHeight() / height;

        if (xNum == 0 || yNum == 0) {
            return new Image[][]{{img}
            };
        }

        Image[][] tiles = new Image[yNum][xNum];
        for (int y = 0; y < yNum; y++) {
            for (int x = 0; x < xNum; x++) {
            	WritableImage subimage = new WritableImage(width, height);
        		PixelWriter pw = subimage.getPixelWriter();
        		pw.setPixels(0, 0, width, height, img.getPixelReader(), x * width, y * height);
        		
                tiles[y][x] = subimage;
            }
        }
        return tiles;
    }
    
    @Override
	public MineImage load(String path) {
		return new ImageJFX(loadNative(path));
	}

    @Override
	public MineImage[][] loadTile(String path, int width, int height) {
		Image[][] btile = loadTileNative(path, width, height);

		MineImage[][] tile = new MineImage[btile.length][btile[0].length];
		for (int i=0; i<btile.length; i++) {
			for (int j=0; j<btile[i].length; j++) {
				tile[i][j] = new ImageJFX(btile[i][j]);
			}
		}
		return tile;
	}
	
    @Override
	public MineImage getBuffer(int width, int height) {
		return new ImageJFX(new WritableImage(width, height));
	}

	/**
	 * イメージを指定されたサイズに拡大縮小します。
	 * @param img - ソースイメージ
	 * @param width - 指定された幅
	 * @param height - 指定された高さ
	 * @return 指定されたサイズに拡大縮小されたイメージ
	 */
    @Override
	public MineImage resize(MineImage img, int width, int height) {
		Image simg = (Image)img.getImage();
		PixelReader pr = simg.getPixelReader();
		
		WritableImage rimg = new WritableImage(width, height);
		PixelWriter pw = rimg.getPixelWriter();
		
		for (int x = 0; x < rimg.getWidth(); x++) {
			for (int y = 0; y < rimg.getHeight(); y++) {
				
				pw.setArgb(x, y, pr.getArgb(
						(int)(simg.getWidth() * x / rimg.getWidth()),
						(int)(simg.getHeight() * y / rimg.getHeight())
						));
			}
		}
		return new ImageJFX(rimg);
	}

	/**
	 * イメージを指定された比率で拡大縮小します。
	 * @param img - ソースイメージ
	 * @param rate - サイズ比率
	 * @return 指定された比率で拡大縮小されたイメージ
	 */
    @Override
	public MineImage resize(MineImage img, double rate) {
		int width = (int) (img.getWidth() * rate);
		int height = (int) (img.getHeight() * rate);
		return resize(img, width, height);
	}
}
