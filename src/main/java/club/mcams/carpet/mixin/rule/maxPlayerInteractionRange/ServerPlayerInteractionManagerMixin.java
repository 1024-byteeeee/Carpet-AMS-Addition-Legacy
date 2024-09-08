package club.mcams.carpet.mixin.rule.maxPlayerInteractionRange;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.maxPlayerInteractionDistance_maxClientInteractionReachDistance.MaxInteractionDistanceMathHelper;

import net.minecraft.server.network.ServerPlayerInteractionManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    @ModifyConstant(
        //#if MC>=11500
        method = "processBlockBreakingAction",
        //#else
        //$$ method = "method_14263",
        //#endif
        require = 0,
        allow = 1,
        constant = @Constant(doubleValue = 36.0)
    )
    private double processBlockBreakingAction(final double constant) {
        if (AmsServerSettings.maxPlayerBlockInteractionRange != -1.0D) {
            return MaxInteractionDistanceMathHelper.getMaxSquaredReachDistance(AmsServerSettings.maxPlayerBlockInteractionRange);
        } else {
            return constant;
        }
    }
}