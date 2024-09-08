package club.mcams.carpet.mixin.rule.ironGolemNoDropFlower;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    @Nullable
    public abstract ItemEntity dropStack(ItemStack stack);

    @Inject(method = "dropStack(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"), cancellable = true)
    private void noDropPoppy(ItemStack stack, CallbackInfoReturnable<ItemEntity> cir) {
        if (AmsServerSettings.ironGolemNoDropFlower) {
            Entity entity = (Entity)(Object)this;
            if (entity instanceof IronGolemEntity && stack.getItem().equals(Items.POPPY)) {
                cir.setReturnValue(dropStack(ItemStack.EMPTY));
            }
        }
    }
}
