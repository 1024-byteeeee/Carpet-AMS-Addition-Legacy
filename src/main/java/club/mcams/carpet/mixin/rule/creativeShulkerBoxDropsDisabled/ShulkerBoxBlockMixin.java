package club.mcams.carpet.mixin.rule.creativeShulkerBoxDropsDisabled;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlock.class)
public abstract class ShulkerBoxBlockMixin {
    @Inject(method = "onBreak", at = @At("HEAD"), cancellable = true)
    private void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        if (AmsServerSettings.creativeShulkerBoxDropsDisabled && player.isCreative()) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.playSound(player, pos, SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
            ci.cancel();
        }
    }
}
