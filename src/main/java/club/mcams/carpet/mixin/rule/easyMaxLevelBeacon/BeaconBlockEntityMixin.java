package club.mcams.carpet.mixin.rule.easyMaxLevelBeacon;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mixin(BeaconBlockEntity.class)
public abstract class BeaconBlockEntityMixin extends BlockEntity{
    private BeaconBlockEntityMixin(BlockEntityType<?> type) {
        super(type);
    }

    @Shadow
    private int level;

    @Unique
    private static final List<Predicate<Block>> BEACON_BASE_BLOCKS = new ArrayList<>();

    @Inject(method = "updateLevel", at = @At("HEAD"), cancellable = true)
    private void updateLevel(int x, int y, int z, CallbackInfo ci) {
        if (AmsServerSettings.easyMaxLevelBeacon) {
            BlockPos pos = new BlockPos(x, y - 1, z);
            if (this.world != null && isBaseBlocks(this.world.getBlockState(pos).getBlock())) {
                this.level = 4;
                ci.cancel();
            }
        }
    }

    @Unique
    private static boolean isBaseBlocks(Block block) {
        return BEACON_BASE_BLOCKS.stream().anyMatch(predicate -> predicate.test(block));
    }

    static {
        BEACON_BASE_BLOCKS.add(block -> block.equals(Blocks.EMERALD_BLOCK));
        BEACON_BASE_BLOCKS.add(block -> block.equals(Blocks.GOLD_BLOCK));
        BEACON_BASE_BLOCKS.add(block -> block.equals(Blocks.DIAMOND_BLOCK));
        BEACON_BASE_BLOCKS.add(block -> block.equals(Blocks.IRON_BLOCK));
    }
}
