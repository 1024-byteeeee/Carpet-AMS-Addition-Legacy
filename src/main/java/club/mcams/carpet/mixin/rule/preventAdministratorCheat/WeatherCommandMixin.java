package club.mcams.carpet.mixin.rule.preventAdministratorCheat;

import club.mcams.carpet.helpers.rule.preventAdministratorCheat.PermissionHelper;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.WeatherCommand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WeatherCommand.class)
public abstract class WeatherCommandMixin {
    @ModifyExpressionValue(
        method = "method_13832",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"
        )
    )
    private static boolean WeatherCommand(boolean original, ServerCommandSource source) {
        return original && PermissionHelper.canCheat(source);
    }
}
