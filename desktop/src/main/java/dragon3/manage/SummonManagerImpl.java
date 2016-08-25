package dragon3.manage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.DeployType;
import dragon3.common.constant.Page;
import dragon3.common.util.MoveUtils;
import dragon3.map.StageMap;
import mine.util.Point;

@Singleton
public class SummonManagerImpl implements SummonManager {
	private List<Body> devils;

	@Inject AnimeManager anime;
	@Inject StageMap map;

	/*** Constructer *********************************************/

	@Inject
	public SummonManagerImpl() {
		clean();
	}

	@Override
	public void clean() {
		this.devils = new ArrayList<>();
	}
	
	@Override
	public void setup(List<Body> enemys) {
		clean();

		for (Body b : enemys) {
			if (b.getDeployType() == DeployType.SUMMON) {
				map.getMap().setData(Page.P01, b.getGoalX(), b.getGoalY(), MoveUtils.CLOSE_MAGIC);
				b.setX(b.getGoalX());
				b.setY(b.getGoalY());
				b.setGoalX(0);
				b.setGoalY(0);
				b.setHp(0);
				devils.add(b);
			}
		}
	}
	
	@Override
	public int getLimitTurn(Point p) {
		int turn = 64;
		for (int i=0; i<devils.size(); i++) {
			Body b = (Body)devils.get(i);
			if (b == null)
				continue;
			if (p.x != b.getX())
				continue;
			if (p.y != b.getY())
				continue;
			if (turn > b.getLimitTurn())
				turn = b.getLimitTurn();
		}
		return turn;
	}

	@Override
	public void summon(int turn) {

		for (int i=0; i<devils.size(); i++) {
			Body b = (Body)devils.get(i);
			if (b.getLimitTurn() > turn)
				continue;
			if (map.getMap().getData(Page.P20, b.getX(), b.getY()) != 0)
				continue;
			map.getMap().setData(Page.P01, b.getX(), b.getY(), MoveUtils.OPEN_MAGIC);
			anime.systemAnime(AnimeManager.ID_SUMMON, b.getX(), b.getY());

			anime.summonAnime(b.getImageNum(), b.getX(), b.getY());
			b.setHp(b.getHpMax());
		}
	}
}
