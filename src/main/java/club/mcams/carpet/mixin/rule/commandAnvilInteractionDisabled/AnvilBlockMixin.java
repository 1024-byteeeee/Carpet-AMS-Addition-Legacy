package club.mcams.carpet.mixin.rule.commandAnvilInteractionDisabled;

import club.mcams.carpet.commands.rule.commandAnvilInteractionDisabled.AnvilInteractionDisabledCommandRegistry;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.block.AnvilBlock;
import net.minecraft.util.ActionResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft > 1.14.4")
@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin {
    @ModifyReturnValue(method = "onUse", at = @At("RETURN"))
    private ActionResult onUse(ActionResult original) {
        return AnvilInteractionDisabledCommandRegistry.anvilInteractionDisabled ? ActionResult.PASS : original;
    }
}
