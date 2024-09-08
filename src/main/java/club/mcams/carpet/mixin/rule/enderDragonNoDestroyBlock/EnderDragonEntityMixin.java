package club.mcams.carpet.mixin.rule.enderDragonNoDestroyBlock;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin {
    @ModifyExpressionValue(
        method = "method_6821",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$RuleKey;)Z"
        )
    )
    private boolean destroyBlocks(boolean original) {
        return original && !AmsServerSettings.enderDragonNoDestroyBlock;
    }
}
