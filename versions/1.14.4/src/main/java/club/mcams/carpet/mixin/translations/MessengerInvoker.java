package club.mcams.carpet.mixin.translations;

import carpet.utils.Messenger;

import net.minecraft.text.BaseText;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft < 1.15.2")
@Mixin(Messenger.class)
public interface MessengerInvoker {
    @Invoker(value = "_applyStyleToTextComponent", remap = false)
    static BaseText invokeApplyStyleToTextComponent(BaseText comp, String style) {
        return null;
    }
}
