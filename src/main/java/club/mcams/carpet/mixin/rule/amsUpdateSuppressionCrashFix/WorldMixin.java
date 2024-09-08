package club.mcams.carpet.mixin.rule.amsUpdateSuppressionCrashFix;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.amsUpdateSuppressionCrashFix.ThrowableSuppression;
import club.mcams.carpet.helpers.rule.amsUpdateSuppressionCrashFix.UpdateSuppressionContext;
import club.mcams.carpet.helpers.rule.amsUpdateSuppressionCrashFix.UpdateSuppressionException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft <= 1.18")
@SuppressWarnings("InjectLocalCaptureCanBeReplacedWithLocal")
@Mixin(World.class)
public abstract class WorldMixin {
    @Inject(
        method = "updateNeighbor",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/crash/CrashReport;create(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/util/crash/CrashReport;"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void updateNeighbor(BlockPos sourcePos, Block sourceBlock, BlockPos neighborPos, CallbackInfo ci, BlockState state, Throwable throwable) {
        if (AmsServerSettings.amsUpdateSuppressionCrashFix && UpdateSuppressionException.isUpdateSuppression(throwable)) {
            World world = (World) (Object) this;
            UpdateSuppressionContext.sendMessageToServer(sourcePos, world, throwable);
            throw new ThrowableSuppression(UpdateSuppressionContext.suppressionMessageText(sourcePos, world, throwable));
        }
    }
}
