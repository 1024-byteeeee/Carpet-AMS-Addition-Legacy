package club.mcams.carpet.mixin.rule.shulkerGolem;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin {
    @Inject(method = "trySpawnEntity", at = @At("TAIL"))
    private void trySpawnShulker(World world, BlockPos headPos, CallbackInfo ci) {
        if (AmsServerSettings.shulkerGolem) {
            BlockPos bodyPos = headPos.down(1);
            boolean headIsCarvedPumpkin = world.getBlockState(headPos).getBlock() instanceof CarvedPumpkinBlock;
            boolean bodyIsShulkerBox = world.getBlockState(bodyPos).getBlock() instanceof ShulkerBoxBlock;
            if (headIsCarvedPumpkin && bodyIsShulkerBox) {
                ShulkerEntity shulkerGolem = EntityType.SHULKER.create(world);
                Objects.requireNonNull(shulkerGolem).refreshPositionAndAngles(
                    bodyPos.getX() + 0.5, bodyPos.getY(), bodyPos.getZ() + 0.5, 0.0F, 0.0F
                );
                world.breakBlock(bodyPos, false);
                world.breakBlock(headPos, false);
                world.spawnEntity(shulkerGolem);
            }
        }
    }
}
