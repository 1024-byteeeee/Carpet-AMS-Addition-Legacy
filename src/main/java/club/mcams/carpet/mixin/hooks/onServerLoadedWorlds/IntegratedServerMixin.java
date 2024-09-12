package club.mcams.carpet.mixin.hooks.onServerLoadedWorlds;

import club.mcams.carpet.AmsServer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin {
    @Inject(method = "loadWorld", at = @At("RETURN"))
    private void onLoadWorld(CallbackInfo ci) {
        AmsServer.getInstance().onServerLoadedWorlds_AMS((MinecraftServer) (Object) this);
    }
}
