package club.mcams.carpet.mixin.rule.maxPlayerInteractionRange;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.maxPlayerInteractionDistance_maxClientInteractionReachDistance.MaxInteractionDistanceMathHelper;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @ModifyConstant(
        method = "onPlayerInteractBlock",
        require = 0,
        allow = 1,
        constant = @Constant(doubleValue = 64.0)
    )
    private double onPlayerInteractBlock1(double constant) {
        if (AmsServerSettings.maxPlayerBlockInteractionRange != -1.0D) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerBlockInteractionRange);
        } else {
            return constant;
        }
    }

    @ModifyConstant(
        method = "onPlayerInteractEntity",
        require = 0,
        allow = 2,
        constant = @Constant(doubleValue = 36.0)
    )
    private double onPlayerInteractEntity(double constant) {
        if (AmsServerSettings.maxPlayerEntityInteractionRange != -1.0D) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerEntityInteractionRange);
        } else {
            return constant;
        }
    }
}