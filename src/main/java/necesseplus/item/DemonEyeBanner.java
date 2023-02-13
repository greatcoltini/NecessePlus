package necesseplus.item;

import java.util.function.Function;
import java.util.stream.Stream;
import necesse.engine.localization.Localization;
import necesse.engine.network.NetworkClient;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.pickup.ItemPickupEntity;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

public class DemonEyeBanner extends Item {
  public Function<Mob, Buff> buff;
  
  public int range;
  
  public boolean buffsPlayers = true;
  
  public boolean buffsMobs = false;

  public DemonEyeBanner()
  {
    super(1);
    this.rarity = Rarity.COMMON;
    this.range = 32;
    this.worldDrawSize = 32;
    setItemCategory((new String[] {"equipment", "banners"}));
  }  
  
  public void tickHolding(InventoryItem item, PlayerMob player) {
    super.tickHolding(item, player);
    if (this.buffsPlayers)
      GameUtils.streamNetworkClients(player.getLevel())
        .filter(c -> (c.playerMob.getDistance(player.x, player.y) <= this.range))
        .filter(c -> shouldBuffPlayer(item, player, c.playerMob))
        .forEach(c -> applyBuffs((Mob)c.playerMob)); 
    if (this.buffsMobs) {
      Stream<Mob> mobsStream = (player.getLevel()).entityManager.mobs.streamInRegionsShape(GameUtils.rangeBounds(player.x, player.y, this.range), 0);
      mobsStream
        .filter(m -> !m.removed())
        .filter(m -> (m.getDistance(player.x, player.y) <= this.range))
        .filter(m -> shouldBuffMob(item, player, m))
        .forEach(this::applyBuffs);
    } 
  }
  
  public DrawOptions getStandDrawOptions(Level level, int tileX, int tileY, int drawX, int drawY, GameLight light) {
    int anim = GameUtils.getAnim(level.getWorldEntity().getTime() + (tileX * 97) + (tileY * 151), 4, 800);
    int xOffset = 0, yOffset = 0, holdSpriteRes = 64;
    if (this.holdTexture.getWidth() / 128 == 6) {
      xOffset = -32;
      yOffset = -32;
      holdSpriteRes = 128;
    } 
    return (DrawOptions)this.holdTexture.initDraw().sprite(1 + anim, 3, holdSpriteRes).light(light).pos(drawX - 9 + xOffset, drawY - 40 + yOffset + ((anim == 1 || anim == 3) ? 2 : 0));
  }
  
  public String canAttack(Level level, int x, int y, PlayerMob player, InventoryItem item) {
    return "";
  }
  
  public float getSinkingRate(ItemPickupEntity entity, float currentSinking) {
    return super.getSinkingRate(entity, currentSinking) / 5.0F;
  }
  
  public void applyBuffs(Mob mob) {
    Buff buff = this.buff.apply(mob);
    if (buff == null)
      return; 
    ActiveBuff ab = new ActiveBuff(buff, mob, 100, null);
    mob.buffManager.addBuff(ab, false);
  }
  
  public boolean shouldBuffPlayer(InventoryItem item, PlayerMob player, PlayerMob target) {
    return (player == target || player.isSameTeam((Mob)target));
  }
  
  public boolean shouldBuffMob(InventoryItem item, PlayerMob player, Mob target) {
    return true;
  }
  
  public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
    ListGameTooltips tooltips = super.getTooltips(item, perspective);
    tooltips.add(Localization.translate("itemtooltip", "demoneyebanner"));
    return tooltips;
  }
}
