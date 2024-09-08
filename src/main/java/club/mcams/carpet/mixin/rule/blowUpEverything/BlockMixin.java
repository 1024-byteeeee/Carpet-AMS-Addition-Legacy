package club.mcams.carpet.mixin.rule.blowUpEverything;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.Block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public abstract class BlockMixin {
    @ModifyReturnValue(method = "getBlastResistance", at = @At("RETURN"))
    private float getBlastResistance(float original) {
        return AmsServerSettings.blowUpEverything ? 0.114514F : original;
    }
}