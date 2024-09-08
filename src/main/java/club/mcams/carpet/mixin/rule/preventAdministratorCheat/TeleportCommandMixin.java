package club.mcams.carpet.mixin.rule.preventAdministratorCheat;

import club.mcams.carpet.helpers.rule.preventAdministratorCheat.PermissionHelper;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TeleportCommand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TeleportCommand.class)
public abstract class TeleportCommandMixin {
    @ModifyExpressionValue(
        method = {"method_13763", "method_13764"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"
        )
    )
    private static boolean TeleportCommand(boolean original, ServerCommandSource source) {
        return original && PermissionHelper.canCheat(source);
    }
}
