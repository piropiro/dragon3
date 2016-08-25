package dragon3.data.load;

import java.util.Arrays;
import java.util.List;

import dragon3.common.DataList;
import dragon3.data.AnimeData;

public class AnimeDataLoader {
	private static final String ANIME_DIR = "dragon3/data/anime/";

	private static final List<String> ANIME_FILES = Arrays.asList("AnimeData.json", "SystemAnime.json");

	public static DataList<AnimeData> loadAnimeList() {
		return new DataList<AnimeData>(ANIME_DIR, ANIME_FILES, AnimeData[].class);
	}
}
