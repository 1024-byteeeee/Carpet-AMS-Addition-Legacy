package club.mcams.carpet.mixin.rule.fancyFakePlayerName;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.FakePlayerHelper;
import club.mcams.carpet.helpers.rule.fancyFakePlayerName.FancyFakePlayerNameTeamController;
import club.mcams.carpet.helpers.rule.fancyFakePlayerName.FancyNameHelper;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void onPlayerConnects(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        if (!Objects.equals(AmsServerSettings.fancyFakePlayerName, "false") && FakePlayerHelper.isFakePlayer(player)) {
            FancyNameHelper.addBotTeamNamePrefix(player, AmsServerSettings.fancyFakePlayerName);
        }
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void kickFakePlayerFromBotTeam(ServerPlayerEntity player, CallbackInfo info) {
        if (!Objects.equals(AmsServerSettings.fancyFakePlayerName, "false") && FakePlayerHelper.isFakePlayer(player)) {
            FancyFakePlayerNameTeamController.kickFakePlayerFromBotTeam(player, AmsServerSettings.fancyFakePlayerName);
        }
    }
}