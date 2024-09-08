package club.mcams.carpet.mixin.translations;

import carpet.logging.Logger;

import club.mcams.carpet.translations.AMSTranslations;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Logger.class)
public abstract class LoggerMixin {
    @ModifyVariable(method = "sendPlayerMessage", at = @At("HEAD"), argsOnly = true, remap = false)
    private BaseText[] applyAMSTranslationToLoggerMessage(BaseText[] messages, ServerPlayerEntity player, BaseText... messages_) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = AMSTranslations.translate(messages[i], player);
        }
        return messages;
    }
}
