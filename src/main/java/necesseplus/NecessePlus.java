package necesseplus;

import necesseplus.item.*;
import necesseplus.object.*;
import net.bytebuddy.dynamic.TypeResolutionStrategy.Active;
import necesseplus.lootpatch.*;
import necesseplus.mob.hostile.*;
import necesseplus.mob.hostile.bosses.EyeOfCthulu;
import necesseplus.mobs.buffs.staticbuffs.*;
import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.miscItem.CoinItem;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.maps.biomes.Biome;
import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.item.Item.Rarity;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.VicinityBuff;

@ModEntry
public class NecessePlus {

    public void init() {

        // Register out objects
        ObjectRegistry.registerObject("spiderqueentrophyobject", new SpiderQueenTrophyObject(), 200, true);
        ObjectRegistry.registerObject("evilsprotectortrophyobject", new EvilsProtectorTrophyObject(), 175, true);
        ObjectRegistry.registerObject("voidwizardtrophyobject", new VoidWizardTrophyObject(), 210, true);
        ObjectRegistry.registerObject("ancientvulturetrophyobject", new AncientVultureTrophyObject(), 235, true);
        ObjectRegistry.registerObject("swampguardiantrophyobject", new SwampGuardianTrophyObject(), 235, true);
        ObjectRegistry.registerObject("piratecaptaintrophyobject", new PirateCaptainTrophyObject(), 225, true);
        ObjectRegistry.registerObject("demoneyebannerstand", new DemonEyeBannerStand(), 45, true);
        ObjectRegistry.registerObject("trophycase", new TrophyCase(), 20, true);

        // register buffs
        DemonEyeBannerBuff itemBuff = BuffRegistry.registerBuff("demoneyebanner", new DemonEyeBannerBuff());

        // Register our items
        ItemRegistry.registerItem("spiderqueenhead", new SpiderQueenHead(), 100, true);
        ItemRegistry.registerItem("evilsprotectorhead", new EvilsProtectorHead(), 90, true);
        ItemRegistry.registerItem("voidwizardbeard", new VoidWizardBeard(), 110, true);
        ItemRegistry.registerItem("piratecaptainflag", new PirateCaptainFlag(), 100, true);
        ItemRegistry.registerItem("swampguardianskin", new SwampGuardianSkin(), 90, true);
        ItemRegistry.registerItem("ancientvulturehead", new AncientVultureHead(), 110, true);
        ItemRegistry.registerItem("demoneyebanner", new DemonEyeBanner(Rarity.COMMON, 480, itemBuff), 200.0F, true);
        ItemRegistry.registerItem("lens", new Lens(), 10, true);

        // Register our mob
        MobRegistry.registerMob("demoneye", DemonEye.class, true);
        MobRegistry.registerMob("eyeofcthulu", EyeOfCthulu.class, true);

    }

    public void initResources() {
        DemonEye.texture = GameTexture.fromFile("mobs/demoneyesheet");
        EyeOfCthulu.texture = GameTexture.fromFile("mobs/eyeofcthulusheet2");
    }

    public void postInit() {
        // Add recipes

        // Trophy Case
        Recipes.registerModRecipe(new Recipe(
            "trophycase",
            1,
            RecipeTechRegistry.WORKSTATION,
            new Ingredient[]{
                new Ingredient("oaklog", 5)
            },
            true
        ));

        // Spider Queen Trophy
        Recipes.registerModRecipe(new Recipe(
            "spiderqueentrophyobject",
            1,
            RecipeTechRegistry.CARPENTER,
            new Ingredient[]{
                    new Ingredient("spiderqueenhead", 1),
                    new Ingredient("trophycase", 1)
            },
            true
        ));

        // Evils Protector Trophy
        Recipes.registerModRecipe(new Recipe(
            "evilsprotectortrophyobject",
            1,
            RecipeTechRegistry.CARPENTER,
            new Ingredient[]{
                    new Ingredient("evilsprotectorhead", 1),
                    new Ingredient("trophycase", 1)
            },
            true
        ));

        // Void Wizard Trophy
        Recipes.registerModRecipe(new Recipe(
            "voidwizardtrophyobject",
            1,
            RecipeTechRegistry.CARPENTER,
            new Ingredient[]{
                    new Ingredient("voidwizardbeard", 1),
                    new Ingredient("trophycase", 1)
            },
            true
        ));

        // Swamp Guardian Trophy
        Recipes.registerModRecipe(new Recipe(
            "swampguardiantrophyobject",
            1,
            RecipeTechRegistry.CARPENTER,
            new Ingredient[]{
                    new Ingredient("swampguardianskin", 1),
                    new Ingredient("trophycase", 1)
            },
            true
        ));

        // Ancient Vulture Trophy
        Recipes.registerModRecipe(new Recipe(
            "ancientvulturetrophyobject",
            1,
            RecipeTechRegistry.CARPENTER,
            new Ingredient[]{
                    new Ingredient("ancientvulturehead", 1),
                    new Ingredient("trophycase", 1)
            },
            true
        ));

        // Pirate Captain Trophy
        Recipes.registerModRecipe(new Recipe(
            "piratecaptaintrophyobject",
            1,
            RecipeTechRegistry.CARPENTER,
            new Ingredient[]{
                    new Ingredient("piratecaptainflag", 1),
                    new Ingredient("trophycase", 1)
            },
            true
        ));

        // Demon Eye Banner
        Recipes.registerModRecipe(new Recipe(
            "demoneyebannerstand",
            1,
            RecipeTechRegistry.CARPENTER,
            new Ingredient[]{
                    new Ingredient("demoneyebanner", 1),
                    new Ingredient("oaklog", 5)
            },
            false
        ));

        // recipe for handheld banner demon eye
        Recipes.registerModRecipe(new Recipe(
            "demoneyebanner",
            1,
            RecipeTechRegistry.WORKSTATION,
            new Ingredient[] {
                new Ingredient("lens", 5)
            },
            true
        ));

        // add mob spawn
        Biome.defaultSurfaceMobs.add(60, "demoneye");


    }

}
