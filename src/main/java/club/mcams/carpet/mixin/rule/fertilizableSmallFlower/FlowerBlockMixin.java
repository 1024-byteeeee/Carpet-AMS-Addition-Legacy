package club.mcams.carpet.mixin.rule.fertilizableSmallFlower;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.FlowerBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

import static net.minecraft.block.Block.dropStack;

@Mixin(FlowerBlock.class)
public abstract class FlowerBlockMixin implements Fertilizable {
    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return AmsServerSettings.fertilizableSmallFlower;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return AmsServerSettings.fertilizableSmallFlower;
    }

    @Override
    public void grow(
        //#if MC>=11500
        ServerWorld world,
        //#else
        //$$ World world,
        //#endif
        Random random,
        BlockPos pos,
        BlockState state
    ) {
        if (AmsServerSettings.fertilizableSmallFlower) {
            dropStack(world, pos, new ItemStack((ItemConvertible) this));
        }
    }
}
