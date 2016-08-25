package dragon3.attack.special;

import java.util.Set;

import dragon3.anime.AnimeManager;
import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;

public interface SpecialEffect {
	public boolean isEffective(Body ba, Body bb, Set<AttackEffect> effect);
	public void execute(Body ba, Body bb, AnimeManager anime);
}
