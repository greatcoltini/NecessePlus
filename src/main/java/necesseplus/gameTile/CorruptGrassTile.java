package necesseplus.gameTile;

import java.awt.Color;
import java.awt.Point;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.layers.SimulatePriorityList;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.gameTile.GrassTile;

public class CorruptGrassTile extends TerrainSplatterTile {
  public static double growChance = GameMath.getAverageSuccessRuns(4900.0D);
  
  public static double spreadChance = GameMath.getAverageSuccessRuns(550.0D);
  
  private final GameRandom drawRandom;
  
  public CorruptGrassTile() {
    super(false, "corruptgrass");
    this.mapColor = new Color(80, 108, 55);
    this.canBeMined = true;
    this.drawRandom = new GameRandom();
  }
  
  public LootTable getLootTable(Level level, int tileX, int tileY) {
    return new LootTable(new LootItemInterface[] { (LootItemInterface)new ChanceLootItem(0.04F, "swampgrassseed") });
  }
  
  public void addSimulateLogic(Level level, int x, int y, long ticks, SimulatePriorityList list, boolean sendChanges) {
    GrassTile.addSimulateGrow(level, x, y, growChance, ticks, "corruptgrass", list, sendChanges);
  }
  
  public double spreadToDirtChance() {
    return spreadChance;
  }
  
  // public void tick(Level level, int x, int y) {
  //   if (!level.isServerLevel())
  //     return; 
  //   if (level.getObjectID(x, y) == 0 && 
  //     GameRandom.globalRandom.getChance(growChance)) {
  //     GameObject grass = ObjectRegistry.getObject(ObjectRegistry.getObjectID("corruptgrass"));
  //     if (grass.canPlace(level, x, y, 0) == null) {
  //       grass.placeObject(level, x, y, 0);
  //       level.sendObjectUpdatePacket(x, y);
  //     } 
  //   } 
  // }
  
  public Point getTerrainSprite(GameTextureSection terrainTexture, Level level, int tileX, int tileY) {
    int tile;
    synchronized (this.drawRandom) {
      tile = this.drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
    } 
    return new Point(0, tile);
  }
  
  public int getTerrainPriority() {
    return 0;
  }
}
