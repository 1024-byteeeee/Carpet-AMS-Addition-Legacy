package club.mcams.carpet;

import club.mcams.carpet.api.recipe.AmsRecipeBuilder;
import club.mcams.carpet.utils.ChainableHashMap;
import club.mcams.carpet.utils.ChainableList;

import net.minecraft.item.Item;

import static net.minecraft.item.Items.*;

public class AmsServerCustomRecipes {
    private static final AmsServerCustomRecipes INSTANCE = new AmsServerCustomRecipes();
    private static final AmsRecipeBuilder builder = AmsRecipeBuilder.getInstance();

    private AmsServerCustomRecipes() {}

    public static AmsServerCustomRecipes getInstance() {
        return INSTANCE;
    }

    public void buildRecipes() {
        /*
         * 有序合成配方
         */
        if (AmsServerSettings.craftableEnchantedGoldenApples) {
            String[][] notchApplePattern = {
                {"#", "#", "#"},
                {"#", "A", "#"},
                {"#", "#", "#"}
            };
            ChainableHashMap<Character, String> notchAppleIngredients = new ChainableHashMap<>();
            notchAppleIngredients.cPut('#', item(GOLD_BLOCK)).cPut('A', item(APPLE));
            builder.addShapedRecipe("enchanted_golden_apple", notchApplePattern, notchAppleIngredients, item(ENCHANTED_GOLDEN_APPLE), 1);
        }

        if (AmsServerSettings.betterCraftableBoneBlock) {
            String[][] boneBlockPattern = {
                {"#", "#", "#"},
                {"#", "#", "#"},
                {"#", "#", "#"}
            };
            ChainableHashMap<Character, String> boneBlockIngredients = new ChainableHashMap<>();
            boneBlockIngredients.cPut('#', item(BONE));
            builder.addShapedRecipe("bone_block", boneBlockPattern, boneBlockIngredients, item(BONE_BLOCK), 3);
        }

        if (AmsServerSettings.betterCraftableDispenser) {
            String[][] dispenserPattern = {
                {" ", "S", "X"},
                {"S", "D", "X"},
                {" ", "S", "X"}
            };
            ChainableHashMap<Character, String> dispenserIngredients = new ChainableHashMap<>();
            dispenserIngredients.cPut('D', item(DROPPER)).cPut('S', item(STICK)).cPut('X', item(STRING));
            builder.addShapedRecipe("dispenser1", dispenserPattern, dispenserIngredients, item(DISPENSER), 1);
        }

        if (AmsServerSettings.craftableElytra) {
            String[][] elytraPattern = {
                {"P", "S", "P"},
                {"P", "*", "P"},
                {"P", "L", "P"}
            };
            ChainableHashMap<Character, String> elytraIngredients = new ChainableHashMap<>();
            elytraIngredients.cPut('P', item(PHANTOM_MEMBRANE)).cPut('S', item(STICK)).cPut('*', item(SADDLE)).cPut('L', item(STRING));
            builder.addShapedRecipe("elytra", elytraPattern, elytraIngredients, item(ELYTRA), 1);
        }

        /*
         * 无序合成配方
         */
        if (AmsServerSettings.betterCraftableDispenser) {
            ChainableList<String> dispenserIngredients = new ChainableList<>();
            dispenserIngredients.cAdd(item(BOW)).cAdd(item(DROPPER));
            builder.addShapelessRecipe("dispenser2", dispenserIngredients, item(DISPENSER), 1);
        }

        /*
         * 熔炉烧炼配方
         */
        if (AmsServerSettings.rottenFleshBurnedIntoLeather) {
            builder.addSmeltingRecipe("leather", item(ROTTEN_FLESH), item(LEATHER), 0.1F, 50);
        }
    }

    private static String item(Item item) {
        return item.toString();
    }
}
