package club.mcams.carpet.mixin.rule.cakeBlockDropOnBreak;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.Block.dropStack;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "onBreak", at = @At("HEAD"))
    private void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        if (AmsServerSettings.cakeBlockDropOnBreak && state.getBlock() == Blocks.CAKE && state.get(CakeBlock.BITES) == 0) {
            if (!player.isCreative()) {
                ItemStack cakeStack = new ItemStack(Items.CAKE);
                dropStack(world, pos, cakeStack);
            }
        }
    }
}
