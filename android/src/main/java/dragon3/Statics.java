package dragon3;

import java.util.Arrays;
import java.util.List;

import dragon3.common.DataList;
import dragon3.data.BodyData;
import dragon3.data.DeployData;
import dragon3.data.StageData;
import dragon3.data.WazaData;
import mine.MineException;
import mine.io.JsonIO;

public class Statics {

	public static final List<String> STAGE_FILES = Arrays.asList("StageData.json");
	public static final List<String> WAZA_FILES = Arrays.asList("WazaData.json" );
	public static final List<String> BODY_FILES = Arrays.asList("CharaData.json", "ClassData.json", "WeponData.json", "ArmorData.json", "ItemData.json");

	public static final String WAZA_DIR = "dragon3/data/waza/";

	public static final String BODY_DIR = "dragon3/data/body/";
	public static final String DEPLOY_DIR = "dragon3/data/deploy/";
	public static final String STAGE_DIR = "dragon3/data/stage/";

	public static final String MAP_DIR = "dragon3/data/map/";
	public static final String TEXT_DIR = "dragon3/text/";

	public static final int TYPE_MAX = 100;


	public static final DataList<BodyData> bodyList = new DataList<BodyData>(BODY_DIR, BODY_FILES, BodyData[].class);
	public static final DataList<WazaData> wazaList = new DataList<WazaData>(WAZA_DIR, WAZA_FILES, WazaData[].class);
	public static final DataList<StageData> stageList = new DataList<StageData>(STAGE_DIR, STAGE_FILES, StageData[].class);


	public static int getBukiType(int type) {
		switch (type) {
		case 1:
		case 2:
		case 3:
			return 1;
		case 4:
		case 5:
		case 6:
			return 2;
		default:
			return 3;
		}
	}


	
	public static List<DeployData> getDeployData(String stageId) {
		try {
			return Arrays.asList(JsonIO.read(DEPLOY_DIR + "deploy_" + stageId + ".json", DeployData[].class));
		} catch (MineException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static StageData getStageData(String stageId) {
		return stageList.getData(stageId);
	}

	public static int[][] getMapData(String stageId) {
		try {
			return JsonIO.read(MAP_DIR + "map_" + stageId + ".json", int[][].class);
		} catch (MineException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static int[][] getStageMapData() {
        try {
            int[][] stages = JsonIO.read(Statics.STAGE_DIR + "map_stage.json", int[][].class);
            return stages;
        } catch (MineException e) {
            throw new RuntimeException(e);
        }
	}
	
    /*** DataLoad ******************************/
 
    public static int[][] getCampMap() {
        return Statics.getMapData("camp");
    }

    public static int[][] getCollectionMap() {
        return Statics.getMapData("collection");
    }

    public static int[][] getWazalistMap() {
        return Statics.getMapData("wazalist");
    }
}
