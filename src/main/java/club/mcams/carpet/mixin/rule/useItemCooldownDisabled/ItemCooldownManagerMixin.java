package club.mcams.carpet.mixin.rule.useItemCooldownDisabled;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.entity.player.ItemCooldownManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("SimplifiableConditionalExpression")
@Mixin(ItemCooldownManager.class)
public abstract class ItemCooldownManagerMixin {
    @ModifyReturnValue(method = "isCoolingDown", at = @At("RETURN"))
    private boolean isCoolingDown(boolean original) {
       return AmsServerSettings.useItemCooldownDisabled ? false : original;
    }
}
