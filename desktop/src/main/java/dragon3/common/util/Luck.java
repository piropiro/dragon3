package dragon3.common.util;

import java.util.Random;

import dragon3.common.Body;
import dragon3.common.constant.GameColor;




public class Luck {

	private static Random random;

	private static int luckType = 0;

	public static final int FairLuck = 0;
	public static final int GoodLuck = 1;
	public static final int HardLuck = 2;
	public static final int NoneLuck = 3;

	public static void setup(int type) {
		luckType = type;
		random = new Random();
	}

	public static int rnd(int max) {
		return random.nextInt(max + 1);
	}

	public static int rnd(int width, Body b) {
		switch (luckType) {
			case FairLuck :
				return random.nextInt(width);
			case GoodLuck :
				if (GameColor.Companion.isPlayer(b.getColor()))
					return width;
				else
					return -width;
			case HardLuck :
				if (GameColor.Companion.isPlayer(b.getColor()))
					return -width;
				else
					return width;
			case NoneLuck:
				return 0;
			default :
				return 0;
		}
	}
}
