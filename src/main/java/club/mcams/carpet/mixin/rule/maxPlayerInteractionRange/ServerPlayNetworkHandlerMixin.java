package club.mcams.carpet.mixin.rule.maxPlayerInteractionRange;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.maxPlayerInteractionDistance_maxClientInteractionReachDistance.MaxInteractionDistanceMathHelper;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.network.ServerPlayNetworkHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @ModifyExpressionValue(method = "onPlayerInteractBlock", at = @At(value = "CONSTANT", args = "doubleValue=64.0"))
    private double onPlayerInteractBlock1(double constant) {
        if (AmsServerSettings.maxPlayerBlockInteractionRange != -1.0D) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerBlockInteractionRange);
        } else {
            return constant;
        }
    }

    @ModifyExpressionValue(method = "onPlayerInteractEntity", at = @At(value = "CONSTANT", args = "doubleValue=36.0"))
    private double onPlayerInteractEntity(double constant) {
        if (AmsServerSettings.maxPlayerEntityInteractionRange != -1.0D) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerEntityInteractionRange);
        } else {
            return constant;
        }
    }
}