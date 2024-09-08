package club.mcams.carpet.mixin.rule.antiFireTotem_itemAntiExplosion;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow
    public abstract ItemStack getStack();

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(AmsServerSettings.itemAntiExplosion && source.isExplosive()) {
            cir.setReturnValue(false);
            cir.cancel();
        }

        if (AmsServerSettings.antiFireTotem && this.getStack().getItem().equals(Items.TOTEM_OF_UNDYING) && source.isFire()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}