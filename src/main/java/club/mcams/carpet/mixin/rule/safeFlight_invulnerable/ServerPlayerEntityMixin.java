package club.mcams.carpet.mixin.rule.safeFlight_invulnerable;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "isInvulnerableTo",at = @At("TAIL"), cancellable = true)
    private void isInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (AmsServerSettings.safeFlight && damageSource.equals(DamageSource.FLY_INTO_WALL)) {
            cir.setReturnValue(true);
            cir.cancel();
        }

        if (AmsServerSettings.invulnerable && !damageSource.equals(DamageSource.OUT_OF_WORLD)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}