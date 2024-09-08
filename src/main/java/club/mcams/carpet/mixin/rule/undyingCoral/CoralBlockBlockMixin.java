package club.mcams.carpet.mixin.rule.undyingCoral;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.CoralBlockBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CoralBlockBlock.class)
public abstract class CoralBlockBlockMixin {
    @ModifyReturnValue(method = "isInWater", at = @At("RETURN"))
    private boolean isInWater(boolean original) {
        return AmsServerSettings.undyingCoral || original;
    }
}
