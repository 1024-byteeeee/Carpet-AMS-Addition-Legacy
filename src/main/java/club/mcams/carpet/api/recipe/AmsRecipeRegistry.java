package club.mcams.carpet.api.recipe;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.api.recipe.template.ShapedRecipeTemplate;
import club.mcams.carpet.api.recipe.template.ShapelessRecipeTemplate;
import club.mcams.carpet.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmsRecipeRegistry {
    private static final String MOD_ID = AmsServer.compactName;
    private static final AmsRecipeRegistry instance = new AmsRecipeRegistry();
    public List<ShapedRecipeTemplate> shapedRecipeList = new ArrayList<>();
    public List<ShapelessRecipeTemplate> shapelessRecipeList = new ArrayList<>();

    public static AmsRecipeRegistry getInstance() {
        return instance;
    }

    public void register() {
        // Shaped Recipe List
        if (AmsServerSettings.craftableEnchantedGoldenApples) {
            String[][] enchantedGoldenApplePattern = {
                {"#", "#", "#"},
                {"#", "A", "#"},
                {"#", "#", "#"}
            };
            Map<Character, String> enchantedGoldenAppleIngredients = new HashMap<>();
            enchantedGoldenAppleIngredients.put('#', "minecraft:gold_block");
            enchantedGoldenAppleIngredients.put('A', "minecraft:apple");
            shapedRecipeList.add(new ShapedRecipeTemplate(
                IdentifierUtil.of(MOD_ID, "enchanted_golden_apple"),
                enchantedGoldenApplePattern,
                enchantedGoldenAppleIngredients,
                "minecraft:enchanted_golden_apple",
                1
            ));
        }

        if (AmsServerSettings.betterCraftableBoneBlock) {
            String[][] bonePattern = {
                {"#", "#", "#"},
                {"#", "#", "#"},
                {"#", "#", "#"}
            };
            Map<Character, String> boneBlockIngredients = new HashMap<>();
            boneBlockIngredients.put('#', "minecraft:bone");
            shapedRecipeList.add(new ShapedRecipeTemplate(
                IdentifierUtil.of(MOD_ID, "bone_block"),
                bonePattern,
                boneBlockIngredients,
                "minecraft:bone_block",
                3
            ));
        }

        if (AmsServerSettings.betterCraftableDispenser) {
            String[][] dispenserPattern = {
                {" ", "S", "X"},
                {"S", "D", "X"},
                {" ", "S", "X"}
            };
            Map<Character, String> dispenserIngredients = new HashMap<>();
            dispenserIngredients.put('D', "minecraft:dropper");
            dispenserIngredients.put('S', "minecraft:stick");
            dispenserIngredients.put('X', "minecraft:string");
            shapedRecipeList.add(new ShapedRecipeTemplate(
                IdentifierUtil.of(MOD_ID, "dispenser1"),
                dispenserPattern,
                dispenserIngredients,
                "minecraft:dispenser",
                1
            ));
        }

        if (AmsServerSettings.craftableElytra) {
            String[][] elytraPattern = {
                {"P", "S", "P"},
                {"P", "*", "P"},
                {"P", "L", "P"}
            };
            Map<Character, String> elytraIngredients = new HashMap<>();
            elytraIngredients.put('P', "minecraft:phantom_membrane");
            elytraIngredients.put('S', "minecraft:stick");
            elytraIngredients.put('*', "minecraft:saddle");
            elytraIngredients.put('L', "minecraft:string");
            shapedRecipeList.add(new ShapedRecipeTemplate(
                IdentifierUtil.of(MOD_ID, "elytra"),
                elytraPattern,
                elytraIngredients,
                "minecraft:elytra",
                1
            ));
        }

        // Shapeless Recipe List
        if (AmsServerSettings.betterCraftableDispenser) {
            List<String> dispenserIngredients = List.of(
                "minecraft:bow",
                "minecraft:dropper"
            );
            shapelessRecipeList.add(new ShapelessRecipeTemplate(
                IdentifierUtil.of(MOD_ID, "dispenser2"),
                dispenserIngredients,
                "minecraft:dispenser",
                1
            ));
        }
    }
}
