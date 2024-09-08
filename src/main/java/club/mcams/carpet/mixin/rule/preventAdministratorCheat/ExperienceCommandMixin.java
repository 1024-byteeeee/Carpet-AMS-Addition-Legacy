package club.mcams.carpet.mixin.rule.preventAdministratorCheat;

import club.mcams.carpet.helpers.rule.preventAdministratorCheat.PermissionHelper;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.command.ExperienceCommand;
import net.minecraft.server.command.ServerCommandSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperienceCommand.class)
public abstract class ExperienceCommandMixin {
    @ModifyExpressionValue(
        method = "method_13334",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"
        )
    )
    private static boolean ExperienceCommand1(boolean original, ServerCommandSource source) {
        return original && PermissionHelper.canCheat(source);
    }

    @ModifyExpressionValue(
        method = "method_13335",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"
        )
    )
    private static boolean ExperienceCommand2(boolean original, ServerCommandSource source) {
        return original && PermissionHelper.canCheat(source);
    }
}
