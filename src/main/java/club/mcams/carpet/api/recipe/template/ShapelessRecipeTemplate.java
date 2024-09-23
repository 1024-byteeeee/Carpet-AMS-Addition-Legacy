package club.mcams.carpet.api.recipe.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class ShapelessRecipeTemplate implements RecipeTemplateInterface{
    private final Identifier recipeId;
    private final List<String> ingredients;
    private final String resultItem;
    private final int resultCount;

    public ShapelessRecipeTemplate(Identifier recipeId, List<String> ingredients, String resultItem, int resultCount) {
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.resultItem = resultItem;
        this.resultCount = resultCount;
    }

    @Override
    public JsonObject toJson() {
        JsonObject recipeJson = new JsonObject();
        recipeJson.addProperty("type", "minecraft:crafting_shapeless");

        JsonArray ingredientsJson = new JsonArray();
        for (String ingredient : ingredients) {
            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("item", ingredient);
            ingredientsJson.add(itemJson);
        }
        recipeJson.add("ingredients", ingredientsJson);

        JsonObject resultJson = new JsonObject();
        resultJson.addProperty(this.compatResultItemIdKey(), resultItem);
        resultJson.addProperty("count", resultCount);
        recipeJson.add("result", resultJson);
        return recipeJson;
    }

    @Override
    public void addToRecipeMap(Map<Identifier, JsonElement> recipeMap) {
        recipeMap.put(recipeId, toJson());
    }
}
