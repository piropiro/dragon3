package dragon3.common.util;

import dragon3.common.Body;
import dragon3.common.constant.BodyAttribute;
import dragon3.common.constant.GameColor;
import dragon3.common.constant.Texts;
import dragon3.controller.UnitWorks;

public class Tutorial {
	public static void setHelp(Body ba, Body bb, int n, UnitWorks uw) {
		String[] line;
		line = Texts.tuto[0];

		GameColor color = GameColor.BLUE;
		if (bb != null) {
			if (ba.hasAttr(BodyAttribute.HERO)) {
				if (n == 0) {
					if (bb.base.getName().equals(Texts.goburin)) {
						line = Texts.tuto[1];
						color = GameColor.RED;
					} else if (bb.base.getName().equals(Texts.pikusi)) {
						line = Texts.tuto[2];
					} else if (bb.base.getName().equals(Texts.gaikotu)) {
						if (bb.getHp() == bb.getHpMax()) {
							line = Texts.tuto[3];
							color = GameColor.GREEN;
						} else {
							line = Texts.tuto[4];
						}
					}
				} else {
					if (bb.base.getName().equals(Texts.goburin)) {
						line = Texts.tuto[5];
						color = GameColor.RED;
					} else if (bb.base.getName().equals(Texts.pikusi)) {
						line = Texts.tuto[6];
						color = GameColor.RED;
					} else if (bb.base.getName().equals(Texts.gaikotu)) {
						if (bb.getHpMax() == bb.getHpMax()) {
							line = Texts.tuto[7];
						} else {
							line = Texts.tuto[8];
							color = GameColor.RED;
						}
					}
				}
			} else if (ba.hasAttr(BodyAttribute.SISTER)) {
				if (n == 0) {
					if (bb.base.getName().equals(Texts.goburin)) {
						line = Texts.tuto[9];
					} else if (bb.base.getName().equals(Texts.pikusi)) {
						line = Texts.tuto[10];
						color = GameColor.RED;
					} else if (bb.base.getName().equals(Texts.gaikotu)) {
						if (bb.getHpMax() == bb.getHpMax()) {
							line = Texts.tuto[11];
						} else {
							line = Texts.tuto[12];
							color = GameColor.RED;
						}
					}
				} else {
					if (bb.base.getName().equals(Texts.goburin)) {
						line = Texts.tuto[13];

					} else if (bb.base.getName().equals(Texts.pikusi)) {
						line = Texts.tuto[14];
						color = GameColor.RED;
					} else if (bb.base.getName().equals(Texts.gaikotu)) {
						if (bb.getHp() == bb.getHpMax()) {
							line = Texts.tuto[15];
							color = GameColor.GREEN;
						} else {
							line = Texts.tuto[16];
						}
					}
				}
			}
		}
		uw.getPanelManager().displayHelp(uw.getMapWorks().getWaku(), color, line);
	}
}
