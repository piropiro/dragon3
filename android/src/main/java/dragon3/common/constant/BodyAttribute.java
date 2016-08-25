package dragon3.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

public enum BodyAttribute {
	
	NONE("NONE"), // 0
	DRAGON("竜族"), // 5
	UNDEAD("死霊"), // 34
	FIRE_200("火炎倍打"), // 6
	FIRE_50("火炎半減"), // 7
	FIRE_0("火炎無効"), // 8
	ICE_200("氷結倍打"), // 9
	ICE_50("氷結半減"), // 10
	ICE_0("氷結無効"), // 11
	THUNDER_200("電撃倍打"), // 12
	THUNDER_50("電撃半減"), // 13
	THUNDER_0("電撃無効"), // 14
	TALKABLE("会話可能"), // 15
	ANTI_ALL("全部無効"), // 17
	ANTI_CRITICAL("必殺無効"), // 18
	ANTI_DEATH("デス無効"), // 19
	ANTI_SLEEP("睡眠無効"), // 22
	ANTI_POISON("毒無効"), // 24
	ANTI_CHARM("魅了無効"), // 28
	SLEEP("睡眠"), // 21
	POISON("毒"), // 23s
	WET("水ぬれ"), // 25
	OIL("油まみれ"), // 26
	CHARM("魅了"), // 27
	REGENE("自然治癒"), // 29
	ATTACK_UP("攻撃UP"), // 30
	GUARD_UP("防御UP"), // 31
	POWERUP("強化"), // 33
	BERSERK("暴走"), // 58
	SORA("飛行"), // 36
	RIKU("歩行"), // 37
	FLY_ABLE("飛行可能"), // 46
	SWIM_ABLE("水中可能"), // 47
	LITE_WALK("軽歩可能"), // 57
	MOVE_UP_1("移動＋１"), // 48
	MOVE_UP_2("移動＋２"), // 49
	MOVE_DOWN_1("移動－１"), // 56
	SISTER("妹"), // 50
	HERO("主人公"), // 51
	DRAGON_KILLER("竜退治"), // 40
	UNDEAD_KILLER("死霊退治"), // 41
	MAGIC_50("魔法半減"), // 45
	SWORD_50("剣斧半減"), // 43
	CHARM_LOCK("魅了ロック"),
	SLEEP_LOCK("睡眠ロック"),
	WET_LOCK("水ぬれロック"),
	OIL_LOCK("オイルロック"),
	ATTACK_UP_LOCK("攻撃UPロック"),
	GUARD_UP_LOCK("防御UPロック"),
	MASTER("魔物使い"), // 42

	//CLASS("職業"), // 1
	//WEPON("武器"), // 2
	//ARMOR("防具"), // 3
	//ITEM("小物"), // 4

	//ASK("説得可能"), // 16
	//NKILL("即死無効"), // 20
	//S_WAIT("ウエイト"), // 32
	//POWERUP("強化"), // 33
	//S_LOCK("ロック"), // 35
	//SUMMON("召喚"), // 38
	//DOLL("ドール"), // 39
	//MASTER("魔物使い"), // 42
	//BADITEM("劣化品"), // 44

	//WAZA("技"), // 52
	//M_RED("赤玉"), // 53
	//M_GREEN("緑玉"), // 54
	//M_BLUE("青玉"), // 55
	;
	
	@Getter private String text;
	
	BodyAttribute(String text) {
		this.text = text;
	}
	
	public static BodyAttribute convert(int n) {
		switch (n) {
		//case 1: return CLASS;
		//case 2: return WEPON;
		//case 3: return ARMOR;
		//case 4: return ITEM;
		case 5: return DRAGON;
		case 6: return FIRE_200;
		case 7: return FIRE_50;
		case 8: return FIRE_0;
		case 9: return ICE_200;
		case 10: return ICE_50;
		case 11: return ICE_0;
		case 12: return THUNDER_200;
		case 13: return THUNDER_50;
		case 14: return THUNDER_0;
		case 15: return TALKABLE;
		case 16: return TALKABLE;
		case 17: return ANTI_ALL;
		case 18: return ANTI_CRITICAL;
		case 19: return ANTI_DEATH;
		//case 20: return NKILL;
		case 21: return SLEEP;
		case 22: return ANTI_SLEEP;
		case 23: return POISON;
		case 24: return ANTI_POISON;
		case 25: return WET;
		case 26: return OIL;
		case 27: return CHARM;
		case 28: return ANTI_CHARM;
		case 29: return REGENE;
		case 30: return ATTACK_UP;
		case 31: return GUARD_UP;
		//case 32: return S_WAIT;
		//case 33: return POWERUP;
		case 34: return UNDEAD;
		//case 35: return S_LOCK;
		case 36: return SORA;
		case 37: return RIKU;
		//case 38: return SUMMON;
		//case 39: return DOLL;
		case 40: return DRAGON_KILLER;
		case 41: return UNDEAD_KILLER;
		//case 42: return MASTER;
		case 43: return SWORD_50;
		//case 44: return BADITEM;
		case 45: return MAGIC_50;
		case 46: return FLY_ABLE;
		case 47: return SWIM_ABLE;
		case 48: return MOVE_UP_1;
		case 49: return MOVE_UP_2;
		case 50: return SISTER;
		case 51: return HERO;
		//case 52: return WAZA;
		//case 53: return M_RED;
		//case 54: return M_GREEN;
		//case 55: return M_BLUE;
		case 56: return MOVE_DOWN_1;
		case 57: return LITE_WALK;
		case 58: return BERSERK;

		default:
			throw new IllegalArgumentException("Types unmatch: " + n);
		}
	}
	
	public static Map<String, String> createMap() {
		Map<String, String> idAndText = new LinkedHashMap<>();
		for (BodyAttribute a : values()) {	
			idAndText.put(a.name(), a.getText());
		}
		return idAndText;
	}
}
