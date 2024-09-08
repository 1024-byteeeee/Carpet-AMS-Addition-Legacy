package club.mcams.carpet.mixin.rule.commandAnvilInteractionDisabled;

import club.mcams.carpet.commands.rule.commandAnvilInteractionDisabled.AnvilInteractionDisabledCommandRegistry;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.block.AnvilBlock;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

import java.util.OptionalInt;

@GameVersion(version = "Minecraft < 1.15.2")
@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin {
    @WrapOperation(
        method = "activate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;openContainer(Lnet/minecraft/container/NameableContainerFactory;)Ljava/util/OptionalInt;"
        )
    )
    private OptionalInt noActivate(PlayerEntity player, NameableContainerFactory nameableContainerFactory, Operation<OptionalInt> original) {
        if (AnvilInteractionDisabledCommandRegistry.anvilInteractionDisabled) {
            return OptionalInt.empty();
        } else {
            return original.call(player, nameableContainerFactory);
        }
    }

    @SuppressWarnings("SimplifiableConditionalExpression")
    @ModifyReturnValue(method = "activate", at = @At("RETURN"))
    private boolean noActivate(boolean original) {
        return AnvilInteractionDisabledCommandRegistry.anvilInteractionDisabled ? false : original;
    }
}
