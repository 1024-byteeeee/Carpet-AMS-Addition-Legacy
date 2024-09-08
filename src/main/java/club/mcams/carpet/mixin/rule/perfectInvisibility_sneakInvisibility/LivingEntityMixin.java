package club.mcams.carpet.mixin.rule.perfectInvisibility_sneakInvisibility;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements EntityInvoker {
    @ModifyReturnValue(method = "getAttackDistanceScalingFactor", at = @At("RETURN"))
    private double modifyAttackDistanceScalingFactor(double original) {
        if (AmsServerSettings.perfectInvisibility && this.invokeIsInvisible()) {
            return 0.0D;
        } else {
            return original;
        }
    }

    @ModifyReturnValue(method = "getAttackDistanceScalingFactor", at = @At("RETURN"))
    private double sneakInvisibility(double original) {
        if (AmsServerSettings.sneakInvisibility && this.invokeIsSneaky()) {
            return 0.0D;
        } else {
            return original;
        }
    }
}
