package club.mcams.carpet.mixin.rule.superZombieDoctor;

import net.minecraft.entity.mob.ZombieVillagerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ZombieVillagerEntity.class)
public interface ZombieVillagerEntityAccessor {
    @Accessor("conversionTimer")
    void setConversionTimer(int conversionTimer);
}
