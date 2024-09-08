package club.mcams.carpet.mixin.rule.fancyFakePlayerName;

import carpet.commands.PlayerCommand;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.fancyFakePlayerName.FancyNameHelper;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.brigadier.context.CommandContext;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(PlayerCommand.class)
public abstract class CarpetMod_PlayerCommandMixin {
    @WrapOperation(method = "spawn",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/brigadier/arguments/StringArgumentType;getString(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)Ljava/lang/String;"
        ),
        require = 2,
        remap = false
    )
    private static String spawn(CommandContext<?> context, String name, Operation<String> original) {
       return
           !Objects.equals(AmsServerSettings.fancyFakePlayerName, "false") ?
           FancyNameHelper.addBotNameSuffix(context, name, AmsServerSettings.fancyFakePlayerName) :
           original.call(context, name);
    }

    @WrapOperation(
        method = "cantSpawn",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/brigadier/arguments/StringArgumentType;getString(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)Ljava/lang/String;"
        ),
        require = 1,
        remap = false
    )
    private static String cantSpawn(CommandContext<?> context, String name, Operation<String> original) {
        return
            !Objects.equals(AmsServerSettings.fancyFakePlayerName, "false") ?
            FancyNameHelper.addBotNameSuffix(context, name, AmsServerSettings.fancyFakePlayerName) :
            original.call(context, name);
    }
}
