package club.mcams.carpet.mixin.rule.sendPlayerDeathLocation;

import club.mcams.carpet.AmsServer;
import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.rule.sendPlayerDeathLocation.PlayerDeathLocationContext;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ServerPlayerEntityInvoker {
    @Inject(method = "onDeath", at = @At("TAIL"))
    private void sendDeathLocation(CallbackInfo ci) {
        if (!Objects.equals(AmsServerSettings.sendPlayerDeathLocation, "false")) {
            MinecraftServer server = AmsServer.minecraftServer;
            ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
            World world = this.invokeGetWorld();
            switch (AmsServerSettings.sendPlayerDeathLocation) {
                case "all":
                    PlayerDeathLocationContext.sendMessage(server, player, world);
                    break;
                case "realPlayerOnly":
                    PlayerDeathLocationContext.realPlayerSendMessage(server, player, world);
                    break;
                case "fakePlayerOnly":
                    PlayerDeathLocationContext.fakePlayerSendMessage(server, player, world);
                    break;
            }
        }
    }
}
