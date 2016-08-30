package dragon3.anime;

import dragon3.controller.UnitWorks;
import dragon3.data.AnimeData;

public interface AnimeManager extends AnimeWorks {

	public static final String ID_REFRESH = "system.refresh";
	public static final String ID_KAKUSEI = "system.kakusei";
	public static final String ID_FINISH = "system.finish";
	public static final String ID_DEATH = "system.death";
	public static final String ID_SUMMON = "system.summon";
	public static final String ID_FIRE = "system.fire";

	public static final int TEXT_MISS = 0;
	public static final int TEXT_POISON = 1;
	public static final int TEXT_SLEEP = 2;
	public static final int TEXT_FINISH = 3;
	public static final int TEXT_WIN = 4;
	public static final int TEXT_DEATH = 5;
	public static final int TEXT_CLOSE = 6;

	public static final int STATUS_SLEEP = 1;
	public static final int STATUS_POISON = 2;
	public static final int STATUS_WET = 3;
	public static final int STATUS_ATTACK_UP = 4;
	public static final int STATUS_GUARD_UP = 5;
	public static final int STATUS_CHARM = 6;
	public static final int STATUS_REGENE = 7;
	public static final int STATUS_SORA = 8;
	public static final int STATUS_RIKU = 9;
	public static final int STATUS_OIL = 10;
	public static final int STATUS_HAMMER = 11;
	public static final int STATUS_BERSERK = 12;

	public void dispose();

	public void setVisible(boolean flag);
	
	public AnimeData getData(String id);

	public void systemAnime(String id, int x, int y);
	public void openTitle();
	public void closeTitleIn();
	public void closeTitleOut();
	public void eraseAnime(int x, int y);
	public void walkAnime(int x, int y);
	public void numberAnime(int n, int x, int y);
	public void criticalAnime(int x, int y);
	public void dropText(int text, int x, int y);
	public void slideText(int text, int x, int y);
	public void statusAnime(int status, int x, int y);
	public void summonAnime(int image, int x, int y);
	public void singleAnime(AnimeData data, int x, int y);
	public void allAnime(AnimeData data);
	public void singleArrowAnime(AnimeData data, int startX, int startY, int goalX, int goalY);
	public void someArrowAnime(AnimeData data, int x, int y);
	public void rotateAnime(AnimeData data, int startX, int startY, int goalX, int goalY);


}
