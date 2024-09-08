package club.mcams.carpet.mixin.rule.amsUpdateSuppressionCrashFix;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.amsUpdateSuppressionCrashFix.UpdateSuppressionException;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @WrapOperation(
        method = "tickWorlds",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;tick(Ljava/util/function/BooleanSupplier;)V"
        )
    )
    private void tickWorlds(ServerWorld serverWorld, BooleanSupplier shouldKeepTicking, Operation<Void> original) {
        if (AmsServerSettings.amsUpdateSuppressionCrashFix) {
            try {
                original.call(serverWorld, shouldKeepTicking);
            } catch (Throwable throwable) {
                if (UpdateSuppressionException.isUpdateSuppression(throwable)) {
                    throw throwable;
                }
            }
        } else {
            original.call(serverWorld, shouldKeepTicking);
        }
    }
}
