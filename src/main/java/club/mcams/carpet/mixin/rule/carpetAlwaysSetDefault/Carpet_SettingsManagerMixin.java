package club.mcams.carpet.mixin.rule.carpetAlwaysSetDefault;

import carpet.settings.ParsedRule;
import carpet.settings.SettingsManager;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.server.command.ServerCommandSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SettingsManager.class)
public abstract class Carpet_SettingsManagerMixin {

    @Shadow
    protected abstract int setDefault(ServerCommandSource source, ParsedRule<?> rule, String stringValue);

    @Inject(method = "setRule", at = @At("TAIL"))
    private void setRule(ServerCommandSource source, ParsedRule<?> rule, String value, CallbackInfoReturnable<Integer> cir) {
        if (AmsServerSettings.carpetAlwaysSetDefault) {
            setDefault(source, rule, value);
        }
    }
}
