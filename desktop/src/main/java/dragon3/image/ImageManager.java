package dragon3.image;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dragon3.common.constant.GameColor;
import dragon3.data.StageData;
import dragon3.save.SaveData;
import dragon3.stage.StageBack;
import dragon3.stage.StageSelectPanel;
import dragon3.stage.StageStatus;
import lombok.Getter;
import mine.MineException;
import mine.MineUtils;
import mine.paint.MineGraphics;
import mine.paint.MineImage;
import mine.paint.MineImageLoader;


public class ImageManager {

	public static final String IMAGE_DIR = "dragon3/image/";
	public static final String BODY_IMAGE_DIR = "dragon3/image/body/";
	public static final String ANIME_IMAGE_DIR = "dragon3/image/anime/";
	public static final String STAGE_IMAGE_DIR = "dragon3/image/stage/";
	public static final String BACK_IMAGE_DIR = "dragon3/image/back/";	
	
	private MineImageLoader imageLoader;
	private AnimeImageList animeImageList;
	private BodyImageList bodyImageList;

	private MineImage[][] waku;
	private MineImage[] stageBack;
	private MineImage[] stageObj;
	private MineImage[] text;
	private MineImage[] status;
	private MineImage[] num;

	private MineImage[] stageWaku;
	private MineImage stageStar;
	
	private MineImage whiteBack;

	
	@Inject
	public ImageManager(MineImageLoader imageLoader) {
		this.imageLoader = imageLoader;

		try {
			waku = imageLoader.loadTile(IMAGE_DIR + "waku.png", 32, 32);
			text = imageLoader.loadTile(IMAGE_DIR + "text.png", 32, 12)[0];
			status = imageLoader.loadTile(IMAGE_DIR + "status.png", 32, 32)[0];
			num = imageLoader.loadTile(IMAGE_DIR + "num.png", 10, 12)[0];
			animeImageList = new AnimeImageList(ANIME_IMAGE_DIR, imageLoader);
			bodyImageList = new BodyImageList(BODY_IMAGE_DIR, imageLoader);
			
			stageWaku = imageLoader.loadTile(IMAGE_DIR + "stageWaku.png", StageSelectPanel.UNIT_WIDTH, StageSelectPanel.UNIT_HEIGHT)[0];
			stageStar = imageLoader.load(IMAGE_DIR + "stageStar.png");
			
			resetBack(StageBack.WHITE);
			whiteBack = this.stageBack[0];
		} catch (MineException e) {
			throw new RuntimeException(e);
		}
	}

	public MineImage getImage(String name) {
		return imageLoader.load(IMAGE_DIR + name);
	}
	
	public void resetBack(StageBack sb) {
		stageBack = imageLoader.loadTile(BACK_IMAGE_DIR + sb.getBackImage(), 32, 32)[0];
		//stageObj = MineUtils.linerize(imageLoader.loadTile(BACK_IMAGE_DIR + sb.getObjImage(), 32, 32), new MineImage[0]);
		List<MineImage> imageList = new ArrayList<>();
		for (MineImage[] array : imageLoader.loadTile(BACK_IMAGE_DIR + sb.getObjImage(), 32, 32)) {
			for (MineImage image : array) {
				imageList.add(image);
			}
		}
		stageObj = imageList.toArray(new MineImage[0]);

	}
	
	public MineImage[] loadStageImageList(List<StageData> stageList) {
		List<MineImage> list = new ArrayList<>();
		for (StageData stage : stageList) {
			MineImage img = imageLoader.load(STAGE_IMAGE_DIR + "stage_" + stage.getId() + ".png");
			list.add(img);
		}
		return list.toArray(new MineImage[0]);
	}
	
	public MineImage[] createStageStatusImageList(List<StageData> stageList, SaveData saveData) {
		List<MineImage> list = new ArrayList<>();
		for (StageData stage : stageList) {
			StageStatus status = saveData.getStageStatus(stage.getId());
			
			MineImage img = imageLoader.getBuffer(StageSelectPanel.UNIT_WIDTH, StageSelectPanel.UNIT_HEIGHT);
			MineGraphics g = img.getGraphics();
			
			g.setColor(0, 0, 150);
			g.fillRect(0, 80, StageSelectPanel.UNIT_WIDTH, StageSelectPanel.UNIT_WIDTH - 80);
			g.setColor(GameColor.BLUE.getFg());
			
			if (!status.getOpened()) {
				g.setColor(GameColor.BLUE.getAlphaBg());
				g.fillRect(0, 0,  img.getWidth(), img.getHeight());
				g.setColor(GameColor.BLUE.getFg());
				g.drawString("CLOSED", 30, 48);
			} else {
				g.drawString(stage.getName(), 30, 48);
			}
			int level = stage.getLevel() + status.getStar() * 5;
			g.drawString("Lv." + level, 70, 92);
			
			for (int i = 0; i < status.getStar(); i++) {
				g.drawImage(stageStar, 2 + 13 * i, 82);
			}
			list.add(img);
		}
		return list.toArray(new MineImage[0]);
	}

	public MineImageLoader getImageLoader() {
		return imageLoader;
	}

	public AnimeImageList getAnimeImageList() {
		return animeImageList;
	}

	public BodyImageList getBodyImageList() {
		return bodyImageList;
	}

	public MineImage[][] getWaku() {
		return waku;
	}

	public MineImage[] getStageBack() {
		return stageBack;
	}

	public MineImage[] getStageObj() {
		return stageObj;
	}

	public MineImage[] getText() {
		return text;
	}

	public MineImage[] getStatus() {
		return status;
	}

	public MineImage[] getNum() {
		return num;
	}

	public MineImage[] getStageWaku() {
		return stageWaku;
	}

	public MineImage getStageStar() {
		return stageStar;
	}

	public MineImage getWhiteBack() {
		return whiteBack;
	}
}
