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

        // Register our items
        ItemRegistry.registerItem("spiderqueenhead", new SpiderQueenHead(), 100, true);

    }
    

    // public void initResources() {
    //     ExampleMob.texture = GameTexture.fromFile("mobs/examplemob");
    // }

    public void postInit() {
        // Add recipes
        // Show the recipe after example item recipe
        // Example staff recipe, crafted in workstation using 4 example items and 10 gold bars
        Recipes.registerModRecipe(new Recipe(
            "spiderqueentrophyobject",
            1,
            RecipeTechRegistry.NONE,
            new Ingredient[]{
                    new Ingredient("spiderqueenhead", 1),
                    new Ingredient("oaklog", 5)
            },
            false
        ));
        // Add out example mob to default cave mobs.
        // Spawn tables use a ticket/weight system. In general, common mobs have about 100 tickets.
        // Biome.defaultCaveMobs
        //         .add(100, "examplemob");
    }

}
