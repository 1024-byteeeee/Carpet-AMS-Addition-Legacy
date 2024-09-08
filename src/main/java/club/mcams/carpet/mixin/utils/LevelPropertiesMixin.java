package club.mcams.carpet.mixin.utils;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.config.LoadConfigFromJson;
import club.mcams.carpet.helpers.rule.fancyFakePlayerName.FancyFakePlayerNameTeamController;
import club.mcams.carpet.utils.CraftingRuleUtil;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelProperties;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelProperties.class)
public abstract class LevelPropertiesMixin {
    @Inject(method = "loadLevelInfo", at = @At("TAIL"))
    private void afterLoadLevelInfo(CallbackInfo ci) {
        MinecraftServer server = AmsServer.minecraftServer;
        CraftingRuleUtil.loadAmsDatapacks(server);
        FancyFakePlayerNameTeamController.removeBotTeam(server, AmsServerSettings.fancyFakePlayerName);
        LoadConfigFromJson.load(server);
    }
}
