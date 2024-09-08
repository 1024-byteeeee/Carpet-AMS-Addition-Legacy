package club.mcams.carpet.mixin.rule.fakePlayerPickUpController;

import club.mcams.carpet.AmsServerSettings;
import club.mcams.carpet.helpers.FakePlayerHelper;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void onCanReplaceCurrentItem(PlayerEntity player, CallbackInfo ci) {
        if (!Objects.equals(AmsServerSettings.fakePlayerPickUpController, "false") && FakePlayerHelper.isFakePlayer(player)) {
            ItemStack mainHandStack = player.getMainHandStack();
            if (Objects.equals(AmsServerSettings.fakePlayerPickUpController, "MainHandOnly") && !mainHandStack.isEmpty()) {
                ci.cancel();
            } else if (Objects.equals(AmsServerSettings.fakePlayerPickUpController, "NoPickUp")) {
                ci.cancel();
            }
        }
    }
}
