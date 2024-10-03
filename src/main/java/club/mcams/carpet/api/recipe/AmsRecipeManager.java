package club.mcams.carpet.api.recipe;

import club.mcams.carpet.api.recipe.template.ShapedRecipeTemplate;
import club.mcams.carpet.api.recipe.template.ShapelessRecipeTemplate;
import club.mcams.carpet.api.recipe.template.SmeltingRecipeTemplate;
import com.google.gson.JsonElement;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class AmsRecipeManager {
    private final List<ShapelessRecipeTemplate> shapelessRecipes;
    private final List<ShapedRecipeTemplate> shapedRecipes;
    private final List<SmeltingRecipeTemplate> smeltingRecipes;

    public AmsRecipeManager(AmsRecipeBuilder builder) {
        this.shapelessRecipes = builder.getShapelessRecipeList();
        this.shapedRecipes = builder.getShapedRecipeList();
        this.smeltingRecipes = builder.getSmeltingRecipeList();
    }

    public void registerRecipes(Map<Identifier, JsonElement> recipeMap) {
        registerAllRecipes(recipeMap);
    }

    private void registerAllRecipes(Map<Identifier, JsonElement> recipeMap) {
        shapelessRecipes.forEach(recipe -> recipe.addToRecipeMap(recipeMap));
        shapedRecipes.forEach(recipe -> recipe.addToRecipeMap(recipeMap));
        smeltingRecipes.forEach(recipe -> recipe.addToRecipeMap(recipeMap));
    }

    public static void clearRecipeListMemory(AmsRecipeBuilder amsRecipeBuilder) {
        amsRecipeBuilder.getShapedRecipeList().clear();
        amsRecipeBuilder.getShapelessRecipeList().clear();
        amsRecipeBuilder.getSmeltingRecipeList().clear();
    }
}
