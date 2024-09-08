package club.mcams.carpet.mixin.rule.strongLeash;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.entity.mob.MobEntityWithAi;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntityWithAi.class)
public abstract class PathAwareEntityMixin {
    @ModifyExpressionValue(
        method = "updateLeash",
        at = @At(
            value = "CONSTANT",
            args = "floatValue=10.0F"
        ),
        require = 1
    )
    private float modifyMaxLeashDetachDistance(float original) {
        return AmsServerSettings.strongLeash ? Math.max(original, Float.MAX_VALUE) : original;
    }
}
