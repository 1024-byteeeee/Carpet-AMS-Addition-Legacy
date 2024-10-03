package club.mcams.carpet.api.recipe;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.api.recipe.template.ShapedRecipeTemplate;
import club.mcams.carpet.api.recipe.template.ShapelessRecipeTemplate;
import club.mcams.carpet.api.recipe.template.SmeltingRecipeTemplate;
import club.mcams.carpet.utils.ChainableHashMap;
import club.mcams.carpet.utils.ChainableList;
import club.mcams.carpet.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.List;

public class AmsRecipeBuilder {
    private static final String MOD_ID = AmsServer.compactName;
    private static final AmsRecipeBuilder INSTANCE = new AmsRecipeBuilder();
    private static final List<ShapedRecipeTemplate> shapedRecipeList = new ArrayList<>();
    private static final List<ShapelessRecipeTemplate> shapelessRecipeList = new ArrayList<>();
    private static final List<SmeltingRecipeTemplate> smeltingRecipeList = new ArrayList<>();

    private AmsRecipeBuilder() {}

    public static AmsRecipeBuilder getInstance() {
        return INSTANCE;
    }

    public List<ShapedRecipeTemplate> getShapedRecipeList() {
        return shapedRecipeList;
    }

    public List<ShapelessRecipeTemplate> getShapelessRecipeList() {
        return shapelessRecipeList;
    }

    public List<SmeltingRecipeTemplate> getSmeltingRecipeList() {
        return smeltingRecipeList;
    }

    public void addShapedRecipe(String id, String[][] pattern, ChainableHashMap<Character, String> ingredients, String result, int count) {
        shapedRecipeList.add(new ShapedRecipeTemplate(IdentifierUtil.of(MOD_ID, id), pattern, ingredients, result, count));
    }

    public void addShapelessRecipe(String id, ChainableList<String> ingredients, String result, int count) {
        shapelessRecipeList.add(new ShapelessRecipeTemplate(IdentifierUtil.of(MOD_ID, id), ingredients, result, count));
    }

    public void addSmeltingRecipe(String id, String input, String output, float experience, int cookingTime) {
        smeltingRecipeList.add(new SmeltingRecipeTemplate(IdentifierUtil.of(MOD_ID, id), input, output, experience, cookingTime));
    }
}
