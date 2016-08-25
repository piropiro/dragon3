/*
 * 作成日: 2004/03/20
 */
package dragon3.image;

import java.util.HashMap;
import java.util.Map;

import mine.MineException;
import mine.MineUtils;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;

/**
 * @author k-saito
 */
public class BodyImageList {

	private String[] pathList;

	/**
	 * キャラの画像のリスト
	 */
	private MineImage[] imageList;

	/**
	 * イメージパスをイメージ番号にマップする。
	 */
	private Map<String, Integer> map;
	

	public BodyImageList(String image_dir, MineImageLoader mil) throws MineException {
		map = new HashMap<String, Integer>();
		
		pathList = MineUtils.readStringArray(image_dir + "list.txt");
		imageList = new MineImage[pathList.length];

		for (int i=0; i<pathList.length; i++) {
			imageList[i] = mil.load(image_dir + pathList[i]);
			map.put(pathList[i], i);
		}
	}
	
	public MineImage[] getImageList(){
		return imageList;
	}
	
	public String[] getPathList(){
		return pathList;
	}
	
	public MineImage getImage(int i) {
		if (i < imageList.length) {
			return imageList[i];
		} else {
			return imageList[0];
		}
	}
	
	public int getNum(String path){
		Integer n = map.get(path);
		if (n != null) {
			return n.intValue();
		} else {
			return 0;
		}
	}
	
	public MineImage getImage(String path){
		return imageList[getNum(path)];
	}
}
