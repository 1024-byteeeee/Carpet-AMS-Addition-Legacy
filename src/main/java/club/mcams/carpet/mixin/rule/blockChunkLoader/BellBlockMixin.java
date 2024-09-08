package club.mcams.carpet.mixin.rule.blockChunkLoader;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.blockChunkLoader.BlockChunkLoaderHelper;

import net.minecraft.block.BellBlock;
//#if MC>=11500
import net.minecraft.util.math.Direction;
//#endif
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

//#if MC>=11500
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//#else
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#endif

@Mixin(BellBlock.class)
public abstract class BellBlockMixin {
    @Inject(
        //#if MC>=11500
        method = "ring(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
        //#else
        //$$ method = "ring(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
        //#endif
        at = @At("HEAD")
    )
    private void ringByTriggeredMixin(
        World world,
        BlockPos pos,
        //#if MC>=11500
        Direction direction,
        CallbackInfoReturnable<Boolean> cir
        //#else
        //$$ CallbackInfo ci
        //#endif
    ) {
        if (AmsServerSettings.bellBlockChunkLoader && !world.isClient) {
            ChunkPos chunkPos = new ChunkPos(pos);
            BlockChunkLoaderHelper.addBellBlockTicket((ServerWorld) world, chunkPos);
        }
    }
}
