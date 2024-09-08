package club.mcams.carpet.mixin.rule.sneakToEditSign;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.entity.SignBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public abstract class SignBlockEntityMixin {
    @Inject(method = "isEditable", at = @At("HEAD"), cancellable = true)
    private void isEditable(CallbackInfoReturnable<Boolean> cir) {
        if (AmsServerSettings.sneakToEditSign) {
            cir.setReturnValue(true);
        }
    }
}
