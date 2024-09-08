package club.mcams.carpet.mixin.rule.kirinArm;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @ModifyReturnValue(method = "getBlockBreakingSpeed", at = @At("RETURN"))
    private float getBlockBreakingSpeed(float original) {
        return AmsServerSettings.kirinArm ? Float.MAX_VALUE : original;
    }
}
