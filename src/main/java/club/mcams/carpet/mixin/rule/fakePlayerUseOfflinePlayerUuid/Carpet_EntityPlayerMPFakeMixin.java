package club.mcams.carpet.mixin.rule.fakePlayerUseOfflinePlayerUuid;

import carpet.patches.EntityPlayerMPFake;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.UserCache;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityPlayerMPFake.class)
public abstract class Carpet_EntityPlayerMPFakeMixin {
    @WrapOperation(
        method = "createFake",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/UserCache;findByName(Ljava/lang/String;)Lcom/mojang/authlib/GameProfile;"
        )
    )
    private static GameProfile useOfflinePlayerUuid(UserCache userCache, String playerName, Operation<GameProfile> original) {
        return
            AmsServerSettings.fakePlayerUseOfflinePlayerUUID ?
            new GameProfile(PlayerEntity.getOfflinePlayerUuid(playerName), playerName) :
            original.call(userCache, playerName);
    }
}