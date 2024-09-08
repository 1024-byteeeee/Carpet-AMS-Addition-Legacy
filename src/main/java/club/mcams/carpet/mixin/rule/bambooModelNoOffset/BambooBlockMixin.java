package club.mcams.carpet.mixin.rule.bambooModelNoOffset;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Vec3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockState.class)
public abstract class BambooBlockMixin {

    @Shadow
    public abstract Block getBlock();

    @ModifyReturnValue(method = "getOffsetPos", at = @At("RETURN"))
    public Vec3d getModelOffset(Vec3d original) {
        if (AmsServerSettings.bambooModelNoOffset && (this.getBlock() == Blocks.BAMBOO || this.getBlock() == Blocks.BAMBOO_SAPLING)) {
            return Vec3d.ZERO;
        } else {
            return original;
        }
    }
}