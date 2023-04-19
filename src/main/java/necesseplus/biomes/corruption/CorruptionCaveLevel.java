package necesseplus.biomes.corruption;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameLinkedList;
import necesse.engine.util.GameMath;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.entity.mobs.Mob;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.maps.Level;
import necesse.level.maps.generationModules.CaveGeneration;
import necesse.level.maps.generationModules.CellAutomaton;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.LinesGeneration;
import necesse.level.maps.generationModules.PresetGeneration;
import necesse.level.maps.generationModules.GenerationTools.PlaceFunction;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.PresetUtils;
import necesse.level.maps.presets.RandomCaveChestRoom;
import necesse.level.maps.presets.RandomLootAreaPreset;
import necesse.level.maps.presets.caveRooms.CaveRuins;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.FurnitureSet;
import necesse.level.maps.presets.set.WallSet;

public class CorruptionCaveLevel extends Level {
  public CorruptionCaveLevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
    super(identifier, width, height, worldEntity);
  }
  
  public CorruptionCaveLevel(int islandX, int islandY, int dimension, WorldEntity worldEntity) {
    super(new LevelIdentifier(islandX, islandY, dimension), 300, 300, worldEntity);
    this.isCave = true;
    generateLevel();
  }
  
  public void generateLevel() {
    CaveGeneration cg = new CaveGeneration(this, "rocktile", "rock");
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateCaveLayoutEvent(this, cg), e -> cg.generateLevel());
    GameEvents.triggerEvent((GameEvent)new GeneratedCaveLayoutEvent(this, cg));
    int crate = ObjectRegistry.getObjectID("crate");
    int trackObject = ObjectRegistry.getObjectID("minecarttrack");
    LinkedList<Mob> minecartsGenerated = new LinkedList<>();
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateCaveMiniBiomesEvent(this, cg), e -> {
        //   GenerationTools.generateRandomSmoothVeinsL(this, cg.random, 0.01F, 5, 7.0F, 15.0F, 3.0F, 5.0F, ()); 
        //   GenerationTools.generateRandomPoints(this, cg.random, 0.01F, 15, ());
          AtomicInteger cryptRotation = new AtomicInteger();
        //   GenerationTools.generateRandomSmoothVeinsL(this, cg.random, 0.015F, 2, 3.0F, 5.0F, 8.0F, 10.0F, ());
          GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.02F, 2, 2.0F, 10.0F, 2.0F, 4.0F, TileRegistry.getTileID("lavatile"), 1.0F, true);
          GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.02F, 2, 2.0F, 10.0F, 2.0F, 4.0F, TileRegistry.getTileID("watertile"), 1.0F, true);
          this.liquidManager.calculateShores();
          cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("caverock"), 0.005F);
          cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("caverocksmall"), 0.01F);
        });
    GameEvents.triggerEvent((GameEvent)new GeneratedCaveMiniBiomesEvent(this, cg));
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateCaveOresEvent(this, cg), e -> {
          cg.generateOreVeins(0.25F, 5, 10, ObjectRegistry.getObjectID("clayrock"));
          cg.generateOreVeins(0.45F, 3, 6, ObjectRegistry.getObjectID("copperorerock"));
          cg.generateOreVeins(0.35F, 3, 6, ObjectRegistry.getObjectID("ironorerock"));
          cg.generateOreVeins(0.1F, 3, 6, ObjectRegistry.getObjectID("goldorerock"));
        });
    GameEvents.triggerEvent((GameEvent)new GeneratedCaveOresEvent(this, cg));
    PresetGeneration presets = new PresetGeneration(this);
    GameEvents.triggerEvent((PreventableGameEvent)new GenerateCaveStructuresEvent(this, cg, presets), e -> {
          AtomicInteger chestRoomRotation = new AtomicInteger();
          int chestRoomAmount = cg.random.getIntBetween(16, 20);
          for (int i = 0; i < chestRoomAmount; i++) {
            RandomCaveChestRoom randomCaveChestRoom = new RandomCaveChestRoom(cg.random, LootTablePresets.basicCaveChest, chestRoomRotation, new ChestRoomSet[] { ChestRoomSet.stone, ChestRoomSet.wood });
            randomCaveChestRoom.replaceTile(TileRegistry.stoneFloorID, ((Integer)cg.random.getOneOf((Object[])new Integer[] { Integer.valueOf(TileRegistry.stoneFloorID), Integer.valueOf(TileRegistry.stoneBrickFloorID) })).intValue());
            presets.findRandomValidPositionAndApply(cg.random, 5, (Preset)randomCaveChestRoom, 10, true, true);
          } 
          int lootAreaAmount = cg.random.getIntBetween(5, 10);
          for (int j = 0; j < lootAreaAmount; j++) {
            RandomLootAreaPreset randomLootAreaPreset = new RandomLootAreaPreset(cg.random, 15, "stonecolumn", new String[] { "goblin" });
            presets.findRandomValidPositionAndApply(cg.random, 5, (Preset)randomLootAreaPreset, 10, true, true);
          } 
          AtomicInteger caveRuinsRotation = new AtomicInteger();
          int caveRuinsCount = cg.random.getIntBetween(25, 35);
          for (int k = 0; k < caveRuinsCount; k++) {
            WallSet wallSet = (WallSet)cg.random.getOneOf((Object[])new WallSet[] { WallSet.stone, WallSet.wood });
            FurnitureSet furnitureSet = (FurnitureSet)cg.random.getOneOf((Object[])new FurnitureSet[] { FurnitureSet.oak, FurnitureSet.spruce });
            String floorStringID = (String)cg.random.getOneOf((Object[])new String[] { "woodfloor", "woodfloor", "stonefloor", "stonebrickfloor" });
            Preset room = ((CaveRuins.CaveRuinGetter)cg.random.getOneOf(CaveRuins.caveRuinGetters)).get(cg.random, wallSet, furnitureSet, floorStringID, LootTablePresets.basicCaveRuinsChest, caveRuinsRotation);
            presets.findRandomValidPositionAndApply(cg.random, 5, room, 10, true, true);
          } 
          cg.generateRandomCrates(0.03F, new int[] { crate });
        });
    GameEvents.triggerEvent((GameEvent)new GeneratedCaveStructuresEvent(this, cg, presets));
    GenerationTools.checkValid(this);
    for (Mob mob : minecartsGenerated) {
      if (getObjectID(mob.getTileX(), mob.getTileY()) != trackObject)
        mob.remove(); 
    } 
  }
}
