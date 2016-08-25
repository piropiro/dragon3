package dragon3.edit;

import dragon3.common.constant.AnimeType;
import dragon3.data.AnimeData;
import dragon3.image.AnimeImageList;
import dragon3.image.ImageManager;
import mine.edit.BeanEditor;
import mine.edit.EditPanel;
import mine.paint.MineImage;

import javax.inject.Inject;

@SuppressWarnings("serial")
public class AnimeEditor extends EditPanel<AnimeData> {

	public static void main(String[] args) throws Exception {
		EditorComponent og = DaggerEditorComponent.builder().build();

		new BeanEditor<>("AnimeEditor", "animes.txt", "data.json", og.getAnimeEditor());
	}

	@Inject
	AnimeEditor(ImageManager im) {
		super(AnimeData.class);

		AnimeImageList ail = im.getAnimeImageList();
		
		String[] pathList = ail.getPathList();
		MineImage[][] imageList = ail.getImageList();
		MineImage[] firstImageList = new MineImage[imageList.length];
		for (int i=0; i<imageList.length; i++) {
			firstImageList[i] = imageList[i][imageList[i].length / 2];
		}

		setField(CENTER, "id", "ID");
		setField(CENTER, "name", "名前");
		setImageCombo(CENTER, "image", "画像");
		setEnumCombo(CENTER, "type", "タイプ", AnimeType.class);
		setSlider(CENTER, "sleep", "ウエイト", 100);

		initCombo("image", pathList, firstImageList);
		initCombo("type", AnimeType.Companion.createMap());
	}
}
