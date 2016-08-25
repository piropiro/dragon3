package dragon3.edit;

import java.util.Map;

import javax.inject.Inject;

import dragon3.Statics;
import dragon3.common.constant.ArmorType;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.BodyKind;
import dragon3.common.constant.MoveType;
import dragon3.common.constant.SoulType;
import dragon3.common.constant.WeponType;
import dragon3.data.BodyData;
import dragon3.image.BodyImageList;
import dragon3.image.ImageManager;
import mine.edit.BeanEditor;
import mine.edit.EditListener;
import mine.edit.EditPanel;

@SuppressWarnings("serial")
public class BodyEditor extends EditPanel<BodyData> implements EditListener<BodyData> {

	public static void main(String[] args) throws Exception {
		EditorComponent og = DaggerEditorComponent.builder().build();
		new BeanEditor<>("BodyEditor", "bodys.txt", "data.json", og.getBodyEditor());
	}

	@Inject
	public BodyEditor(Statics statics, ImageManager im) {
		super(BodyData.class);

		BodyImageList bil = im.getBodyImageList();

		setField(CENTER, "id", "ID");
		setField(CENTER, "name", "名前");
		setImageCombo(CENTER, "image", "画像");
		initCombo("image", bil.getPathList(), bil.getImageList());
		setEnumCombo(LEFT, "kind", "種別", BodyKind.class);
		initCombo("kind", BodyKind.Companion.createMap());
		setEnumCombo(RIGHT, "soulType", "魂色", SoulType.class);
		initCombo("soulType", SoulType.Companion.createMap());
		
		setSlider(CENTER, "hp", "HP", 99);
		setIntCombo(LEFT, "str", "攻撃", 99);
		setIntCombo(RIGHT, "def", "防御", 99);
		setIntCombo(LEFT, "mst", "魔法", 99);
		setIntCombo(RIGHT, "mdf", "抵抗", 99);
		setIntCombo(LEFT, "hit", "命中", 99);
		setIntCombo(RIGHT, "mis", "回避", 99);
//		setSlider(CENTER, "str", "攻撃", 30);
//		setSlider(CENTER, "def", "防御", 30);
//		setSlider(CENTER, "mst", "魔法", 30);
//		setSlider(CENTER, "mdf", "抵抗", 30);
//		setSlider(CENTER, "hit", "命中", 30);
//		setSlider(CENTER, "mis", "回避", 30);
		setEnumCombo(LEFT, "moveType", "移動", MoveType.class);
		initCombo("moveType", MoveType.Companion.createMap());
		setIntCombo(RIGHT, "moveStep", "歩数", 30);
		setEnumCombo(LEFT, "weponType", "武器", WeponType.class);
		initCombo("weponType", WeponType.Companion.createMap());
		setEnumCombo(RIGHT, "armorType", "防具", ArmorType.class);
		initCombo("armorType", ArmorType.Companion.createMap());

		Map<String, String> wazaIdAndName = statics.getWazaList().getIdAndName();
		for (int i=0; i<4; i++) {
			setTextCombo(CENTER, "wazaList", i, "特技" + i);
			initCombo("wazaList", i, wazaIdAndName);
		}

		for (int i=0; i<5; i++) {
			setEnumCombo(CENTER, "attrList", i, "特性" + i, BodyAttribute.class);
			initCombo("attrList", i, BodyAttribute.Companion.createMap());
		}
	}
}
