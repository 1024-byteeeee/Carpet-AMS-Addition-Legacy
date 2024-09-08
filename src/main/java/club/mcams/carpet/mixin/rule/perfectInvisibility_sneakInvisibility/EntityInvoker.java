package club.mcams.carpet.mixin.rule.perfectInvisibility_sneakInvisibility;

import net.minecraft.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityInvoker {
    @Invoker("isInvisible")
    boolean invokeIsInvisible();

    //#if MC>=11500
    @Invoker("isSneaky")
    //#else
    //$$ @Invoker("isSneaking")
    //#endif
    boolean invokeIsSneaky();
}
