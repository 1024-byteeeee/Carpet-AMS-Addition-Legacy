package club.mcams.carpet.observers.rule.recipeRule;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.helpers.rule.recipeRule.RecipeRuleHelper;
import club.mcams.carpet.settings.SimpleRuleObserver;

public class RecipeRuleObserver extends SimpleRuleObserver<Boolean> {
    @Override
    public void onValueChange(Boolean oldValue, Boolean newValue) {
        RecipeRuleHelper.onValueChange(AmsServer.minecraftServer);
    }
}
