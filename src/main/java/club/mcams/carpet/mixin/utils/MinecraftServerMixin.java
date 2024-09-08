package club.mcams.carpet.mixin.utils;

import club.mcams.carpet.AmsServer;

import net.minecraft.server.MinecraftServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "loadWorld", at = @At("TAIL"))
    private void onLoadWorld(CallbackInfo ci) {
        AmsServer.onServerLoadedWorlds_AMS((MinecraftServer) (Object) this);
    }
}
