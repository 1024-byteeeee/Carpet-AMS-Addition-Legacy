package club.mcams.carpet.mixin.rule.hopperSuctionDisabled;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.entity.HopperBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {
    @Inject(method = "insertAndExtract", at = @At("HEAD"), cancellable = true)
    private void insertAndExtract(CallbackInfoReturnable<Boolean> cir) {
        if (AmsServerSettings.hopperSuctionDisabled) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
