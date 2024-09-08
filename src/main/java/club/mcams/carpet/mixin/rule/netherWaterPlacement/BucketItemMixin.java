package club.mcams.carpet.mixin.rule.netherWaterPlacement;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.item.BucketItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("SimplifiableConditionalExpression")
@Mixin(BucketItem.class)
public abstract class BucketItemMixin {
    @ModifyExpressionValue(
        method = "placeFluid",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/dimension/Dimension;doesWaterVaporize()Z"
        )
    )
    private boolean canPlaceFluid(boolean original) {
        return AmsServerSettings.netherWaterPlacement ? false : original;
    }
}
