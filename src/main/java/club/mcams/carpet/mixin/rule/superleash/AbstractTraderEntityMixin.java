package club.mcams.carpet.mixin.rule.superleash;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.entity.passive.AbstractTraderEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("SimplifiableConditionalExpression")
@Mixin(AbstractTraderEntity.class)
public abstract class AbstractTraderEntityMixin {
    @ModifyReturnValue(method = "canBeLeashedBy", at = @At("RETURN"))
    private boolean allowLeashed(boolean original) {
        return AmsServerSettings.superLeash ? true : original;
    }
}
