package club.mcams.carpet.api.recipe.template;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.Identifier;

import java.util.Map;

public class SmeltingRecipeTemplate implements RecipeTemplateInterface {
    private final Identifier recipeId;
    private final String ingredient;
    private final String resultItem;
    private final float experience;
    private final int cookingTime;

    public SmeltingRecipeTemplate(Identifier recipeId, String ingredient, String resultItem, float experience, int cookingTime) {
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.resultItem = resultItem;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    @Override
    public JsonObject toJson() {
        JsonObject recipeJson = new JsonObject();
        recipeJson.addProperty("type", "minecraft:smelting");

        JsonObject ingredientJson = new JsonObject();
        ingredientJson.addProperty("item", ingredient);
        recipeJson.add("ingredient", ingredientJson);

        recipeJson.addProperty("result", resultItem);

        recipeJson.addProperty("experience", experience);
        recipeJson.addProperty("cookingtime", cookingTime);

        return recipeJson;
    }

    @Override
    public void addToRecipeMap(Map<Identifier, JsonElement> recipeMap) {
        recipeMap.put(recipeId, toJson());
    }
}
