package club.mcams.carpet.mixin.hooks.onServerLoadedWorlds;

import club.mcams.carpet.AmsServer;

import net.minecraft.server.MinecraftServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "loadWorld", at = @At("RETURN"))
    private void onLoadWorld(CallbackInfo ci) {
        AmsServer.getInstance().onServerLoadedWorlds_AMS((MinecraftServer) (Object) this);
    }
}
