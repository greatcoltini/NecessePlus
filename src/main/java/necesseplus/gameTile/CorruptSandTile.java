package necesseplus.gameTile;

import java.awt.Color;
import java.awt.Point;
import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.maps.Level;
import necesse.level.gameTile.TerrainSplatterTile;

public class CorruptSandTile extends TerrainSplatterTile {
  private final GameRandom drawRandom;
  
  public CorruptSandTile() {
    super(false, "corruptsand");
    this.mapColor = new Color(40, 0, 23);
    this.canBeMined = true;
    this.drawRandom = new GameRandom();
  }
  
  public Point getTerrainSprite(GameTextureSection terrainTexture, Level level, int tileX, int tileY) {
    int tile;
    synchronized (this.drawRandom) {
      tile = this.drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
    } 
    return new Point(0, tile);
  }
  
  public int getTerrainPriority() {
    return 100;
  }
}
