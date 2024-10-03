package club.mcams.carpet.helpers.rule.recipeRule;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.AmsServerCustomRecipes;
import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.api.recipe.AmsRecipeBuilder;
import club.mcams.carpet.api.recipe.AmsRecipeManager;
import club.mcams.carpet.settings.RecipeRule;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.util.Collection;

public class RecipeRuleHelper {
    public static void onPlayerLoggedIn(MinecraftServer server, ServerPlayerEntity player) {
        if (hasActiveRecipeRule()) {
            RecipeManager recipeManager = server.getRecipeManager();
            Collection<Recipe<?>> allRecipes = recipeManager.values();
            for (Recipe<?> recipe : allRecipes) {
                Identifier recipeId = recipe.getId();
                //#if MC>=11500
                if (recipeId.getNamespace().equals(AmsServer.compactName) && !player.getRecipeBook().contains(recipeId)) {
                //#else
                //$$ if (recipeId.getNamespace().equals(AmsServer.compactName) && !player.getRecipeBook().contains(recipe)) {
                //#endif
                    server.getCommandManager().execute(
                        server.getCommandSource().withSilent(),
                        String.format("/recipe give %s %s", player.getName().getString(), recipeId)
                    );
                }
            }
        }
    }

    public static void onValueChange(MinecraftServer server) {
        AmsRecipeManager.clearRecipeListMemory(AmsRecipeBuilder.getInstance());
        AmsServerCustomRecipes.getInstance().buildRecipes();
        if (server != null) {
            server.execute(() -> {
                server.getCommandManager().execute(server.getCommandSource().withSilent(), "/reload");
                RecipeManager recipeManager = server.getRecipeManager();
                Collection<Recipe<?>> allRecipes = recipeManager.values();
                for (Recipe<?> recipe : allRecipes) {
                    Identifier recipeId = recipe.getId();
                    if (recipeId.getNamespace().equals(AmsServer.compactName)) {
                        server.getCommandManager().execute(server.getCommandSource().withSilent(), "/recipe give @a " + recipeId);
                    }
                }
            });
        }
    }

    public static void onServerLoadedWorlds(MinecraftServer server) {
        if (hasActiveRecipeRule()) {
            server.execute(() -> {
                CommandManager commandManager = server.getCommandManager();
                commandManager.execute(server.getCommandSource(), "/reload");
            });
        }
    }

    private static boolean hasActiveRecipeRule() {
        Field[] fields = AmsServerSettings.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(RecipeRule.class)) {
                try {
                    field.setAccessible(true);
                    if (field.getBoolean(null)) {
                        return true;
                    }
                } catch (IllegalAccessException e) {
                    AmsServer.LOGGER.warn("Failed to access RecipeRule field: {}", field.getName(), e);
                }
            }
        }
        return false;
    }
}
