package club.mcams.carpet.translations;

import club.mcams.carpet.utils.Messenger;
import net.minecraft.text.BaseText;

public class Translator {
    private final String translationPath;

    public Translator(String translationPath) {
        this.translationPath = translationPath;
    }

    public BaseText tr(String key, Object... args) {
        String translationKey = TranslationConstants.TRANSLATION_KEY_PREFIX + this.translationPath + "." + key;
        return Messenger.tr(translationKey, args);
    }
}
