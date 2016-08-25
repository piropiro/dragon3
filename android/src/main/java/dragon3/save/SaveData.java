package dragon3.save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dragon3.common.Body;
import dragon3.stage.StageStatus;

@lombok.Data
public class SaveData implements Serializable, Cloneable {

	private static final long serialVersionUID = 761813354588427943L;

	private boolean reverse;
	private boolean allClear;
	private int score;
	private int turn;
	private int dead;
	private int kill;
	private int item;
	private int escape;
	private int save;
	private long playTime;
	private String playerName = "Player";
	
	private List<Body> bodyList = new ArrayList<>();
	private Map<String, StageStatus> stageStatusMap = new HashMap<>();

	public SaveData copy() {
		try {
			return (SaveData) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public void countTurn() {
		turn++;
	}
	public void countDead() {
		dead++;
	}
	public void countKill() {
		kill++;
	}
	public void countItem() {
		item++;
	}
	public void countEscape() {
		escape++;
	}
	public void countSave() {
		save++;
	}
	public void addTime(long n) {
		playTime += n;
	}
	
	public StageStatus getStageStatus(String stageId) {
		if (!stageStatusMap.containsKey(stageId)) {
			StageStatus stageStatus = new StageStatus();
			stageStatus.setStageId(stageId);
			stageStatusMap.put(stageId, stageStatus);
		}
		return stageStatusMap.get(stageId);
	}
	
	public int getStarNum(String stageId) {
		return getStageStatus(stageId).getStar();
	}
	
	public void countStarNum(String stageId) {
		StageStatus status = getStageStatus(stageId);
		status.setStar(status.getStar() + 1);
	}
	
	public void setOpened(String stageId, boolean flag) {
		StageStatus status = getStageStatus(stageId);
		status.setOpened(flag);
	}
	
	public int sumStars() {
		int sum = 0;
		for (StageStatus status : stageStatusMap.values()) {
			sum += status.getStar();
		}
		return sum;
	}
}
