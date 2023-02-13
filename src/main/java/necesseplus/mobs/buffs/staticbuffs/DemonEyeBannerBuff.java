package necesseplus.mobs.buffs.staticbuffs;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.VicinityBuff;

public class DemonEyeBannerBuff extends VicinityBuff {
    public void init(ActiveBuff buff) {
      buff.setModifier(BuffModifiers.SPEED, Float.valueOf(0.15F));
    }
    
    public void updateLocalDisplayName() {
      this.displayName = (GameMessage)new LocalMessage("item", getStringID());
    }
  }
  