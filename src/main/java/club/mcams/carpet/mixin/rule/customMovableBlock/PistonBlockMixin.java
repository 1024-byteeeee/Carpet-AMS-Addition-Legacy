package club.mcams.carpet.mixin.rule.customMovableBlock;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.utils.RegexTools;

import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin {
    @Inject(method = "isMovable", at = @At("HEAD"), cancellable = true)
    private static void MovableBlocks(BlockState state, World world, BlockPos blockPos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir) {
        if (!Objects.equals(AmsServerSettings.customMovableBlock, "VANILLA")) {
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            String blockName = RegexTools.getBlockRegisterName(state.getBlock().toString()); //Block{minecraft:bedrock} -> minecraft:bedrock
            Set<String> moreCustomMovableBlock = new HashSet<>(Arrays.asList(AmsServerSettings.customMovableBlock.split(",")));
            if (moreCustomMovableBlock.contains(blockName) && !(blockEntity instanceof LootableContainerBlockEntity)) {
                if (direction == Direction.DOWN && blockPos.getY() == 0) {
                    cir.setReturnValue(false);
                } else if (direction == Direction.UP && blockPos.getY() == world.getHeight() - 1) {
                    cir.setReturnValue(false);
                } else {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
