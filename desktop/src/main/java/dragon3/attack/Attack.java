package dragon3.attack;

import dragon3.common.Body;
import dragon3.common.constant.AttackEffect;
import dragon3.common.constant.GameColor;


public interface Attack {
    public String getName();
    public String getLabel();
    public GameColor getLabelColor();
    public int getStore();
    public int getDamage();
    public int getMeichu();
    public void setMeichu(int meichu);
    public int getRate();
    public boolean isHit();
    public boolean isEffective(AttackEffect effect);
    public boolean hasEffect(AttackEffect effect);
    public Body getAttacker();
    public Body getReceiver();
}
