package dragon3.attack.special;

import java.util.Set;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import mine.paint.UnitMap;

public interface SpecialEffect {
	public boolean isEffective(UnitMap map, Body ba, Body bb, Set<AttackEffect> effect);
	public void execute(UnitMap map, Body ba, Body bb, AnimeManager anime);
}
