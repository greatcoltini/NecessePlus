package necesseplus.biomes.corruption;

import necesse.engine.network.server.Server;
import necesse.engine.world.WorldEntity;
import necesse.entity.mobs.Mob;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.biomes.FishingLootTable;
import necesse.level.maps.biomes.FishingSpot;
import necesse.level.maps.biomes.MobSpawnTable;

public class CorruptionBiome extends Biome {

  public static MobSpawnTable defaultSurfaceCritters = (new MobSpawnTable())
    .add(100, "eater");
  
  public static MobSpawnTable caveCritters = (new MobSpawnTable())
    .include(Biome.defaultCaveCritters)
    .add(100, "stonecaveling");
  
  public static MobSpawnTable deepCaveCritters = (new MobSpawnTable())
    .include(Biome.defaultCaveCritters)
    .add(100, "deepstonecaveling");
  
  public static FishingLootTable forestSurfaceFish = (new FishingLootTable(defaultSurfaceFish))
    .addWater(120, "furfish");
  
  public static LootItemInterface randomPortalDrop = (LootItemInterface)new LootItemList(new LootItemInterface[] { (LootItemInterface)new ChanceLootItem(0.01F, "mysteriousportal") });
  
  public static LootItemInterface randomShadowGateDrop = (LootItemInterface)new LootItemList(new LootItemInterface[] { (LootItemInterface)new ChanceLootItem(0.004F, "shadowgate") });
  
  public Level getNewSurfaceLevel(int islandX, int islandY, float islandSize, Server server, WorldEntity worldEntity) {
    return new CorruptionSurfaceLevel(islandX, islandY, islandSize, worldEntity);
  }
  
  public Level getNewCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
    return new CorruptionCaveLevel(islandX, islandY, dimension, worldEntity);
  }
  
  public Level getNewDeepCaveLevel(int islandX, int islandY, int dimension, Server server, WorldEntity worldEntity) {
    return new CorruptionDeepCaveLevel(islandX, islandY, dimension, worldEntity);
  }
  
  public MobSpawnTable getCritterSpawnTable(Level level) {
    if (level.isCave) {
      if (level.getIslandDimension() == -2)
        return deepCaveCritters; 
      return caveCritters;
    } 
    return defaultSurfaceCritters;
  }
  
  public FishingLootTable getFishingLootTable(FishingSpot spot) {
    if (!spot.tile.level.isCave)
      return forestSurfaceFish; 
    return super.getFishingLootTable(spot);
  }
  
  public LootTable getExtraMobDrops(Mob mob) {
    if (mob.isHostile && !mob.isBoss() && !mob.isSummoned) {
      if (mob.getLevel().getIslandDimension() == -1)
        return new LootTable(new LootItemInterface[] { randomPortalDrop, (LootItemInterface)super
              
              .getExtraMobDrops(mob) }); 
      if (mob.getLevel().getIslandDimension() == -2)
        return new LootTable(new LootItemInterface[] { randomShadowGateDrop, (LootItemInterface)super
              
              .getExtraMobDrops(mob) }); 
    } 
    return super.getExtraMobDrops(mob);
  }
}
