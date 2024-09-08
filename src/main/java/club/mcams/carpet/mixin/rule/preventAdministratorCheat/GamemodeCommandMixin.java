package club.mcams.carpet.mixin.rule.preventAdministratorCheat;

import club.mcams.carpet.helpers.rule.preventAdministratorCheat.PermissionHelper;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.command.GameModeCommand;
import net.minecraft.server.command.ServerCommandSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameModeCommand.class)
public abstract class GamemodeCommandMixin {
    @ModifyExpressionValue(
        method = "method_13389",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"
        )
    )
    private static boolean GameModeCommand(boolean original, ServerCommandSource source) {
        return original && PermissionHelper.canCheat(source);
    }
}
