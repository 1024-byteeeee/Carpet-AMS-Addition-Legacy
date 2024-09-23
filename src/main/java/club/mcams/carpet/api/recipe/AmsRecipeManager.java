package club.mcams.carpet.api.recipe;

import club.mcams.carpet.api.recipe.template.ShapedRecipeTemplate;
import club.mcams.carpet.api.recipe.template.ShapelessRecipeTemplate;

import com.google.gson.JsonElement;

import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class AmsRecipeManager {
    private final List<ShapelessRecipeTemplate> shapelessRecipes;
    private final List<ShapedRecipeTemplate> shapedRecipes;

    public AmsRecipeManager(List<ShapelessRecipeTemplate> shapelessRecipes, List<ShapedRecipeTemplate> shapedRecipes) {
        this.shapelessRecipes = shapelessRecipes;
        this.shapedRecipes = shapedRecipes;
    }

    public void registerRecipes(Map<Identifier, JsonElement> recipeMap) {
        for (ShapelessRecipeTemplate recipe : shapelessRecipes) {
            recipe.addToRecipeMap(recipeMap);
        }
        for (ShapedRecipeTemplate recipe : shapedRecipes) {
            recipe.addToRecipeMap(recipeMap);
        }
    }

    public static void clearRecipeListMemory(AmsRecipeRegistry amsRecipeRegistry) {
        amsRecipeRegistry.shapedRecipeList.clear();
        amsRecipeRegistry.shapelessRecipeList.clear();
    }
}
