/*
 * Created on 2004/10/10
 */
package mine.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;

import mine.MineException;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

/**
 * @author saito
 */
public class ImageLoaderAND implements MineImageLoader {

	private Context context;

	public ImageLoaderAND(Context context) {
		this.context = context;
	}

    /**
     * ファイルからイメージを読み込む。
     * <p>
     *
     * @param fileName イメージのpath
     * @return ファイルから読み込んだイメージ
     * @throws MineException 読み込みに失敗した。
     */
    public Bitmap loadNative(String fileName) {
        final AssetManager assetManager = context.getAssets();

        try (BufferedInputStream bis = new BufferedInputStream(assetManager.open(fileName))) {
            return BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ファイルからタイルを読み込む。
     * <p>
     *
     * @param fileName イメージファイルのパス
     * @param width タイルの横幅
     * @param height タイルの高さ
     * @return ファイルから読み込んだタイル
     * @throws MineException ファイルの読み込みに失敗した。
     */
    public Bitmap[][] loadTileNative(String fileName, int width, int height) {
        Bitmap img = loadNative(fileName);

        int xNum = img.getWidth() / width;
        int yNum = img.getHeight() / height;

        if (xNum == 0 || yNum == 0) {
            return new Bitmap[][]{{img}
            };
        }

        Bitmap[][] tiles = new Bitmap[yNum][xNum];
        for (int y = 0; y < yNum; y++) {
            for (int x = 0; x < xNum; x++) {
                tiles[y][x] = Bitmap.createBitmap(img, x * width, y * height, width, height);
            }
        }
        return tiles;
    }
    
    @Override
	public MineImage load(String fileName) {
		return new ImageAND(loadNative(fileName));
	}

    @Override
	public MineImage[][] loadTile(String fileName, int width, int height) {
		Bitmap[][] btile = loadTileNative(fileName, width, height);

		MineImage[][] tile = new MineImage[btile.length][btile[0].length];
		for (int i=0; i<btile.length; i++) {
			for (int j=0; j<btile[i].length; j++) {
				tile[i][j] = new ImageAND(btile[i][j]);
			}
		}
		return tile;
	}
	
    @Override
	public MineImage getBuffer(int width, int height) {
		return new ImageAND(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
	}

    @NotNull
    @Override
    public MineImage resize(@NotNull MineImage img, int width, int height) {
        return null;
    }

    @NotNull
    @Override
    public MineImage resize(@NotNull MineImage img, double rate) {
        return null;
    }
}
