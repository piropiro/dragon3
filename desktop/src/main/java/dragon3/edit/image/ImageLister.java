package dragon3.edit.image;

import java.io.File;
import java.io.FilenameFilter;

import mine.MineUtils;

public class ImageLister {
	static final String ANIME_IMAGE_DIR = "../dragon3_common/resources/dragon3/image/anime/";
	static final String BODY_IMAGE_DIR = "../dragon3_common/resources/dragon3/image/body/";
	public static void main(String args[]) throws Exception {
		list(ANIME_IMAGE_DIR);
		list(BODY_IMAGE_DIR);
		System.out.println("end");
	}
	
	private static void list(String imageDir) throws Exception {
		File folder = new File(imageDir);
		String[] list = folder.list(new FilenameFilter(){
			public boolean accept(File dir, String name) {
				return name.endsWith(".png");
			}
		});
		MineUtils.INSTANCE.writeStringArray(imageDir + "list.txt", list);
	}
}
