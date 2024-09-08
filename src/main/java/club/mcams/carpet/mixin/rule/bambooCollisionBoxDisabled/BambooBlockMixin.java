package club.mcams.carpet.mixin.rule.bambooCollisionBoxDisabled;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.BambooBlock;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BambooBlock.class)
public abstract class BambooBlockMixin {
    @ModifyReturnValue(method = "getCollisionShape", at = @At("RETURN"))
    private VoxelShape getCollisionShape(VoxelShape original) {
        return AmsServerSettings.bambooCollisionBoxDisabled ? VoxelShapes.empty() : original;
    }
}
