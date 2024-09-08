package club.mcams.carpet.mixin.rule.noCakeEating;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.block.CakeBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft < 1.15.2")
@Mixin(CakeBlock.class)
public abstract class CakeBlockMixin {
    @ModifyExpressionValue(
        method = "tryEat",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;canConsume(Z)Z"
        )
    )
    private boolean noEat(boolean original) {
        return original && !AmsServerSettings.noCakeEating;
    }
}
