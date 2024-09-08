package club.mcams.carpet.mixin.rule.quickVillagerLevelUp;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerData;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(VillagerEntity.class)
public interface VillagerEntityInvoker {
    @Invoker("levelUp")
    void invokerLevelUp();

    @Invoker("getVillagerData")
    VillagerData invokerGetVillagerData();
}
