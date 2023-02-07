package necesseplus;

import necesseplus.item.*;
import necesseplus.object.*;
// import necesseplus.lootpatch.*;
import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.maps.biomes.Biome;

@ModEntry
public class NecessePlus {

    public void init() {
        System.out.println("Hello world from my example mod!");

        // Register out objects
        ObjectRegistry.registerObject("spiderqueenobject", new SpiderQueenTrophyObject(), 2, true);

        // Register our items
        ItemRegistry.registerItem("spiderqueentrophy", new SpiderQueenTrophy(), 10, true);
    }

    // public void initResources() {
    //     ExampleMob.texture = GameTexture.fromFile("mobs/examplemob");
    // }

    public void postInit() {
        // Add recipes
        // Example staff recipe, crafted in workstation using 4 example items and 10 gold bars
        Recipes.registerModRecipe(new Recipe(
                "spiderqueentrophy",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("wood", 1),
                }
        )); // Show the recipe after example item recipe

        // Add out example mob to default cave mobs.
        // Spawn tables use a ticket/weight system. In general, common mobs have about 100 tickets.
        // Biome.defaultCaveMobs
        //         .add(100, "examplemob");
    }

}
