package necesseplus;

import necesseplus.item.*;
import necesseplus.object.*;
import necesseplus.lootpatch.*;
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

@ModEntry
public class NecessePlus {

    public void init() {

        // Register out objects
        ObjectRegistry.registerObject("spiderqueentrophyobject", new SpiderQueenTrophyObject(), 200, true);
        ObjectRegistry.registerObject("evilsprotectortrophyobject", new EvilsProtectorTrophyObject(), 175, true);
        ObjectRegistry.registerObject("trophycaseobject", new TrophyCaseObject(), 20, true);

        // Register our items
        ItemRegistry.registerItem("spiderqueenhead", new SpiderQueenHead(), 100, true);
        ItemRegistry.registerItem("evilsprotectorhead" new EvilsProtectorHead(), 90, true);
        ItemRegistry.registerItem("trophycase", new TrophyCase(), 20, true);

    }

    public void postInit() {
        // Add recipes
        // Show the recipe after example item recipe
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
            RecipeTechRegistry.NONE,
            new Ingredient[]{
                    new Ingredient("spiderqueenhead", 1),
                    new Ingredient("trophycase", 1)
            },
            false
        ));
        // Evils Protector Trophy
        Recipes.registerModRecipe(new Recipe(
            "evilsprotectortrophyobject",
            1,
            RecipeTechRegistry.NONE,
            new Ingredient[]{
                    new Ingredient("evilsprotectorhead", 1),
                    new Ingredient("trophycase", 1)
            },
            false
        ));

    }

}
