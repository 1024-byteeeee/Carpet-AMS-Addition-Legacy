package club.mcams.carpet.mixin.rule.maxPlayerInteractionRange;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.maxPlayerInteractionDistance_maxClientInteractionReachDistance.MaxInteractionDistanceMathHelper;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyExpressionValue(method = "updateTargetedEntity", at = @At(value = "CONSTANT", args = "doubleValue=6.0"))
    private double updateTargetedEntity1(final double constant) {
        if (AmsServerSettings.maxPlayerEntityInteractionRange != -1.0D && this.client.player != null) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerEntityInteractionRange);
        }
        return constant;
    }

    @ModifyExpressionValue(method = "updateTargetedEntity", at = @At(value = "CONSTANT", args = "doubleValue=3.0"))
    private double updateTargetedEntity2(final double constant) {
        if (AmsServerSettings.maxPlayerEntityInteractionRange != -1.0D && this.client.player != null) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerEntityInteractionRange);
        } else {
            return constant;
        }
    }

    @ModifyExpressionValue(method = "updateTargetedEntity", at = @At(value = "CONSTANT", args = "doubleValue=9.0"))
    private double updateTargetedEntity3(final double constant) {
        if (AmsServerSettings.maxPlayerEntityInteractionRange != -1.0D && this.client.player != null) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerEntityInteractionRange);
        } else {
            return constant;
        }
    }
}
