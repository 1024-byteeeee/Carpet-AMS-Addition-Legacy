package club.mcams.carpet.api.recipe.template;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.Identifier;

import java.util.Map;

public interface RecipeTemplateInterface {
    JsonObject toJson();

    void addToRecipeMap(Map<Identifier, JsonElement> recipeMap);

    default String compatResultItemIdKey() {
        //#if MC>=12005
        //$$ return "id";
        //#else
        return "item";
        //#endif
    }
}
