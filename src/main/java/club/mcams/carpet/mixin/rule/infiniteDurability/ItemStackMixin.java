package club.mcams.carpet.mixin.rule.infiniteDurability;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "damage(ILjava/util/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void damage(CallbackInfoReturnable<Boolean> cir) {
        if (AmsServerSettings.infiniteDurability) {
            cir.setReturnValue(false);
        }
    }
}
