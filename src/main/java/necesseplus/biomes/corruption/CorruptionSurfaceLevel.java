package necesseplus.biomes.corruption;

import necesse.engine.GameEvents;
import necesse.engine.events.GameEvent;
import necesse.engine.events.PreventableGameEvent;
import necesse.engine.events.worldGeneration.GenerateIslandAnimalsEvent;
import necesse.engine.events.worldGeneration.GenerateIslandFloraEvent;
import necesse.engine.events.worldGeneration.GenerateIslandLayoutEvent;
import necesse.engine.events.worldGeneration.GenerateIslandStructuresEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandAnimalsEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandFloraEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandLayoutEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandStructuresEvent;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.IslandGeneration;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.RandomRuinsPreset;

public class CorruptionSurfaceLevel extends Level {
  public CorruptionSurfaceLevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
    super(identifier, width, height, worldEntity);
  }
  
  public CorruptionSurfaceLevel(int islandX, int islandY, float islandSize, WorldEntity worldEntity) {
    super(new LevelIdentifier(islandX, islandY, 0), 300, 300, worldEntity);
    generateLevel(islandSize);
  }
  
  public void generateLevel(float islandSize) {
    int size = (int)(islandSize * 100.0F) + 20;
    IslandGeneration ig = new IslandGeneration(this, size);
    int waterTile = TileRegistry.getTileID("watertile");
    int sandTile = TileRegistry.getTileID("sandtile");
    int grassTile = TileRegistry.getTileID("corruptgrasstile");
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateIslandLayoutEvent(this, islandSize, ig), e -> {
          ig.generateSimpleIsland(this.width / 2, this.height / 2, waterTile, grassTile, sandTile);
          ig.generateRiver(waterTile, grassTile, sandTile);
          ig.generateLakes(0.01F, waterTile, grassTile, sandTile);
          this.liquidManager.calculateHeights();
        });
    GameEvents.triggerEvent((GameEvent)new GeneratedIslandLayoutEvent(this, islandSize, ig));
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateIslandFloraEvent(this, islandSize, ig), e -> {
          int corruptTree = ObjectRegistry.getObjectID("corrupttree");
          int grassObject = ObjectRegistry.getObjectID("grass");
          ig.generateCellMapObjects(0.32F, corruptTree, grassTile, 0.07F);
          ig.generateObjects(grassObject, grassTile, 0.2F);
          ig.generateObjects(ObjectRegistry.getObjectID("surfacerock"), -1, 0.001F, false);
          ig.generateObjects(ObjectRegistry.getObjectID("surfacerocksmall"), -1, 0.002F, false);
          ig.ensureGenerateObjects("beehive", 2, new int[] { grassTile });
          ig.generateFruitGrowerSingle("appletree", 0.01F, new int[] { grassTile });
          ig.generateFruitGrowerVeins("blueberrybush", 0.04F, 8, 10, 0.1F, new int[] { grassTile });
          GenerationTools.generateRandomObjectVeinsOnTile(this, ig.random, 0.1F, 5, 10, grassTile, ObjectRegistry.getObjectID("wildsunflower"), 0.15F, false);
          GameObject waterPlant = ObjectRegistry.getObject(ObjectRegistry.getObjectID("watergrass"));
          // GenerationTools.generateRandomVeins(this, ig.random, 0.15F, 12, 20, ());
        });
    GameEvents.triggerEvent((GameEvent)new GeneratedIslandFloraEvent(this, islandSize, ig));
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateIslandStructuresEvent(this, islandSize, ig), e -> GenerationTools.spawnRandomPreset(this, (Preset)new RandomRuinsPreset(ig.random), false, false, ig.random, false, 40, 2));
    GameEvents.triggerEvent((GameEvent)new GeneratedIslandStructuresEvent(this, islandSize, ig));
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateIslandAnimalsEvent(this, islandSize, ig), e -> {
          ig.spawnMobHerds("sheep", ig.random.getIntBetween(25, 50), grassTile, 2, 6, islandSize);
          ig.spawnMobHerds("cow", ig.random.getIntBetween(15, 40), grassTile, 2, 6, islandSize);
        });
    GameEvents.triggerEvent((GameEvent)new GeneratedIslandAnimalsEvent(this, islandSize, ig));
    GenerationTools.checkValid(this);
  }
}
