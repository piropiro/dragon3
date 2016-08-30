package dragon3;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dragon3.common.DataList;
import dragon3.data.AnimeData;
import dragon3.data.BodyData;
import dragon3.data.DeployData;
import dragon3.data.StageData;
import dragon3.data.WazaData;
import mine.io.JsonManager;

@Singleton
public class Statics {

	public static final List<String> STAGE_FILES = Arrays.asList("StageData.json");
	public static final List<String> WAZA_FILES = Arrays.asList("WazaData.json" );
	public static final List<String> BODY_FILES = Arrays.asList("CharaData.json", "ClassData.json", "WeponData.json", "ArmorData.json", "ItemData.json");
	public static final List<String> ANIME_FILES = Arrays.asList("AnimeData.json", "SystemAnime.json");
	

	public static final String STAGE_DIR = "dragon3/data/stage/";
	public static final String WAZA_DIR = "dragon3/data/waza/";
	public static final String BODY_DIR = "dragon3/data/body/";
	public static final String ANIME_DIR = "dragon3/data/anime/";
	public static final String DEPLOY_DIR = "dragon3/data/deploy/";

	public static final String MAP_DIR = "dragon3/data/map/";
	public static final String TEXT_DIR = "dragon3/text/";

	public static final int TYPE_MAX = 100;

	private JsonManager jsonManager;

	private DataList<BodyData> bodyList;
	private DataList<WazaData> wazaList;
	private DataList<StageData> stageList;
	private DataList<AnimeData> animeList;
	private int[][] stageMapData;

	@Inject public Statics(JsonManager jsonManager) {
		this.jsonManager = jsonManager;
		bodyList = new DataList<BodyData>(jsonManager, BODY_DIR, BODY_FILES, BodyData[].class);
		wazaList = new DataList<WazaData>(jsonManager, WAZA_DIR, WAZA_FILES, WazaData[].class);
		stageList = new DataList<StageData>(jsonManager, STAGE_DIR, STAGE_FILES, StageData[].class);
		animeList = new DataList<AnimeData>(jsonManager, ANIME_DIR, ANIME_FILES, AnimeData[].class);
		stageMapData = jsonManager.read(Statics.STAGE_DIR + "map_stage.json", int[][].class);
	}
	
	public List<DeployData> getDeployData(String stageId) {
		return Arrays.asList(jsonManager.read(DEPLOY_DIR + "deploy_" + stageId + ".json", DeployData[].class));
	}

	public int[][] getMapData(String stageId) {
		return jsonManager.read(MAP_DIR + "map_" + stageId + ".json", int[][].class);
	}

	
    /*** DataLoad ******************************/
 
    public int[][] getCampMap() {
        return getMapData("camp");
    }

    public int[][] getCollectionMap() {
        return getMapData("collection");
    }

    public int[][] getWazalistMap() {
        return getMapData("wazalist");
    }
    
    public WazaData getWazaData(String wazaId) {
    	return wazaList.getData(wazaId);
    }
    
    public BodyData getBodyData(String bodyId) {
    	return bodyList.getData(bodyId);
    }
    
	public StageData getStageData(String stageId) {
		return stageList.getData(stageId);
	}
    
    public List<StageData> getStageList() {
    	return stageList.getList();
    }

	public DataList<BodyData> getBodyList() {
		return bodyList;
	}

	public DataList<WazaData> getWazaList() {
		return wazaList;
	}

	public DataList<AnimeData> getAnimeList() {
		return animeList;
	}

	public int[][] getStageMapData() {
		return stageMapData;
	}
}
