package club.mcams.carpet.mixin.rule.keepWorldTickUpdate;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.server.world.ServerWorld;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Shadow
    public abstract void resetIdleTimeout();

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (AmsServerSettings.keepWorldTickUpdate) {
            this.resetIdleTimeout();
        }
    }
}
