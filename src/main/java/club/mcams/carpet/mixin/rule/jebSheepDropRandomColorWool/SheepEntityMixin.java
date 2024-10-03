package club.mcams.carpet.mixin.rule.jebSheepDropRandomColorWool;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin {
    @WrapOperation(
        method = "dropItems",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/passive/SheepEntity;getColor()Lnet/minecraft/util/DyeColor;"
        )
    )
    private DyeColor randomColor(SheepEntity sheepEntity, Operation<DyeColor> original) {
        if (AmsServerSettings.jebSheepDropRandomColorWool && isJebSheep(sheepEntity)) {
            Random random = new Random();
            return DyeColor.values()[random.nextInt(DyeColor.values().length)];
        } else {
            return original.call(sheepEntity);
        }
    }

    @Unique
    private static boolean isJebSheep(SheepEntity sheepEntity) {
        return sheepEntity.hasCustomName() && sheepEntity.getCustomName() != null && sheepEntity.getCustomName().getString().equals("jeb_");
    }
}
