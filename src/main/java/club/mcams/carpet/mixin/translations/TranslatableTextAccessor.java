package club.mcams.carpet.mixin.translations;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(TranslatableText.class)
public interface TranslatableTextAccessor {
    @Accessor
    List<Text> getTranslations();
}
