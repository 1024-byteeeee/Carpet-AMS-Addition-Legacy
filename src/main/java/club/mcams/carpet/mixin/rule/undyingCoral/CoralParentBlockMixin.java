package club.mcams.carpet.mixin.rule.undyingCoral;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.CoralParentBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CoralParentBlock.class)
public abstract class CoralParentBlockMixin {
    @Inject(method = "checkLivingConditions", at = @At("HEAD"), cancellable = true)
    private void checkLivingConditionsMixin(CallbackInfo ci) {
        if (AmsServerSettings.undyingCoral) {
            ci.cancel();
        }
    }
}
