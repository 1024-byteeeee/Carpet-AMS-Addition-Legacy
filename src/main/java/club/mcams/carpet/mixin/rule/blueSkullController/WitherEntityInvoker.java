package club.mcams.carpet.mixin.rule.blueSkullController;

import net.minecraft.entity.boss.WitherEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WitherEntity.class)
public interface WitherEntityInvoker {
    @Invoker("method_6877")
    void invokeShootSkullAt(int headIndex, double d, double e, double f, boolean bl);
}
