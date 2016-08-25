package card;

import card.common.ImageList;
import card.common.Page;
import lombok.Getter;
import mine.paint.MineImageLoader;
import mine.paint.UnitMap;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CardMap {

	@Getter private UnitMap map;
	
	@Inject
	public CardMap(ImageList il, MineImageLoader mil) {
		map = new UnitMap(3, 11, 13, mil);
		map.setTile(Page.BACK, il.getBack(), -1);
		map.setTile(Page.WAKU, il.getWaku(), 0);
		map.clear(Page.BACK, -1);
		map.clear(Page.CHARA, -1);
		map.clear(Page.WAKU, 0);
		map.setVisible(0, true);
		map.setVisible(1, true);
		map.setVisible(2, true);
	}
	
}
