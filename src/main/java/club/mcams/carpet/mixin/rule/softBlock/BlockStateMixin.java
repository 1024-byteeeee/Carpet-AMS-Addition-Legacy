package club.mcams.carpet.mixin.rule.softBlock;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockState.class)
public abstract class BlockStateMixin implements AbstractBlockStateInvoker{
    @ModifyReturnValue(method = "getHardness", at = @At("RETURN"))
    private float modifyDeepslateHardness(float original, BlockView world, BlockPos pos) {
        if (AmsServerSettings.softObsidian && this.invokeGetBlock().equals(Blocks.OBSIDIAN)) {
            return Blocks.STONE.getDefaultState().getHardness(world, pos);
        } else {
            return original;
        }
    }
}
