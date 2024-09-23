package club.mcams.carpet.mixin.hooks.recipeRule;

import club.mcams.carpet.api.recipe.AmsRecipeManager;
import club.mcams.carpet.api.recipe.AmsRecipeRegistry;

import com.google.gson.JsonElement;

import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
    @Inject(
        method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;entrySet()Ljava/util/Set;"
        )
    )
    private void addCustomRecipe(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        AmsRecipeRegistry amsRecipeRegistry = AmsRecipeRegistry.getInstance();
        AmsRecipeManager amsRecipeManager = new AmsRecipeManager(
            amsRecipeRegistry.shapelessRecipeList,
            amsRecipeRegistry.shapedRecipeList
        );
        AmsRecipeManager.clearRecipeListMemory(amsRecipeRegistry);
        amsRecipeRegistry.register();
        amsRecipeManager.registerRecipes(map);
    }
}
