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
public class AnimeImageList {
	
	/**
	 * イメージパスをイメージ番号にマップする。
	 */
	private Map<String, MineImage[]> map;
	
	private String[] pathList;
	
	private MineImage[][] imageList;
	
	public AnimeImageList(String image_dir, MineImageLoader mil) throws MineException {
		
		map = new HashMap<String, MineImage[]>();
		
		pathList = MineUtils.readStringArray(image_dir + "list.txt");

		imageList = new MineImage[pathList.length][];
		
		for (int i=0; i<pathList.length; i++) {
			imageList[i] = mil.loadTile(image_dir + pathList[i], 32, 32)[0];
			map.put(pathList[i], imageList[i]);
		}
	}
	
	public MineImage[][] getImageList(){
		return imageList;
	}
	
	public String[] getPathList(){
		return pathList;
	}
	
	public MineImage[] getImage(String path){
		MineImage[] image = map.get(path);
		if (image != null) {
			return image;
		} else {
			return map.get("0.png");
		}
	}
}
