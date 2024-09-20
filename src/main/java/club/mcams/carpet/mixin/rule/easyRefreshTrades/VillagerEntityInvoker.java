package club.mcams.carpet.mixin.rule.easyRefreshTrades;

import net.minecraft.entity.passive.VillagerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(VillagerEntity.class)
public interface VillagerEntityInvoker {
    @Invoker("fillRecipes")
    void invokeFillRecipes();
}
