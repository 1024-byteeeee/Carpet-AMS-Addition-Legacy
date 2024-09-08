package club.mcams.carpet.mixin.rule.noEnchantedGoldenAppleEating;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void use(CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (AmsServerSettings.noEnchantedGoldenAppleEating) {
            ItemStack stack = (ItemStack) (Object) this;
            if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
                cir.setReturnValue(new TypedActionResult<>(ActionResult.FAIL, stack));
            }
        }
    }
}
