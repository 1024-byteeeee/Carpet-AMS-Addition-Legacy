package club.mcams.carpet.mixin.setting;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import top.byteeeee.annotationtoolbox.annotation.GameVersion;

import java.lang.reflect.Field;

@GameVersion(version = "Minecraft < 1.19")
@Mixin(ParsedRule.class)
public interface ParsedRuleAccessor {
    @SuppressWarnings("rawtypes")
    @Invoker(value = "<init>", remap = false)
    static ParsedRule invokeConstructor(Field field, Rule rule) {
        throw new RuntimeException();
    }
}
