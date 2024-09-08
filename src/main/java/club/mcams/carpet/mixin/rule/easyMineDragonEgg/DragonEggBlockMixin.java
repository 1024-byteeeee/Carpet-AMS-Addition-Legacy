package club.mcams.carpet.mixin.rule.easyMineDragonEgg;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.block.DragonEggBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DragonEggBlock.class)
public abstract class DragonEggBlockMixin {
    @ModifyExpressionValue(
        method = "teleport",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;isAir()Z"
        )
    )
    private boolean teleport(boolean original) {
        return original && !AmsServerSettings.easyMineDragonEgg;
    }
}
