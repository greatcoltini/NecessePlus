package necesseplus.biomes.corruption;

import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;
import necesse.engine.GameEvents;
import necesse.engine.events.GameEvent;
import necesse.engine.events.PreventableGameEvent;
import necesse.engine.events.worldGeneration.GenerateCaveLayoutEvent;
import necesse.engine.events.worldGeneration.GenerateCaveMiniBiomesEvent;
import necesse.engine.events.worldGeneration.GenerateCaveOresEvent;
import necesse.engine.events.worldGeneration.GenerateCaveStructuresEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveLayoutEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveMiniBiomesEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveOresEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveStructuresEvent;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.maps.Level;
import necesse.level.maps.generationModules.CaveGeneration;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.PresetGeneration;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.RandomCaveChestRoom;
import necesse.level.maps.presets.caveRooms.CaveRuins;
import necesse.level.maps.presets.modularPresets.abandonedMinePreset.AbandonedMinePreset;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.FurnitureSet;
import necesse.level.maps.presets.set.WallSet;

public class CorruptionDeepCaveLevel extends Level {
  public CorruptionDeepCaveLevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
    super(identifier, width, height, worldEntity);
  }
  
  public CorruptionDeepCaveLevel(int islandX, int islandY, int dimension, WorldEntity worldEntity) {
    super(new LevelIdentifier(islandX, islandY, dimension), 300, 300, worldEntity);
    this.isCave = true;
    generateLevel();
  }
  
  public void generateLevel() {
    int deepRockTile = TileRegistry.getTileID("deeprocktile");
    CaveGeneration cg = new CaveGeneration(this, "deeprocktile", "deeprock");
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateCaveLayoutEvent(this, cg), e -> cg.generateLevel(0.44F, 4, 3, 6));
    GameEvents.triggerEvent((GameEvent)new GeneratedCaveLayoutEvent(this, cg));
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateCaveMiniBiomesEvent(this, cg), e -> {
          GenerationTools.generateRandomObjectVeinsOnTile(this, cg.random, 0.2F, 4, 8, deepRockTile, ObjectRegistry.getObjectID("wildcaveglow"), 0.2F, false);
          GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.07F, 2, 7.0F, 20.0F, 3.0F, 8.0F, TileRegistry.getTileID("lavatile"), 1.0F, true);
          this.liquidManager.calculateShores();
          cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("deepcaverock"), 0.005F);
          cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("deepcaverocksmall"), 0.01F);
        });
    GameEvents.triggerEvent((GameEvent)new GeneratedCaveMiniBiomesEvent(this, cg));
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateCaveOresEvent(this, cg), e -> {
          cg.generateOreVeins(0.05F, 3, 6, ObjectRegistry.getObjectID("copperoredeeprock"));
          cg.generateOreVeins(0.25F, 3, 6, ObjectRegistry.getObjectID("ironoredeeprock"));
          cg.generateOreVeins(0.15F, 3, 6, ObjectRegistry.getObjectID("goldoredeeprock"));
          cg.generateOreVeins(0.25F, 5, 10, ObjectRegistry.getObjectID("obsidianrock"));
          cg.generateOreVeins(0.15F, 3, 6, ObjectRegistry.getObjectID("tungstenoredeeprock"));
          cg.generateOreVeins(0.05F, 3, 6, ObjectRegistry.getObjectID("lifequartzdeeprock"));
        });
    GameEvents.triggerEvent((GameEvent)new GeneratedCaveOresEvent(this, cg));
    PresetGeneration presets = new PresetGeneration(this);
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateCaveStructuresEvent(this, cg, presets), e -> {
          int abandonedMineCount = cg.random.getIntBetween(2, 3);
          for (int i = 0; i < abandonedMineCount; i++) {
            Rectangle abandonedMineRec = AbandonedMinePreset.generateAbandonedMineOnLevel(this, cg.random, presets.getOccupiedSpace());
            if (abandonedMineRec != null)
              presets.addOccupiedSpace(abandonedMineRec); 
          } 
          AtomicInteger chestRoomRotation = new AtomicInteger();
          int chestRoomAmount = cg.random.getIntBetween(13, 18);
          for (int j = 0; j < chestRoomAmount; j++) {
            RandomCaveChestRoom randomCaveChestRoom = new RandomCaveChestRoom(cg.random, LootTablePresets.deepCaveChest, chestRoomRotation, new ChestRoomSet[] { ChestRoomSet.deepStone, ChestRoomSet.obsidian });
            randomCaveChestRoom.replaceTile(TileRegistry.deepStoneFloorID, ((Integer)cg.random.getOneOf((Object[])new Integer[] { Integer.valueOf(TileRegistry.deepStoneFloorID), Integer.valueOf(TileRegistry.deepStoneBrickFloorID) })).intValue());
            presets.findRandomValidPositionAndApply(cg.random, 5, (Preset)randomCaveChestRoom, 10, true, true);
          } 
          AtomicInteger caveRuinsRotation = new AtomicInteger();
          int caveRuinsCount = cg.random.getIntBetween(25, 35);
          for (int k = 0; k < caveRuinsCount; k++) {
            WallSet wallSet = (WallSet)cg.random.getOneOf((Object[])new WallSet[] { WallSet.deepStone, WallSet.obsidian });
            FurnitureSet furnitureSet = (FurnitureSet)cg.random.getOneOf((Object[])new FurnitureSet[] { FurnitureSet.oak, FurnitureSet.spruce });
            String floorStringID = (String)cg.random.getOneOf((Object[])new String[] { "deepstonefloor", "deepstonebrickfloor" });
            Preset room = ((CaveRuins.CaveRuinGetter)cg.random.getOneOf(CaveRuins.caveRuinGetters)).get(cg.random, wallSet, furnitureSet, floorStringID, LootTablePresets.basicDeepCaveRuinsChest, caveRuinsRotation);
            presets.findRandomValidPositionAndApply(cg.random, 5, room, 10, true, true);
          } 
          cg.generateRandomCrates(0.03F, new int[] { ObjectRegistry.getObjectID("crate") });
        });
    GameEvents.triggerEvent((GameEvent)new GeneratedCaveStructuresEvent(this, cg, presets));
    GenerationTools.checkValid(this);
  }
}
