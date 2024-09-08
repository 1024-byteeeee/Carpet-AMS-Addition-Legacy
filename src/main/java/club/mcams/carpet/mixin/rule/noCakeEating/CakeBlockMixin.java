package club.mcams.carpet.mixin.rule.noCakeEating;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.CakeBlock;
import net.minecraft.util.ActionResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft > 1.14.4")
@Mixin(CakeBlock.class)
public abstract class CakeBlockMixin {
    @ModifyReturnValue(method = "onUse", at = @At("RETURN"))
    private ActionResult noEat(ActionResult original) {
        return AmsServerSettings.noCakeEating ? ActionResult.FAIL : original;
    }
}
