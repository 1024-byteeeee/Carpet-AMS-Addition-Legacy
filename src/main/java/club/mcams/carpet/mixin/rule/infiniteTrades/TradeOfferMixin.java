package club.mcams.carpet.mixin.rule.infiniteTrades;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.village.TradeOffer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TradeOffer.class)
public abstract class TradeOfferMixin implements TradeOfferAccessor{
    @Inject(method = "use", at = @At("TAIL"))
    private void resetUses(CallbackInfo ci) {
        if (AmsServerSettings.infiniteTrades) {
            this.setUses(0);
        }
    }
}
