package club.mcams.carpet.mixin.rule.sensibleEnderman_endermanPickUpDisabled;

import club.mcams.carpet.AmsServerSettings;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.Tag;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/entity/mob/EndermanEntity$PickUpBlockGoal")
public abstract class PickUpBlockGoalMixin {
    @WrapOperation(
        method = "tick()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;matches(Lnet/minecraft/tag/Tag;)Z"
        )
    )
    private boolean isBlockInTag(Block block, Tag<Block> tag, Operation<Boolean> original) {
        if (AmsServerSettings.sensibleEnderman) {
            return block.equals(Blocks.MELON) || block.equals(Blocks.CARVED_PUMPKIN);
        } else {
            return original.call(block, tag);
        }
    }

    @ModifyReturnValue(method = "canStart", at = @At("RETURN"))
    private boolean canStart(boolean original) {
        if (AmsServerSettings.endermanPickUpDisabled) {
            return false;
        } else {
            return original;
        }
    }
}
