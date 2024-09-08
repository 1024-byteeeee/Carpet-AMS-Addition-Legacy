package club.mcams.carpet.mixin.rule.preventAdministratorCheat;

import club.mcams.carpet.helpers.rule.preventAdministratorCheat.PermissionHelper;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.command.DifficultyCommand;
import net.minecraft.server.command.ServerCommandSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DifficultyCommand.class)
public abstract class DifficultyCommandMixin {
	@ModifyExpressionValue(
		method = "method_13172",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"
		)
	)
	private static boolean DifficultyCommand(boolean original, ServerCommandSource source) {
		return original && PermissionHelper.canCheat(source);
	}
}
