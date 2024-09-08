package club.mcams.carpet.mixin.rule.fakePlayerNoScoreboardCounter;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.FakePlayerHelper;

import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "incrementStat(Lnet/minecraft/stat/Stat;)V", at = @At("HEAD"), cancellable = true)
    private void increaseStat(CallbackInfo ci) {
        if (AmsServerSettings.fakePlayerNoScoreboardCounter) {
            PlayerEntity player = (PlayerEntity)(Object)this;
            if (FakePlayerHelper.isFakePlayer(player)) {
                ci.cancel();
            }
        }
    }
}
