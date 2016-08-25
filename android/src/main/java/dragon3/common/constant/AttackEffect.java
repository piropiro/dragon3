package dragon3.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum AttackEffect {

	NONE("NONE"), // 0
	DAMAGE_200("威力２倍"), // 1
	DAMAGE_300("威力３倍"), // 2
	HICHU("必中"), // 3
	TAME("移動不可"), // 4
	COUNTER_ABLE("反撃可能"), // 5
	COMBO("連続攻撃"), // 6
	FIRE("火炎属性"), // 7
	ICE("氷結属性"), // 8
	THUNDER("電撃属性"), // 9
	SORA_200("飛行倍打"), // 10
	DRAGON_200("竜族倍打"), // 11
	COUNTER_ONLY("反撃専用"), // 12
	REGENE("回復効果"), // 13
	CRITICAL("一撃必殺"), // 14
	DEATH("デス"), // 15
	SLEEP("睡眠"), // 16
	POISON("毒"), // 17
	WET("濡れ"), // 18
	CHARM("魅了"), // 19
	ATTACK_UP("攻撃上昇"), // 20
	GUARD_UP("防御上昇"), // 21
	NO_ATTACK("攻撃なし"), // 22
	REFRESH("再行動"), // 23
	HIT_4("命中＋４"), // 24
	MISS_4("命中－４"), // 25
	UPPER("打ち上げ"), // 26
	CHOP("打ち下し"), // 27
	HEAL("回復"), // 28
	RIKU_0("地上のみ"), // 29
	MIZU_0("水中のみ"), // 30
	DAMAGE_150("威力1.5倍"), // 31
	UNDEAD_200("死霊倍打"), // 32
	OIL("油まみれ"), // 33
	RIKU_150("地上1.5倍"), // 34
	MIZU_200("水中倍打"), // 35
	MIZU_100("水中普通"), // 36
	HIT_12("命中＋12"), // 37
	;
	
	@Getter private String text;
	
	AttackEffect(String text) {
		this.text = text;
	}

	public static AttackEffect convert(int n) {
		switch (n) {
		case 1: return DAMAGE_200;
		case 2: return DAMAGE_300;
		case 3: return HICHU;
		case 4: return TAME;
		case 5: return COUNTER_ABLE;
		case 6: return COMBO;
		case 7: return FIRE;
		case 8: return ICE;
		case 9: return THUNDER;
		case 10: return SORA_200;
		case 11: return DRAGON_200;
		case 12: return COUNTER_ONLY;
		case 13: return REGENE;
		case 14: return CRITICAL;
		case 15: return DEATH;
		case 16: return SLEEP;
		case 17: return POISON;
		case 18: return WET;
		case 19: return CHARM;
		case 20: return ATTACK_UP;
		case 21: return GUARD_UP;
		case 22: return NO_ATTACK;
		case 23: return REFRESH;
		case 24: return HIT_4;
		case 25: return MISS_4;
		case 26: return UPPER;
		case 27: return CHOP;
		case 28: return HEAL;
		case 29: return RIKU_0;
		case 30: return MIZU_0;
		case 31: return DAMAGE_150;
		case 32: return UNDEAD_200;
		case 33: return OIL;
		case 34: return RIKU_150;
		case 35: return MIZU_200;
		case 36: return MIZU_100;
		case 37: return HIT_12;
		default:
			throw new IllegalArgumentException("Kinds unmatch: " + n);
		}
	}
	
	public static Map<String, String> createMap() {
		Map<String, String> idAndText = new LinkedHashMap<>();
		for (AttackEffect a : values()) {	
			idAndText.put(a.name(), a.getText());
		}
		return idAndText;
	}
}
