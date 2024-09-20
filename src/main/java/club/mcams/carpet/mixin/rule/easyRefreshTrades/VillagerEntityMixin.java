package club.mcams.carpet.mixin.rule.easyRefreshTrades;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TraderOfferList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin implements AbstractTraderEntityInvoker, VillagerEntityInvoker {
    @WrapOperation(
        method = "fillRecipes",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/passive/VillagerEntity;fillRecipesFromPool(Lnet/minecraft/village/TraderOfferList;[Lnet/minecraft/village/TradeOffers$Factory;I)V"
        )
    )
    private void refreshRecipes(VillagerEntity villagerEntity, TraderOfferList tradeOffers, TradeOffers.Factory[] factories, int count, Operation<Void> original) {
        if (AmsServerSettings.easyRefreshTrades && villagerEntity.getExperience() <= 0 && villagerEntity.getVillagerData().getLevel() == 1) {
            TraderOfferList traderOfferList = villagerEntity.getOffers();
            traderOfferList.clear();
            this.invokeFillRecipesFromPool(traderOfferList, factories, count);
        } else {
            original.call(villagerEntity, tradeOffers, factories, count);
        }
    }

    @Inject(
        method = "interactMob",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/passive/VillagerEntity;beginTradeWith(Lnet/minecraft/entity/player/PlayerEntity;)V"
        )
    )
    private void updateRecipes(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> cir) {
        if (AmsServerSettings.easyRefreshTrades) {
            VillagerEntity villagerEntity = (VillagerEntity) (Object) this;
            if (isNewMerchant(villagerEntity)) {
                this.invokeFillRecipes();
            }
        }
    }

    @Unique
    private static boolean isNewMerchant(VillagerEntity villagerEntity) {
        return villagerEntity.getExperience() <= 0 && villagerEntity.getVillagerData().getLevel() <= 1;
    }
}
