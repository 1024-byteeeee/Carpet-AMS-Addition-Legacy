package club.mcams.carpet.mixin.rule.commandCustomCommandPermissionLevel;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.commands.rule.commandCustomCommandPermissionLevel.CustomCommandPermissionLevelRegistry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.server.command.ServerCommandSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(CommandDispatcher.class)
public abstract class CommandDispatcherMixin {
    @Inject(method = "register", at = @At("HEAD"), remap = false)
    private void register(LiteralArgumentBuilder<ServerCommandSource> command, CallbackInfoReturnable<LiteralCommandNode<ServerCommandSource>> cir) {
        if (
            !Objects.equals(AmsServerSettings.commandCustomCommandPermissionLevel, "false") &&
            CustomCommandPermissionLevelRegistry.COMMAND_PERMISSION_MAP.containsKey(command.getLiteral())
        ) {
            int level = CustomCommandPermissionLevelRegistry.COMMAND_PERMISSION_MAP.get(command.getLiteral());
            command.requires(source -> source.hasPermissionLevel(level));
        }
    }
}
