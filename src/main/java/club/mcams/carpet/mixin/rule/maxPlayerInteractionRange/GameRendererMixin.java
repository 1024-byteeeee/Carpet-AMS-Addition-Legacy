package club.mcams.carpet.mixin.rule.maxPlayerInteractionRange;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.maxPlayerInteractionDistance_maxClientInteractionReachDistance.MaxInteractionDistanceMathHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyConstant(
        method = "updateTargetedEntity",
        require = 0,
        allow = 1,
        constant = @Constant(doubleValue = 6.0D)
    )
    private double updateTargetedEntity1(final double constant) {
        if (AmsServerSettings.maxPlayerEntityInteractionRange != -1.0D && this.client.player != null) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerEntityInteractionRange);
        }
        return constant;
    }

    @ModifyConstant(method = "updateTargetedEntity", constant = @Constant(doubleValue = 3.0D), require = 0)
    private double updateTargetedEntity2(final double constant) {
        if (AmsServerSettings.maxPlayerEntityInteractionRange != -1.0D && this.client.player != null) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerEntityInteractionRange);
        } else {
            return constant;
        }
    }

    @ModifyConstant(
        method = "updateTargetedEntity",
        require = 0,
        constant = @Constant(doubleValue = 9.0D)
    )
    private double updateTargetedEntity3(final double constant) {
        if (AmsServerSettings.maxPlayerEntityInteractionRange != -1.0D && this.client.player != null) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerEntityInteractionRange);
        } else {
            return constant;
        }
    }
}
