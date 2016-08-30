/*
 * Created on 2004/10/10
 */
package mine.awt;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import mine.MineException;
import mine.io.FileManager;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

/**
 * @author saito
 */
public class ImageLoaderAWT implements MineImageLoader {

	private FileManager fileManager;


	public ImageLoaderAWT(FileManager fileManager) {
		this.fileManager = fileManager;
	}

    /**
     * ファイルからイメージを読み込む。
     * <p>
     *
     * @param path イメージファイルのパス
     * @return ファイルから読み込んだイメージ
     * @throws MineException 読み込みに失敗した。
     */
    public BufferedImage loadNative(String path) {
        try (InputStream in = fileManager.getInputStream(path)) {
            BufferedImage img = ImageIO.read(in);

            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    if (img.getRGB(x, y) == 0xFFC9FFFF) {
                        img.setRGB(x, y, 0);
                    }
                }
            }
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
    public BufferedImage[][] loadTileNative(String path, int width, int height) {
        BufferedImage img = loadNative(path);

        int xNum = img.getWidth() / width;
        int yNum = img.getHeight() / height;

        if (xNum == 0 || yNum == 0) {
            return new BufferedImage[][]{{img}
            };
        }

        BufferedImage[][] tiles = new BufferedImage[yNum][xNum];
        for (int y = 0; y < yNum; y++) {
            for (int x = 0; x < xNum; x++) {
                tiles[y][x] = img.getSubimage(x * width, y * height, width, height);
            }
        }
        return tiles;
    }
    
    @Override
	public MineImage load(String path) {
		return new ImageAWT(loadNative(path));
	}

    @Override
	public MineImage[][] loadTile(String path, int width, int height) {
		BufferedImage[][] btile = loadTileNative(path, width, height);

		MineImage[][] tile = new MineImage[btile.length][btile[0].length];
		for (int i=0; i<btile.length; i++) {
			for (int j=0; j<btile[i].length; j++) {
				tile[i][j] = new ImageAWT(btile[i][j]);
			}
		}
		return tile;
	}
	
    @Override
	public MineImage getBuffer(int width, int height) {
		return new ImageAWT(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
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
		BufferedImage simg = (BufferedImage)img.getImage();
		BufferedImage rimg = new BufferedImage(width, height, simg.getType());
		Graphics g = rimg.getGraphics();
		g.drawImage(simg, 0, 0, width, height, null);
		return new ImageAWT(rimg);
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
