package club.mcams.carpet.mixin.rule.easyRefreshTrades;

import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TraderOfferList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractTraderEntity.class)
public interface AbstractTraderEntityInvoker {
    @Invoker("fillRecipesFromPool")
    void invokeFillRecipesFromPool(TraderOfferList recipeList, TradeOffers.Factory[] pool, int count);
}
