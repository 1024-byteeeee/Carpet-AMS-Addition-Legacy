package club.mcams.carpet.mixin.rule.maxClientInteractionReachDistance;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerInteractionManager.class)

public abstract class ClientPlayerInteractionManagerMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyReturnValue(method = "getReachDistance()F", at = @At("RETURN"))
    private float getReachDistance(final float original) {
        if (AmsServerSettings.maxClientInteractionReachDistance != -1.0D && this.client.player != null) {
            return (float) AmsServerSettings.maxClientInteractionReachDistance;
        } else {
            return original;
        }
    }
}
