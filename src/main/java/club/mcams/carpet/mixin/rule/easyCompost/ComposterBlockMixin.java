package club.mcams.carpet.mixin.rule.easyCompost;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ComposterBlock.class)
public abstract class ComposterBlockMixin {
    @Inject(method = "addToComposter", at = @At("HEAD"))
    private static void addToComposter(BlockState state, IWorld world, BlockPos pos, ItemStack item, CallbackInfoReturnable<Boolean> cir) {
        if (AmsServerSettings.easyCompost) {
            int level = state.get(ComposterBlock.LEVEL);
            int newLevel = Math.min(7, level + 1);
            BlockState updatedState = state.with(ComposterBlock.LEVEL, newLevel);
            world.setBlockState(pos, updatedState, 3);
        }
    }
}
