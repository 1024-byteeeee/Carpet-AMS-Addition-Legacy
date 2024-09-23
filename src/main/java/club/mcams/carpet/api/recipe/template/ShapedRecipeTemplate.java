package club.mcams.carpet.api.recipe.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.Identifier;

import java.util.Map;

public class ShapedRecipeTemplate implements RecipeTemplateInterface{
    private final Identifier recipeId;
    private final String[][] pattern;
    private final Map<Character, String> ingredients;
    private final String resultItem;
    private final int resultCount;

    public ShapedRecipeTemplate(Identifier recipeId, String[][] pattern, Map<Character, String> ingredients, String resultItem, int resultCount) {
        this.recipeId = recipeId;
        this.pattern = pattern;
        this.ingredients = ingredients;
        this.resultItem = resultItem;
        this.resultCount = resultCount;
    }

    @Override
    public JsonObject toJson() {
        JsonObject recipeJson = new JsonObject();
        recipeJson.addProperty("type", "minecraft:crafting_shaped");

        JsonArray patternJson = new JsonArray();
        for (String[] row : pattern) {
            StringBuilder rowString = new StringBuilder();
            for (String cell : row) {
                rowString.append(cell);
            }
            patternJson.add(rowString.toString());
        }
        recipeJson.add("pattern", patternJson);

        JsonObject keyJson = new JsonObject();
        for (Map.Entry<Character, String> entry : ingredients.entrySet()) {
            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("item", entry.getValue());
            keyJson.add(entry.getKey().toString(), itemJson);
        }
        recipeJson.add("key", keyJson);

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
