package club.mcams.carpet.mixin.rule.superBow;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.enchantment.InfinityEnchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InfinityEnchantment.class)
public abstract class InfinityEnchantmentMixin {
    @ModifyReturnValue(method = "differs", at = @At("RETURN"))
    private boolean canAccept(boolean original) {
        return AmsServerSettings.superBow || original;
    }
}
