package club.mcams.carpet.mixin.rule.preventAdministratorCheat;

import club.mcams.carpet.helpers.rule.preventAdministratorCheat.PermissionHelper;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.SetBlockCommand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SetBlockCommand.class)
public abstract class SetBlockCommandMixin {
    @ModifyExpressionValue(
        method = "method_13627",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"
        )
    )
    private static boolean SetBlockCommand(boolean original, ServerCommandSource source) {
        return original && PermissionHelper.canCheat(source);
    }
}
