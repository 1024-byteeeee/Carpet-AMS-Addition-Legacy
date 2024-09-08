package club.mcams.carpet.mixin.translations;

import carpet.logging.HUDController;

import club.mcams.carpet.translations.AMSTranslations;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft >= 1.15.2")
@Mixin(HUDController.class)
public abstract class HUDControllerMixin {
    @ModifyVariable(method = "addMessage", at = @At("HEAD"), argsOnly = true, remap = false)
    private static BaseText addMessage(BaseText hudMessage, PlayerEntity player, BaseText hudMessage_) {
        return AMSTranslations.translate(hudMessage, (ServerPlayerEntity) player);
    }
}
