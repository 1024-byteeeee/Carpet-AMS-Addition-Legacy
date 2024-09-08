package club.mcams.carpet.mixin.rule.bedRockFlying;

import club.mcams.carpet.AmsServerSettings;

import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Shadow
    public Input input;

    @Inject(method = "move", at = @At("TAIL"), cancellable = true)
    private void onMove(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        if (AmsServerSettings.bedRockFlying) {
            ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
            if (movementType.equals(MovementType.SELF) && player.abilities.flying && !hasMovementInput()) {
                player.setVelocity(Vec3d.ZERO);
                ci.cancel();
            }
        }
    }

    @Unique
    private boolean hasMovementInput() {
        Vec2f vec2f = this.input.getMovementInput();
        return vec2f.x != 0.0F || vec2f.y != 0.0F;
    }
}