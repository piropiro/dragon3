package dragon3.edit.deploy;

import dragon3.Statics;
import dragon3.common.constant.DeployType;
import dragon3.data.BodyData;
import dragon3.data.DeployData;
import dragon3.image.BodyImageList;
import dragon3.image.ImageManager;
import mine.edit.EditListener;
import mine.edit.EditPanel;
import mine.paint.MineImage;

import javax.inject.Inject;
import java.util.List;

@SuppressWarnings("serial")
public class StatusPanel extends EditPanel<DeployData> implements EditListener<DeployData> {

	@Inject
	StatusPanel(Statics statics, ImageManager im) {
		super(DeployData.class);

		
		setImageCombo(CENTER, "bodyId", "BODY");
		setEnumCombo(CENTER, "deployType", "配置種別", DeployType.class);
		initCombo("deployType", DeployType.Companion.createMap());
		setSlider(CENTER, "level", "Level", 15);
		
		setIntCombo(RIGHT, "limitTurn", "制限時間", 20);
		setIntCombo(LEFT, "scope", "射程", 5);
		setIntCombo(RIGHT, "range", "領域", 20);
		setIntCombo(LEFT, "x", "配置X", 20);
		setIntCombo(RIGHT, "y", "配置Y", 15);
		setIntCombo(LEFT, "goalX", "目標X", 20);
		setIntCombo(RIGHT, "goalY", "目標Y", 15);
		setIntCombo(LEFT, "limitTurn", "時限", 20);

		List<BodyData> bodyList = statics.getBodyList().getList();
		String[] idList = new String[bodyList.size()];
		MineImage[] imageList = new MineImage[bodyList.size()];

		BodyImageList bil = im.getBodyImageList();

		for (int i=0; i<bodyList.size(); i++) {
			BodyData body = bodyList.get(i);
			idList[i] = body.getId();
			imageList[i] = bil.getImage(body.getImage());
		}
	
		initCombo("bodyId", idList, imageList);
	}
}
